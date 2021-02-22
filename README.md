# NHL Application
SENG 401 (Winter 2020) Final Project

Contributors: Owen Troke-Billard, Nick Brough, Jedediah Heal, Qifeng Li,  Jathniel Ong  

## Introduction
The NHLApp is a reddit style web-based application where users can discuss their favorite NHL teams. Users are able to create posts, write comments, and rank comments (similar to an upvote/downvote system). Additionally, users can 
subscribe to different team-forums and get basic updates on the latests NHL games.

[demo video](https://www.youtube.com/watch?v=0anj0gVdB7s&feature=youtu.be)

## Project Layout
![Architecture](/FinalDesign.png)
### 1. NHL (./NHL)
- Description: Front End
- Technologies Used: Angular 8, HTML, CSS
- Dev Port: 4200
### 2. Main Web-App (./main-webapp)
- Description: Coordinates between the front-end and all the other microservices. The main-web-app implements a RestAPI using Java Spring Boot and communicates with the front-end using JSON objects. The main-web-app also communicates with the microservices using GRPC.
- Technologies Used: Java, Spring Boot, Maven, MongoDB, GRPC
- Dev Port: 8080
### 3. ForumPost Microservice (./forumpost-microservice)
- Description: Manages forums and posts Data. Communicates with the main-web-app using GRPC and stores data in MongoDB.
- Technologies Used: Golang, MongoDB, GRPC
- Dev Port: 50051
### 4. Comments Microservice (./comments-microservice)
- Description: Manages comments including nesting-comments and stores data in MongoDB. 
- Technologies Used: Rust, MongoDB, GRPC
- Dev Port: 50052
### 5. Notifications Microservice (./notifications-microservice)
- Description: Manages notifications, subscriptions, and posts data. Communicates with the main-web-app using GRPC and stores data in MongoDB.
- Technologies Used: Java, Maven, MongoDB, GRPC
- Dev Port: 50053
