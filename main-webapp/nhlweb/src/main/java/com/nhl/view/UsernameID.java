package com.nhl.view;

public class UsernameID implements MsgObjectView
{
    public String token;
    public String id;
    public String username;

    public UsernameID( String id, String username, String token)
    {
        this.id = id;
        this.username = username;
        this.token = token;
    }
}
