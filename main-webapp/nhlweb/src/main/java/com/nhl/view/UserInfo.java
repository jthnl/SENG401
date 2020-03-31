/*
 * SENG 401
 * NHL Web App
 * Created March 10, 2020
 * Jedediah Heal
 */

package com.nhl.view;

public class UserInfo
{
	public String username;
	public String password;
	public String fname;
	public String lname;
	public String email;

	public UserInfo( String username, String password, String fname, String lname, String email )
	{
		this.username = username;
		this.password = password;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
	}

}