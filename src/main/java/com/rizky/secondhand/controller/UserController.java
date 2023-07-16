package com.rizky.secondhand.controller;

import com.rizky.secondhand.entity.User;
import com.rizky.secondhand.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initUser(){
        userService.initUser();
    }

    @PostMapping("/registernewuser")
    public User registerNewUser(@RequestBody User user) {
        return userService.createNewUser(user);
    }

    @GetMapping("/foradmin")
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin() {
        return "This is for Admin";
    }

    @GetMapping("/foruser")
    @PreAuthorize("hasAnyRole('Admin', 'User')")
    public String forUser() {
        return "This is for User";
    }

}
