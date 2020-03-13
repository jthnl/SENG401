package main

import (
	"fmt"
	"log"
	"context"

	forumpb "../proto"
	"go.mongodb.org/mongo-driver/bson/primitive"
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

func main() {
	fmt.Printf("Not implemented");
}