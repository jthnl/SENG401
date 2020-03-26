/*
 * SENG 401
 * NHL Web App
 * Created March 10, 2020
 * Jedediah Heal
 */

package nhl.entities;

import nhl.entities.User;

import java.util.Date;

public class Forum
{
	private User creator;
	private String title;
	private String desc;
	private Date dateCreated;
	private int id;

	public static void main( String args[] )
	{
		System.out.println( "forum" );
	}

	public Forum( User creator, String title, String desc )
	{
		this.creator = creator;
		this.title = title;
		this.desc = desc;
		this.dateCreated = new Date();
	}
}