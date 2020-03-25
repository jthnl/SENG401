/*
 * SENG 401
 * NHL Web App
 * Created March 11, 2020
 * Jedediah Heal
 */

package com.nhl.model;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.nhl.view.User;
import com.nhl.view.UserInfo;
import org.bson.Document;

import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.AccountException;
import java.util.Iterator;
import java.util.Date;

public class UserModel
{

	private MongoClient mongo;
	private MongoCredential credential;
	private MongoDatabase database;
	private MongoCollection<Document> users;

	public UserModel()
	{
		mongo = new MongoClient("localhost", 27017);
		credential = MongoCredential.createCredential("sampleUser", "core_app", "password".toCharArray());
		database = mongo.getDatabase("core_app");
		users = database.getCollection("Users");
	}

	public User authenticateUser( String username, String password ) throws FailedLoginException
	{
		return null;

		//throw new FailedLoginException( "Username does not exist!" );
	}

	public void createUser( UserInfo info ) throws AccountException
	{
		System.out.println("Searching usernames" );
		FindIterable<Document> iterDoc = users.find();

		// Getting the iterator
		Iterator<Document> it = iterDoc.iterator();

		while (it.hasNext())
		{
			String username = it.next().get( "username" ).toString();
			if( username.compareToIgnoreCase( info.username ) == 0 )
			{
				throw new AccountException( "Username taken" );
			}
		}

		Document document = new Document( "username", info.username )
				.append( "password", info.password )
				.append( "fname", info.fname )
				.append( "lname", info.lname )
				.append( "email", info.email )
				.append( "joined", new Date() );
		users.insertOne(document);
	}

	public boolean changeUsername( int userId, String username )
	{
		return false;
	}

}