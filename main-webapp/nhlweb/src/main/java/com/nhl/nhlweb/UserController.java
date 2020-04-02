package com.nhl.nhlweb;

import com.nhl.view.*;
import com.nhl.model.UserModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountException;
import javax.security.auth.login.FailedLoginException;
import javax.servlet.http.HttpServletResponse;

class LoginInfo {
    public String username;
    public String password;
}

class UserString implements MsgObjectView {
    public String userId;
    public String username;
    public String password;
    public String fname;
    public String lname;
    public String email;
    public String joined;
}

@CrossOrigin(origins = "*")
@RestController
public class UserController {
    Logger log = LoggerFactory.getLogger(UserController.class);
    private static UserModel model = new UserModel();

    @PostMapping(value = "/user/create")
    public MessageView createUser(@RequestBody UserInfo infoJSON) {
        try {
            model.createUser(infoJSON);
        } catch (AccountException e) {
            return new MessageView(true, e.getMessage(), false, null, null);
        }
        return new MessageView(false, null, true, "Account created", null);
    }

    @PostMapping(value = "/users/authenticate")
    public MessageView authenticateUser(@RequestBody UserInfo loginJSON, HttpServletResponse response) {
        UsernameID user = null;
        try {
            user = model.authenticateUser(loginJSON.username, loginJSON.password);
        } catch (FailedLoginException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new MessageView(true, e.getMessage(), false, null, null);
        }
        response.setStatus(HttpServletResponse.SC_OK);
        return new MessageView(false, null, true, "Login Successful", user);
    }

    @GetMapping(value = "/users")
    public MessageView getUsers(@RequestHeader("Authorization") String token, HttpServletResponse response) {
        boolean success = model.getUsers(token);
        if (!success) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new MessageView(!success, "not authenticated", success, null, null);
        }
        response.setStatus(HttpServletResponse.SC_OK);
        return new MessageView(!success, null, success, "authenticated", null);
    }

    @GetMapping(value = "/user")
    public MessageView getUser( @RequestParam( value = "id", required = true ) String userId )
    {
        System.out.println("getUser called");
        User user = null;
        try {
            user = model.getUser( userId );
        } catch( AccountException e ) {
            return new MessageView( true, e.getMessage(), false, null, null );
        }
        UserString userString = new UserString();
        userString.userId = user.getId();
        userString.username = user.getInfo().username;
        userString.password = user.getInfo().password;
        userString.fname = user.getInfo().firstName;
        userString.lname = user.getInfo().lastName;
        userString.email = user.getInfo().email;
        userString.joined = user.getJoined();

        System.out.println("returning user" );
        return new MessageView( false, null, true, "Retrieved user info", userString );
    }

}
