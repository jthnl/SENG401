/*
 * SENG 401
 * NHL Web App
 * Created March 11, 2020
 * Jedediah Heal
 */

package nhl.model;

import nhl.entities.User;
import nhl.entities.UserInfo;

import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.AccountException;
import java.util.ArrayList;
import java.util.Iterator;

public class UserModel
{

	private ArrayList<User> users;

	// static db connection

	public UserModel()
	{
		users = new ArrayList<User>();
	}

	public User authenticateUser( String username, String password ) throws FailedLoginException
	{
		Iterator<User> it = users.iterator();
		while( it.hasNext() )
		{
			User user = it.next();
			if( user.getInfo().getUsername().compareTo( username ) == 0 )
			{
				if( user.getInfo().getPassword().compareTo( password ) == 0 )
				{
					return user;
				}	
				throw new FailedLoginException( "Incorrect password" );
			}
		}

		throw new FailedLoginException( "Username does not exist!" );
	}

	public void createUser( UserInfo info ) throws AccountException
	{
		Iterator<User> it = users.iterator();
		while( it.hasNext() )
		{
			User user = it.next();
			if( user.getInfo().getUsername().compareTo( info.getUsername() ) == 0 )
			{
				throw new AccountException( "Username taken" );
			}
		}

		int id = users.size() * 100 + 1;
		users.add( new User( info, id ) );
	}

	public boolean changeUsername( int userId, String username )
	{
		// accesss db here
		return false;
	}

}