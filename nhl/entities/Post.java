/*
 * SENG 401
 * NHL Web App
 * Created March 10, 2020
 * Jedediah Heal
 */

package nhl.entities;

import nhl.entities.Forum;
import nhl.entities.User;

import java.util.Date;

public class Post
{
	private User creator;
	private Forum forum;
	private String title;
	private String content;
	private Date dateCreated;
	private int id;

	public static void main( String args[] )
	{
		System.out.println( "post" );
	}

	public Post( User creator, Forum forum, String title, String content )
	{
		this.creator = creator;
		this.forum = forum;
		this.title = title;
		this.content = content;
		this.dateCreated = new Date();
	}
}