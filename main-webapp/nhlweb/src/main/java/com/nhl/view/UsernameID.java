package com.nhl.view;

public class UsernameID implements MsgObjectView
{
    public String id;
    public String username;

    public UsernameID( String id, String username )
    {
        this.id = id;
        this.username = username;
    }
}
