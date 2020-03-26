/*
 * SENG 401
 * NHL Web App
 * Created March 10, 2020
 * Jedediah Heal
 */

package nhl.entities;

public class UserInfo
{
	private String username;
	private String password;
	private String fname;
	private String lname;
	private String email;

	public static void main( String args[] )
	{
		System.out.println( "userInfo" );
	}

	public UserInfo( String username, String password, String fname, String lname, String email )
	{
		this.username = username;
		this.password = password;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}
}