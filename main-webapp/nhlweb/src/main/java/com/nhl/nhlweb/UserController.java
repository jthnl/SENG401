package com.nhl.nhlweb;

import com.nhl.view.MessageView;
import com.nhl.view.UserInfo;
import com.nhl.model.UserModel;


import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountException;

@RestController
public class UserController {

    private static UserModel model = new UserModel();

    @PostMapping( value="/createUser" )
    public MessageView createUser( @RequestBody UserInfo infoJSON ) {
        System.out.println("In createUser()");
        try {
            model.createUser( infoJSON );
        } catch( AccountException e ) {
            return new MessageView( true, e.getMessage(), false, null, null );
        }
        return new MessageView( false, null, true, "Account created.", null );
    }

}
