package com.nhl.model;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.Iterator;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;

public class ConnectToDB {

    public static void main( String args[] ) {

        // Creating a Mongo client
        MongoClient mongo = new MongoClient("localhost", 27017);

        // Creating Credentials
        MongoCredential credential;
        credential = MongoCredential.createCredential("sampleUser", "core_app",
                "password".toCharArray());
        System.out.println("Connected to the database successfully");

        // Accessing the database
        MongoDatabase database = mongo.getDatabase("core_app");

        // Creating a collection
        System.out.println("Collection created successfully");

        // Retieving a collection
        MongoCollection<Document> collection = database.getCollection("Users");
        System.out.println("Collection myCollection selected successfully");

        // Getting the iterable object
        FindIterable<Document> iterDoc = collection.find();
        int i = 1;

        // Getting the iterator
        Iterator it = iterDoc.iterator();

        while (it.hasNext()) {
            System.out.println(it.next());
            i++;
        }
    }
}