package com.michael.bloopmedia.service;

import com.michael.bloopmedia.model.user.User;
import com.michael.bloopmedia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User registerUser(User user){
        user.setProfilePicture("");
        user.setFriendsList(new ArrayList<>());
        user.setPosts(new ArrayList<>());
        user.setPassword(passwordEncoder.encode(user.getPassword())); // encode password
        return userRepo.save(user);
    }

    public User getOneUser(String id){
        return userRepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<User> getAllUsers(){return userRepo.findAll();}
 
    public User login(String email, String password){
        User user = userRepo.findByEmail(email).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (passwordEncoder.matches(password,user.getPassword())){
            return user;
        }
        return null;
    }


}
