package com.shadan.userservice.controller;

import com.shadan.userservice.model.ChangePassword;
import com.shadan.userservice.model.User;
import com.shadan.userservice.service.JwtService;
import com.shadan.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            if (authenticate.isAuthenticated())
                return jwtService.generateToken(user.getUsername());
            else
                return "Login failed";
        }
        catch (Exception e) {
            return "Invalid credentials";
        }
    }

    @DeleteMapping("/deleteUser")
    public String deleteUser() {
        return userService.deleteUser();
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestBody ChangePassword changePassword) {
        return userService.changePassword(changePassword);
    }

    @PostMapping("/getUser")
    public boolean getUser() {
//        System.out.println(authorization);
        System.out.println("This method called");
        return userService.getUser();
    }
}
