package main

import (
	"context"
	"flag"
	"fmt"
	"log"
	"net"
	"os"
	"os/signal"
	"strconv"
	"time"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
	"google.golang.org/grpc"
	"google.golang.org/grpc/codes"
	"google.golang.org/grpc/status"
	"github.com/google/uuid"
	forumpb "../proto/forum"
	postpb "../proto/post"
)

type ForumServiceServer struct {}

type ForumItem struct {
	ID       primitive.ObjectID `bson:"_id,omitempty"`
	AuthorID string             `bson:"author_id"`
	Content  string             `bson:"content"`
	Title    string             `bson:"title"`
}

func (s *ForumServiceServer) CreateForum(ctx context.Context, req *forumpb.CreateForumReq) (*forumpb.CreateForumRes, error) {
	// read forum to be added
	forum := req.GetForum()
	// convert to local struct
	data := ForumItem{
		AuthorID: forum.GetAuthorId(),
		Title:    forum.GetTitle(),
		Content:  forum.GetContent(),
	}
	// insert to mongodb
	result, err := forumdb.InsertOne(mongoCtx, data)
	if err != nil {
		return nil, status.Errorf(codes.Internal, fmt.Sprintf("Internal error: %v", err),
		)
	}
	// get generated ID
	oid := result.InsertedID.(primitive.ObjectID)
  	forum.Id = oid.Hex()
	// return forum back
	return &forumpb.CreateForumRes{Forum: forum}, nil
}

func (s *ForumServiceServer) ReadForum(ctx context.Context, req *forumpb.ReadForumReq) (*forumpb.ReadForumRes, error) {
	// read requested forum ID
	oid, err := primitive.ObjectIDFromHex(req.GetId());
	if(err != nil){
		return nil, status.Errorf(codes.InvalidArgument, fmt.Sprintf("Could not convert to ObjectId: %v", err))
	}
	// find forum on mongoDB
	result := forumdb.FindOne(ctx, bson.M{"_id": oid})
	data := ForumItem{}
	if err := result.Decode(&data); err != nil {
		return nil, status.Errorf(codes.NotFound, fmt.Sprintf("Forum does not exist. id requested %s: %v", req.GetId(), err))
	}
	// setup response of forum found
	response := &forumpb.ReadForumRes{
		Forum: &forumpb.Forum{
			Id: oid.Hex(),
			AuthorId: data.AuthorID,
			Title:    data.Title,
			Content:  data.Content,
		},
	}
	// return forum found
	return response, nil
}

func (s *ForumServiceServer) ListForums(req *forumpb.ListForumReq, stream forumpb.ForumService_ListForumsServer) error {
	// get all forums 
	data := &ForumItem{}
	cursor, err := forumdb.Find(context.Background(), bson.M{})
	if err != nil{
		return status.Errorf(codes.Internal, fmt.Sprintf("Unknown internal error: %v", err))
	}
	defer cursor.Close(context.Background())
	// send forums as a gRPC stream
	for cursor.Next(context.Background()) {
		err := cursor.Decode(data)
		if err != nil {
			return status.Errorf(codes.Unavailable, fmt.Sprintf("Could not decode data: %v", err))
		}
		stream.Send(&forumpb.ListForumRes{
			Forum: &forumpb.Forum{
				Id: data.ID.Hex(),
				AuthorId: data.AuthorID,
				Title:    data.Title,
				Content:  data.Content,
			},
		})
	}
	if err := cursor.Err(); err != nil {
		return status.Errorf(codes.Internal, fmt.Sprintf("Unkown cursor error: %v", err))
	}
	// stop sending
	return nil
}

func (s *ForumServiceServer)  UpdateForum(ctx context.Context, req *forumpb.UpdateForumReq) (*forumpb.UpdateForumRes, error){
	// read forum to be update
	forum := req.GetForum()
	oid, err := primitive.ObjectIDFromHex(forum.GetId())
	if err != nil {
		return nil, status.Errorf(codes.InvalidArgument, fmt.Sprintf("Error getting Forum id %v", err),)
	}
	// convert values to be updated into local struct
	update := bson.M{
		"author_id": forum.GetAuthorId(),
		"title":      forum.GetTitle(),
		"content":    forum.GetContent(),
	}
	forumEditID := bson.M{"_id": oid}
	// find and update based on forum id
	result := forumdb.FindOneAndUpdate(ctx, forumEditID, bson.M{"$set": update}, options.FindOneAndUpdate().SetReturnDocument(1))
	decoded := ForumItem{}
	err = result.Decode(&decoded)
	if err != nil {
		return nil, status.Errorf(codes.NotFound, fmt.Sprintf("Forum was not updated. forum id requested: %v", err))
	}
	// return updated forum
	return &forumpb.UpdateForumRes{
		Forum: &forumpb.Forum{
			Id:       decoded.ID.Hex(),
			AuthorId: decoded.AuthorID,
			Title:    decoded.Title,
			Content:  decoded.Content,
		},
	}, nil
}

func (s *ForumServiceServer) DeleteForum(ctx context.Context, req *forumpb.DeleteForumReq) (*forumpb.DeleteForumRes, error) {
	// read id of forum to be deleted
	oid, err := primitive.ObjectIDFromHex(req.GetId())
	if err != nil {
		return nil, status.Errorf(codes.InvalidArgument, fmt.Sprintf("Could not convert to ObjectId: %v", err))
	}
	// delete forum
	_, err = forumdb.DeleteOne(ctx, bson.M{"_id": oid})
	if err != nil {
		return nil, status.Errorf(codes.NotFound, fmt.Sprintf("Forum was not deleted. forum id requested = %s: %v", req.GetId(), err))
	}
	// return true if successful
	return &forumpb.DeleteForumRes{
		Success: true,
	}, nil
}

type PostServiceServer struct{}

type PostItem struct {
	ID       string		 				`bson:"_id,omitempty"`
	ForumID	 primitive.ObjectID 		`bson:"_forum_id,omitempty"`
	AuthorID string             		`bson:"author_id"`
	Title    string             		`bson:"title"`
	Content  string             		`bson:"content"`
	Timestamp string 					`bson:"timestamp"`
}

type VotePostItem struct {
	ID        string		 			`bson:"_id,omitempty"`
	PID       string		 			`bson:"post_id,omitempty"`
	AuthorID  string             		`bson:"user_id"`
	Vote 	  int 						`bson:"vote"`
}

func GetPostVote(id string) (string, string){
	data := &VotePostItem{}
	cursor, err := votepostdb.Find(context.Background(), bson.M{"post_id": id})
	if err != nil{
		return "err","err"
	}
	defer cursor.Close(context.Background())
	upvote := 0
	downvote := 0
	for cursor.Next(context.Background()) {
		fmt.Printf("%d", data.Vote)
		err := cursor.Decode(data)
		if err != nil {
			return "err","err"
		}
		if data.Vote == 1{
			upvote++
		} else {
			downvote++
		}
	}
	if err := cursor.Err(); err != nil {
		return "err","err"
	}
	
	return strconv.Itoa(upvote), strconv.Itoa(downvote)
} 

func (s *PostServiceServer) CreatePost(ctx context.Context, req *postpb.CreatePostReq) (*postpb.CreatePostRes, error) {
	// read post to be added
	post := req.GetPost()
	// convert forum ID
	forum_id, err := primitive.ObjectIDFromHex(post.GetForumId());
	if(err != nil){
		return nil, status.Errorf(codes.InvalidArgument, fmt.Sprintf("Could not convert to ObjectId: %v", err))
	}
	curr_time := time.Now().String()
	// convert to local struct
	data := PostItem{
		ID: uuid.New().String(),
		ForumID: forum_id,
		AuthorID: post.GetAuthorId(),
		Title: post.GetTitle(),
		Content: post.GetContent(),
		Timestamp: curr_time,
	}
	// insert to mongodb
	result, err := postdb.InsertOne(mongoCtx, data)
	if err != nil {
		return nil, status.Errorf(codes.Internal, fmt.Sprintf("Internal error: %v", err),
		)
	}
	// get generated ID, and set timestamp
	oid := result.InsertedID.(string)
	post.Id = oid
	post.Timestamp = curr_time
	post.Upvote, post.Downvote = GetPostVote(oid)
	// return post back
	return &postpb.CreatePostRes{Post: post}, nil
}

func (s *PostServiceServer) ReadPost(ctx context.Context, req *postpb.ReadPostReq) (*postpb.ReadPostRes, error) {
	// read requested post ID
	oid := req.GetId()
	// find post on mongoDB
	result := postdb.FindOne(ctx, bson.M{"_id": oid})
	data := PostItem{}
	if err := result.Decode(&data); err != nil {
		return nil, status.Errorf(codes.NotFound, fmt.Sprintf("Post does not exist. id requested %s: %v", req.GetId(), err))
	}
	upVote, downVote := GetPostVote(oid)
	// setup response of post found
	response := &postpb.ReadPostRes{
		Post: &postpb.Post{
			Id: oid,
			ForumId: data.ForumID.Hex(),
			AuthorId: data.AuthorID,
			Title: data.Title,
			Content: data.Content,
			Timestamp: data.Timestamp,
			Upvote: upVote,
			Downvote: downVote,
		},
	}
	// return post found
	return response, nil
}

func (s *PostServiceServer) ListPosts(req *postpb.ListPostReq, stream postpb.PostService_ListPostsServer) error {
	// read forum id reqeusted
	forumIDHex, err := primitive.ObjectIDFromHex(req.GetForumId())
	if(err != nil){
		status.Errorf(codes.InvalidArgument, fmt.Sprintf("Could not convert to ObjectId: %v", err))
	}
	// get all posts with forum id 
	data := &PostItem{}
	cursor, err := postdb.Find(context.Background(), bson.M{"_forum_id": forumIDHex})
	if err != nil{
		return status.Errorf(codes.Internal, fmt.Sprintf("Unknown internal error: %v", err))
	}
	defer cursor.Close(context.Background())
	// send posts as a gRPC stream
	for cursor.Next(context.Background()) {
		err := cursor.Decode(data)
		if err != nil {
			return status.Errorf(codes.Unavailable, fmt.Sprintf("Could not decode data: %v", err))
		}
		upVote, downVote := GetPostVote(data.ID)
		stream.Send(&postpb.ListPostRes{
			Post: &postpb.Post{
				Id: data.ID,
				ForumId: data.ForumID.Hex(),
				AuthorId: data.AuthorID,
				Title:    data.Title,
				Content:  data.Content,
				Timestamp: data.Timestamp,
				Upvote: upVote,
				Downvote: downVote,
			},
		})
	}
	if err := cursor.Err(); err != nil {
		return status.Errorf(codes.Internal, fmt.Sprintf("Unkown cursor error: %v", err))
	}
	// stop sending
	return nil
}

func (s *PostServiceServer)  UpdatePost(ctx context.Context, req *postpb.UpdatePostReq) (*postpb.UpdatePostRes, error){
	// read post to be update
	post := req.GetPost()
	oid := post.GetId()
	fid, err := primitive.ObjectIDFromHex(post.GetForumId())
	if err != nil {
		return nil, status.Errorf(codes.InvalidArgument, fmt.Sprintf("Error getting Forum id %v", err),)
	}
	curr_time := time.Now().String()
	// convert values to be updated into local struct
	update := bson.M{
		"_forum_id":  fid,
		"author_id": post.GetAuthorId(),
		"title":      post.GetTitle(),
		"content":    post.GetContent(),
		"timestamp":  curr_time,
	}
	postEditID := bson.M{"_id": oid}
	// find and update based on post id
	result := postdb.FindOneAndUpdate(ctx, postEditID, bson.M{"$set": update}, options.FindOneAndUpdate().SetReturnDocument(1))
	decoded := PostItem{}
	err = result.Decode(&decoded)
	if err != nil {
		return nil, status.Errorf(codes.NotFound, fmt.Sprintf("Post was not updated. post id requested: %v", err))
	}
	// return updated post
	return &postpb.UpdatePostRes{
		Post: &postpb.Post{
			Id:       decoded.ID,
			AuthorId: decoded.AuthorID,
			Title:    decoded.Title,
			Content:  decoded.Content,
			Timestamp: decoded.Timestamp,
		},
	}, nil
}

func (s *PostServiceServer) DeletePost(ctx context.Context, req *postpb.DeletePostReq) (*postpb.DeletePostRes, error) {
	// read id of post to be deleted
	oid := req.GetId()
	// delete post
	_, err := postdb.DeleteOne(ctx, bson.M{"_id": oid})
	if err != nil {
		return nil, status.Errorf(codes.NotFound, fmt.Sprintf("Post was not deleted. post id requested = %s: %v", req.GetId(), err))
	}
	// return true if successful
	return &postpb.DeletePostRes{
		Success: true,
	}, nil
}

func (s *PostServiceServer) UpvotePost(ctx context.Context, req *postpb.UpvotePostReq)(*postpb.UpvotePostRes, error){
	// get user id and post id
	uid := req.GetUserId()
	pid := req.GetPostId()

	// check if post can be upvoted - check if already upvoted
	chkVote := votepostdb.FindOne(ctx, bson.M{"user_id" : uid, "post_id" : pid})
	data := VotePostItem{}
	if err := chkVote.Decode(&data); err == nil {
		if data.Vote == 1 {
			// already upvoted
			return &postpb.UpvotePostRes{
				Success: false,
				Message: "already upvoted",
			}, nil
		} else {
			// change downvote to upvote
			did, err := primitive.ObjectIDFromHex(data.ID)
			if err != nil{
				return &postpb.UpvotePostRes{
					Success: false,
					Message: "server error, upvoted failed",
				}, nil
			}
			voteEdit := bson.M{"_id": did}
			update := bson.M{
				"post_id":  data.PID,
				"user_id": 	data.AuthorID,
				"vote":    	1,
			}
			result := votepostdb.FindOneAndUpdate(ctx, voteEdit, bson.M{"$set": update}, options.FindOneAndUpdate().SetReturnDocument(1))
			decoded := VotePostItem{}
			err = result.Decode(&decoded)
			if err != nil {
				return &postpb.UpvotePostRes{
					Success: false,
					Message: "server error, upvoted failed",
				}, nil
			}
			return &postpb.UpvotePostRes{
				Success: true,
				Message: "changed from downvote to upvote",
			}, nil
		}
	}
	// create new upvote item
	insertData := VotePostItem{
		PID: pid,
		AuthorID: uid,
		Vote: 1, 
	}
	// insert to database
	_, err := votepostdb.InsertOne(mongoCtx, insertData)
	if err != nil {
		return &postpb.UpvotePostRes{
			Success: false,
			Message: "server error, upvoted failed",
		}, nil
	}
	// success, return
	return &postpb.UpvotePostRes{
		Success: true,
		Message: "upvoted",
	}, nil
}

func (s *PostServiceServer) DownvotePost(ctx context.Context, req *postpb.DownvotePostReq)(*postpb.DownvotePostRes, error){
		// get user id and post id
		uid := req.GetUserId()
		pid := req.GetPostId()
	
		// check if post can be downvoted - check if already downvoted
		chkVote := votepostdb.FindOne(ctx, bson.M{"user_id" : uid, "post_id" : pid})
		data := VotePostItem{}
		if err := chkVote.Decode(&data); err == nil {
			if data.Vote == -1 {
				// already downvoted
				return &postpb.DownvotePostRes{
					Success: false,
					Message: "already downvoted",
				}, nil
			} else {
				// change upvote to downvote
				did, err := primitive.ObjectIDFromHex(data.ID)
				if err != nil{
					return &postpb.DownvotePostRes{
						Success: false,
						Message: "server error, downvote failed",
					}, nil
				}
				voteEdit := bson.M{"_id": did}
				update := bson.M{
					"post_id":  data.PID,
					"user_id": 	data.AuthorID,
					"vote":    	-1,
				}
				result := votepostdb.FindOneAndUpdate(ctx, voteEdit, bson.M{"$set": update}, options.FindOneAndUpdate().SetReturnDocument(1))
				decoded := VotePostItem{}
				err = result.Decode(&decoded)
				if err != nil {
					return &postpb.DownvotePostRes{
						Success: false,
						Message: "server error, upvoted failed",
					}, nil
				}
				return &postpb.DownvotePostRes{
					Success: true,
					Message: "changed from upvote to downvote",
				}, nil
			}
		}
		// create new downvote item
		insertData := VotePostItem{
			PID: pid,
			AuthorID: uid,
			Vote: -1, 
		}
		// insert to database
		_, err := votepostdb.InsertOne(mongoCtx, insertData)
		if err != nil {
			return &postpb.DownvotePostRes{
				Success: false,
				Message: "server error, downvote failed",
			}, nil
		}
		// success, return
		return &postpb.DownvotePostRes{
			Success: true,
			Message: "downvoted",
		}, nil
}


var db *mongo.Client
var forumdb *mongo.Collection
var postdb *mongo.Collection
var votepostdb *mongo.Collection
var mongoCtx context.Context

func main() {
	// CLI - INPUTS
	portNoPtr := flag.String("portnum", "50051", "specify which port the server is running")
	mongoURIPtr := flag.String("mongo", "localhost:27017", "specify where mongodb is running")
	flag.Parse()

	// SETUP VARIABLES
	//portNo := ":50051"
	//mongoURI := "mongodb://localhost:27017"
	portNo := ":" + *portNoPtr
	mongoURI := "mongodb://" + *mongoURIPtr

	// SETUP LISTENER
	fmt.Printf("Starting server on port %s\n", portNo);
	listener, err := net.Listen("tcp", portNo)
	if err != nil {
		log.Fatalf("Setup on port %s failed: %v", portNo, err);
	}

	// SETUP GRPC SERVER
	opts := []grpc.ServerOption{}
	s := grpc.NewServer(opts...)
	forumsrv := &ForumServiceServer{}
	postsrv := &PostServiceServer{}

	// REGISTER GRPC SERVICES
	forumpb.RegisterForumServiceServer(s, forumsrv)
	postpb.RegisterPostServiceServer(s, postsrv)
	
	// SETUP MONGODB
	fmt.Printf("Connecting to MongoDB at: %s\n", mongoURI)
	mongoCtx = context.Background()
	db, err = mongo.Connect(mongoCtx, options.Client().ApplyURI(mongoURI))
	if err != nil {
		log.Fatal(err)
	}
	err = db.Ping(mongoCtx, nil)
	if err != nil {
		log.Fatalf("Error connecting to MongoDB: %v", err)
	} else {
		fmt.Printf("Connected to MongoDB\n")
	}

	// specify database collections
	forumdb = db.Database("mydb").Collection("forum")
	postdb = db.Database("mydb").Collection("post")
	votepostdb = db.Database("mydb").Collection("votepost")

	// START SERVER
	go func() {
		if err := s.Serve(listener); err != nil {
			log.Fatalf("server failed: %v", err)
		}
	}()
	fmt.Printf("Server running on port: %s\n", portNo)

	// KILL SERVER
	c := make(chan os.Signal)
	signal.Notify(c, os.Interrupt)
	<-c

	// TEARDOWN
	fmt.Println("\nkill server")
	s.Stop()
	listener.Close()
	db.Disconnect(mongoCtx)
	fmt.Println("bye.")
}