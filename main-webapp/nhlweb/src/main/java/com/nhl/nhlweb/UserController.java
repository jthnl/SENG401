package com.nhl.nhlweb;

import com.nhl.view.MessageView;
import com.nhl.view.UserInfo;
import com.nhl.view.User;
import com.nhl.view.UsernameID;
import com.nhl.model.UserModel;

import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountException;
import javax.security.auth.login.FailedLoginException;
import javax.servlet.http.HttpServletResponse;

class LoginInfo {
    public String username;
    public String password;
}

@CrossOrigin(origins = "*")
@RestController
public class UserController {

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
    public MessageView authenticateUser(@RequestBody LoginInfo loginJSON, HttpServletResponse response) {
        System.out.println("HEREA");
        UsernameID user = null;
        try {
            user = model.authenticateUser(loginJSON.username, loginJSON.password);
        } catch (FailedLoginException e) {
            System.out.println("HEREB");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new MessageView(true, e.getMessage(), false, null, null);
        }
        System.out.println("HEREC");
        response.setStatus(HttpServletResponse.SC_OK);
        return new MessageView(false, null, true, "Login Successful", user);
    }

    @GetMapping(value = "/users")
    public MessageView getUser(@RequestHeader("Authorization") String token, HttpServletResponse response) {
        System.out.println("PT2A");
        boolean success = model.getUser(token);
        if (!success) {
            System.out.println("PT2B");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new MessageView(!success, "not authenticated", success, null, null);
        }
        System.out.println("PT2C");
        response.setStatus(HttpServletResponse.SC_OK);
        return new MessageView(!success, null, success, "authenticated", null);
    }

}
