package com.nhl.nhlweb;

import com.nhl.view.MessageView;
import com.nhl.view.MsgObjectView;
import com.nhl.view.UserInfo;
import com.nhl.view.User;
import com.nhl.model.UserModel;

import org.springframework.web.bind.annotation.*;
import javax.security.auth.login.AccountException;
import javax.security.auth.login.FailedLoginException;

class LoginInfo { public String username; public String password; }

@RestController
public class UserController {

    private static UserModel model = new UserModel();

    @PostMapping( value = "/user/create" )
    public MessageView createUser( @RequestBody UserInfo infoJSON ) {
        try {
            model.createUser( infoJSON );
        } catch( AccountException e ) {
            return new MessageView( true, e.getMessage(), false, null, null );
        }
        return new MessageView( false, null, true, "Account created", null );
    }

    @GetMapping( value = "/user/login" )
    public MessageView authenticateUser( @RequestBody LoginInfo loginJSON )
    {
        User user = null;
        try {
            user = model.authenticateUser( loginJSON.username, loginJSON.password );
        } catch( FailedLoginException e ) {
            return new MessageView( true, e.getMessage(), false, null, null );
        }
        return new MessageView( false, null, true, "Login Successful", user );
    }

}
