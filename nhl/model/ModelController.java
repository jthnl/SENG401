/*
 * SENG 401
 * NHL Web App
 * Created March 11, 2020
 * Jedediah Heal
 */

package nhl.model;

import nhl.entities.User;
import nhl.model.UserModel;
import nhl.entities.UserInfo;

import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.AccountException;
import java.util.Scanner;

public class ModelController
{
	private UserModel userModel;

	public static void main( String args[] )
	{
		ModelController mc = new ModelController();

		while( true )
		{
			Scanner scan = new Scanner( System.in );
			System.out.print( "\nEnter username: " );
			String username = scan.nextLine();
			System.out.print( "Enter password: " );
			String password = scan.nextLine();

			UserInfo info = new UserInfo( username, password, "a", "b", "c" );

			try
			{
				mc.createUser( info );
			} catch( AccountException e ) {
				System.out.println( e.toString() );
			}
		}

	}

	public ModelController()
	{
		userModel = new UserModel();
	}

	/* Accepts a username and password attempt
	 * @return If username is found in the DB and password matches, return user info
	 * @throws LoginException if username is not found or password does not match
	 */
	public User authenticateUser( String username, String password ) throws FailedLoginException
	{
		User user = userModel.authenticateUser( username, password );
		return user;
	}

	public void createUser( UserInfo info ) throws AccountException
	{
		userModel.createUser( info );
	}

}