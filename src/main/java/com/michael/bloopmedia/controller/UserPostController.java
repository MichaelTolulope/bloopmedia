package com.michael.bloopmedia.controller;

import com.michael.bloopmedia.model.post.UserPost;
import com.michael.bloopmedia.service.UserPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("bloopmedia/api/userPost")
public class UserPostController {
    @Autowired
    UserPostService postService;


    @PostMapping("/create-post/{userId}")
    protected ResponseEntity<UserPost> createPost
            (@PathVariable String userId,
             @ModelAttribute("file1") MultipartFile file1,
             @ModelAttribute("file2") MultipartFile file2,
             @ModelAttribute("file3") MultipartFile file3,
             @ModelAttribute(name = "post-description") String postDescription

             ) throws IOException {
        ArrayList<MultipartFile> fileList = new ArrayList<>();
        if(!(file1==null)){
        fileList.add(file1);
        }
        if(!(file2==null)){
        fileList.add(file2);
        }
        if(!(file3 == null)){
        fileList.add(file2);
        }

        return new ResponseEntity<>(
                postService.createPost(fileList,postDescription,userId),
                HttpStatus.OK
        );

    }
}
