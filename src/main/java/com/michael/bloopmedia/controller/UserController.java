package com.michael.bloopmedia.controller;

import com.michael.bloopmedia.model.user.User;
import com.michael.bloopmedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bloopmedia/api/user")
public class UserController {

    @Autowired
    UserService userService;

    // register a user
    @PostMapping("/register")
    protected ResponseEntity<User> signUp(@RequestBody User user){
        return new ResponseEntity<>(userService.registerUser(user), HttpStatus.OK);
    }

    // get one user
    @GetMapping("/{id}")
    protected ResponseEntity<User> getOneUser(@PathVariable String id){
        return new ResponseEntity<>(userService.getOneUser(id),HttpStatus.OK);
    }

    // get all users
    @GetMapping("/all")
    protected ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }

    @PostMapping("/login")
    protected ResponseEntity<User> loginUser(@RequestBody User user){
        return new ResponseEntity<>(userService.login(user.getEmail(),user.getPassword()),HttpStatus.OK);
    }
}
