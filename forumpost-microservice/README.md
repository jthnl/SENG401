# ForumPost Microservice
## 1. Prerequites 
- Download and install [mongodb](https://www.mongodb.com/download-center/community)
    - more info: [mongoDB Tutorial](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-windows/)

## 2. Developer Setup
1. Download [GoLang](https://golang.org/dl/)
2. Download dependencies: (open up cmd/terminal) and run:
```
go get -u google.golang.org/grpc
go get -u github.com/golang/protobuf/protoc-gen-go
go get go.mongodb.org/mongo-driver/mongo
```
3. Run the program: `go run server.go`
4. populate mongodb with data: (might have to do this manually using bloomRPC (see below), will automate this soon)


## 3. Running the Microservice
1. run `./server.exe`
2. see optional flags `./server.exe -h`
2. populate mongodb with data: (might have to do this manually using bloomRPC (see below), will automate this soon)


## 4. Populating through bloomRPC - will automate soon
1. Download (bloomRPC)[https://github.com/uw-labs/bloomrpc/releases]
2. on the app, add protos:
    - add: ./forumpost-microservice/proto/forum/forum.proto
    - add: ./forumpost-microservice/proto/post/post.proto
3. Add forums using CreateForum or Add posts using CreatePost