/*
 * SENG 401
 * NHL Web App
 * Created March 10, 2020
 * Jedediah Heal
 */

package nhl.entities;

import nhl.entities.Post;
import nhl.entities.User;

import java.util.Date;

public class Comment
{
	private User creator;
	private Post post;
	private String content;
	private Date dateCreated;
	private int id;

	public static void main( String args[] )
	{
		System.out.println( "comment" );
	}

	public Comment( User creator, Post post, String content )
	{
		this.creator = creator;
		this.post = post;
		this.content = content;
		this.dateCreated = new Date();
	}
}