/*
 * SENG 401
 * NHL Web App
 * Created March 10, 2020
 * Jedediah Heal
 */

package nhl.entities;

import java.util.Date;

public class User
{
	private String username;
	private String password;
	private String fname;
	private String lname;
	private String email;
	private Date dateJoined;
	private int id;

	private static int count = 0;

	public static void main( String args[] )
	{
		System.out.println( "user" );
	}

	public User( String username, String password, String fname, String lname, String email )
	{
		this.username = username;
		this.password = password;
		this.fname = fname;
		this.lname = lname;
		this.email = email;

		this.dateJoined = new Date();
		
		count++;
		this.id = count*100 + 4;
	}
}