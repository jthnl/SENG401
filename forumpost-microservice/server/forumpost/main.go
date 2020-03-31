package main

import (
	"context"
	"flag"
	"fmt"
	"log"
	"net"
	"os"
	"os/signal"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
	"google.golang.org/grpc"
	forumpb "../../proto/forum"
	postpb "../../proto/post"
	
)

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