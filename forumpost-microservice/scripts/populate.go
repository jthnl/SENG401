package main

import (
	"context"
	"flag"
	"fmt"
	"log"
    "io/ioutil"
	"strings"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

var db *mongo.Client
var forumdb *mongo.Collection
var mongoCtx context.Context

func main() {
	// CLI - INPUTS, SETUP MONGO PORT
	mongoURIPtr := flag.String("mongo", "localhost:27017", "specify where mongodb is running")
	flag.Parse()
	mongoURI := "mongodb://" + *mongoURIPtr
	
	// SETUP MONGODB
	fmt.Printf("Connecting to MongoDB at: %s\n", mongoURI)
	mongoCtx = context.Background()
	db, err := mongo.Connect(mongoCtx, options.Client().ApplyURI(mongoURI))
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
	useDB := "mydb"
	useCollection := "forum"
	forumdb = db.Database(useDB).Collection(useCollection)
	fmt.Printf("Using %s - %s", useDB, useCollection)

	// ADD DATA
	fbin, err := ioutil.ReadFile("./data.txt")
    if err != nil {
        log.Fatal(err)
	} // read the file
	f := string(fbin)
	line := strings.Split(f, "\n"); // create an array of words
	for i := 0; i < len(line); i++{ // for every word insert it in the collection
		var temp = strings.Split(line[i],",");
		CreateForum(temp[0],temp[1],temp[2],temp[3])
		fmt.Printf("create forum - %s	%s	%s	%s\n", temp[0],temp[1],temp[2],temp[3])
	}

	// TEARDOWN
	fmt.Println("\ndone - kill program")
	db.Disconnect(mongoCtx)
	fmt.Println("bye.")
}

func CreateForum(id string, author_id string, title string, content string) {
	fid, err := primitive.ObjectIDFromHex(id);
	if(err != nil){
		fmt.Printf("unable to add 1")
	}
	// convert to local struct
	data := ForumItem{
		ID: 	  fid,
		AuthorID: author_id,
		Title:    title,
		Content:  content,
	}
	// insert to mongodb
	result, err := forumdb.InsertOne(mongoCtx, data)
	oid := result.InsertedID.(primitive.ObjectID)
	fmt.Printf("Added %s ", oid)
	if err != nil {
		fmt.Printf("unable to add 2")
	}
}

type ForumItem struct {
	ID       primitive.ObjectID `bson:"_id,omitempty"`
	AuthorID string             `bson:"author_id"`
	Content  string             `bson:"content"`
	Title    string             `bson:"title"`
}