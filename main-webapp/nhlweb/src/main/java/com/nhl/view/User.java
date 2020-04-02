/*
 * SENG 401
 * NHL Web App
 * Created March 10, 2020
 * Jedediah Heal
 */

package com.nhl.view;

import com.nhl.view.UserInfo;
import com.nhl.view.MsgObjectView;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import org.bson.Document;

public class User implements MsgObjectView
{
	private String id;
	private UserInfo info;
	private Date joined;

	public User( Document doc )
	{
		this.id = doc.get( "_id" ).toString();

		String usrnm = doc.get( "username" ).toString();
		String pass = doc.get( "password" ).toString();
		String fname = doc.get( "fname" ).toString();
		String lname = doc.get( "lname" ).toString();
		String email = doc.get( "email" ).toString();
		this.info = new UserInfo( usrnm, pass, fname, lname, email );

		DateFormat df = DateFormat.getInstance();
		try {
			this.joined = df.parse( doc.get( "joined" ).toString() );
		} catch( ParseException e) {
			System.err.println( "Bad 'Date joined' record found" );
			this.joined = new Date();
		}
	}

	public String getId() {return id;}
	public UserInfo getInfo() {return info;}
	public String getJoined()
	{
		DateFormat df = DateFormat.getInstance();
		return df.format( joined );
	}
}