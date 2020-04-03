package main

import (
	"context"
	"fmt"
	"strconv"
	"time"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo/options"
	"google.golang.org/grpc/codes"
	"google.golang.org/grpc/status"
	"github.com/google/uuid"
	postpb "../../proto/post"
)

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
	if forumIDHex.IsZero() {
		cursor, err = postdb.Find(context.Background(), bson.M{})
	}
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

func (s *PostServiceServer) FindPosts(req *postpb.FindPostReq, stream postpb.PostService_FindPostsServer) error {
	
	// read forum id reqeusted
	titleQuery:= req.GetTitleQuery()

	// set filter for text search based on title
	//regex := `^.*` + titleQuery + `.*$`
	filter := bson.M{"$text": bson.M{"$search": titleQuery}}
	findOptions := options.Find()
	findOptions.SetLimit(100)
	findOptions.SetProjection(bson.M{
	  "id":         1,
	  "_forum_id":   1,
	  "author_id":	1,
	  "title": 		1,
	  "content" : 	1,
	  "timestamp" : 1,
	  "score":       bson.M{"$meta": "textScore"},
	})
	findOptions.SetSort(bson.M{"score": bson.M{"$meta": "textScore"}})
	
	// get all posts with filter
	data := &PostItem{}

	cursor, err := postdb.Find(context.Background(), filter, findOptions)
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

		//fmt.Println(data.ForumID)
		//fmt.Println(data.ForumID.Hex())
		upVote, downVote := GetPostVote(data.ID)
		stream.Send(&postpb.FindPostRes{
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