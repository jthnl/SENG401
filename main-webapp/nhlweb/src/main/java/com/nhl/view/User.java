/*
 * SENG 401
 * NHL Web App
 * Created March 10, 2020
 * Jedediah Heal
 */

package com.nhl.view;

import com.nhl.view.UserInfo;

import java.util.Date;


public class User
{
	private UserInfo info;
	private Date dateJoined;
	private String id;

	public static void main( String args[] )
	{
		System.out.println( "user" );
	}

	public User( UserInfo info, String id )
	{
		this.info = info;
		this.dateJoined = new Date();
		this.id = id;
	}

	public UserInfo getInfo()
	{
		return info;
	}
}