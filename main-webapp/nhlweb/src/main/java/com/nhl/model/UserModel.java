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

import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.AccountException;
import java.util.Iterator;
import java.util.Date;
import java.text.DateFormat;

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

	public UsernameID authenticateUser( String username, String password ) throws FailedLoginException
	{
		Iterator<Document> it = users.find().iterator();

		while (it.hasNext())
		{
			Document user_doc = it.next();
			String db_username = user_doc.get( "username" ).toString();
			if( db_username.compareToIgnoreCase( username ) == 0 )
			{
				String db_password = user_doc.get( "password" ).toString();
				if( db_password.compareTo( password ) == 0 )
				{
					return new UsernameID( user_doc.get("_id").toString(), db_username );
				}
				throw new FailedLoginException( "Incorrect Password" );
			}
		}

		throw new FailedLoginException( "Username does not exist!" );
	}

	public void createUser( UserInfo info ) throws AccountException
	{
		Iterator<Document> it = users.find().iterator();

		while (it.hasNext())
		{
			String username = it.next().get( "username" ).toString();
			if( username.compareToIgnoreCase( info.username ) == 0 )
			{
				throw new AccountException( "Username taken" );
			}
		}

		DateFormat df = DateFormat.getInstance();
		Document document = new Document( "username", info.username )
				.append( "password", info.password )
				.append( "fname", info.fname )
				.append( "lname", info.lname )
				.append( "email", info.email )
				.append( "joined", df.format( new Date() ) );
		users.insertOne(document);
	}

	public User getUser( String userId ) throws AccountException
	{
		Iterator<Document> it = users.find().iterator();

		while (it.hasNext())
		{
			Document user_doc = it.next();
			String db_id = user_doc.get( "_id" ).toString();
			if( db_id.compareToIgnoreCase( userId ) == 0 )
			{
				return new User( user_doc );
			}
		}

		throw new AccountException( "User ID not found" );
	}

}