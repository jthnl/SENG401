package main

import (
	"fmt"
	"log"
	"context"
	"net"
	"os"
	"os/signal"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
	"google.golang.org/grpc"

	forumpb "../proto"
)

type ForumServiceServer struct {}

type ForumItem struct {
	ID       primitive.ObjectID `bson:"_id,omitempty"`
	AuthorID string             `bson:"author_id"`
	Content  string             `bson:"content"`
	Title    string             `bson:"title"`
}

func (s *ForumServiceServer) CreateForum(ctx context.Context, req *forumpb.CreateForumReq) (*forumpb.CreateForumRes, error) {
	log.Fatal("Not implemented")
	return nil, nil
}

func (s *ForumServiceServer) ReadForum(ctx context.Context, req *forumpb.ReadForumReq) (*forumpb.ReadForumRes, error) {
	log.Fatal("Not implemented")
	return nil, nil
}

func (s *ForumServiceServer) ListForums(req *forumpb.ListForumReq, stream forumpb.ForumService_ListForumsServer) error {
	log.Fatal("Not implemented")
	return nil
}

func (s *ForumServiceServer) DeleteForum(ctx context.Context, req *forumpb.DeleteForumReq) (*forumpb.DeleteForumRes, error) {
	log.Fatal("Not implemented")
	return nil, nil
}

func (s *ForumServiceServer)  UpdateForum(ctx context.Context, req *forumpb.UpdateForumReq) (*forumpb.UpdateForumRes, error){
	log.Fatal("Not implemented")
	return nil, nil
}

var db *mongo.Client
var forumdb *mongo.Collection
var mongoCtx context.Context

func main() {
	// SETUP VARIABLES
	portNo := ":50051"
	mongoURI := "mongodb://localhost:27017"

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

	// REGISTER GRPC SERVICES
	forumpb.RegisterForumServiceServer(s, forumsrv)

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