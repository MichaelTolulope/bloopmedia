package com.michael.bloopmedia.controller;

import com.michael.bloopmedia.model.post.UserPost;
import com.michael.bloopmedia.service.UserPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@RestController
@RequestMapping("bloopmedia/api/userPost")
public class UserPostController {
    @Autowired
    UserPostService postService;


    @PostMapping("/create-post/{userId}")
    protected ResponseEntity<UserPost> createPost
            (@PathVariable String userId,
             @RequestParam MultipartFile file1,
             @RequestParam MultipartFile file2,
             @RequestParam MultipartFile file3,
             @RequestParam String postDescription

             ){
        ArrayList<MultipartFile> fileList = new ArrayList<>();
        if(!file1.isEmpty()){
        fileList.add(file1);
        }
        if(!file2.isEmpty()){
        fileList.add(file2);
        }
        if(!file3.isEmpty()){
        fileList.add(file2);
        }

        return new ResponseEntity<>(
                postService.createPost(fileList,postDescription,userId),
                HttpStatus.OK
        );

    }
}
