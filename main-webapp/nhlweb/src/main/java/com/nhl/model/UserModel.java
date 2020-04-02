/*
 * SENG 401
 * NHL Web App
 * Created March 11, 2020
 * Jedediah Heal
 */

package com.nhl.model;

import com.nhl.view.User;
import com.nhl.view.UserInfo;
import com.nhl.view.UsernameID;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.AccountException;
import java.util.Iterator;
import java.util.Date;
import java.text.DateFormat;
import java.util.regex.Pattern;

public class UserModel {

    private MongoClient mongo;
    private MongoCredential credential;
    private MongoDatabase database;
    private MongoCollection<Document> users;
    private MongoCollection<Document> userAuth;

    public UserModel() {
        mongo = new MongoClient("localhost", 27017);
        credential = MongoCredential.createCredential("sampleUser", "core_app", "password".toCharArray());
        database = mongo.getDatabase("core_app");
        users = database.getCollection("Users");
        userAuth = database.getCollection("user_auth");
    }

    public UsernameID authenticateUser(String username, String password) throws FailedLoginException {
        System.out.println("TPP:" + username + " " + password);
        Iterator<Document> userFound = users.find(new Document("username", username)).iterator();

        if (!userFound.hasNext()) {
            throw new FailedLoginException("username does not exist");
        }
        Document user = userFound.next();
        if(!user.get("password").toString().equals(password)){
            throw new FailedLoginException("incorrect pw");
        }
        String token = getToken(user.get("_id").toString());
        return new UsernameID(user.get("_id").toString(), user.get("username").toString(), token);
    }

    public String getToken(String userid){
        DateFormat df = DateFormat.getInstance();
        Document document = new Document("userid", userid).append("authtime", df.format(new Date()));
        userAuth.insertOne(document);
        ObjectId id = (ObjectId)document.get( "_id" );
        return id.toString();
    }

    public void createUser(UserInfo info) throws AccountException {
        Document userExists = users.find(new Document("username", Pattern.compile(info.username, Pattern.CASE_INSENSITIVE))).first();
        if (userExists != null) {
            throw new AccountException("Username taken");
        }
        DateFormat df = DateFormat.getInstance();
        Document document = new Document("username", info.username)
                .append("password", info.password)
                .append("fname", info.firstName)
                .append("lname", info.lastName)
                .append("email", info.email)
                .append("joined", df.format(new Date()));
        users.insertOne(document);
    }

    public boolean getUser(String token) {
        Document tokenExists = userAuth.find(new Document("_id", new ObjectId(token))).first();
        if (tokenExists.isEmpty()) {
            return false;
        }
        //System.out.println(tokenExists.getDate("authtime"));
        return true;
    }

}