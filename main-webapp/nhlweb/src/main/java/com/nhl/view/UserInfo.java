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
	public String firstName;
	public String lastName;
	public String email;

	public UserInfo( String username, String password, String firstName, String lastName, String email )
	{
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

}