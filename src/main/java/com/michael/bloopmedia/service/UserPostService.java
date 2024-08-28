package com.michael.bloopmedia.service;

import com.michael.bloopmedia.model.post.MiniUserInfo;
import com.michael.bloopmedia.model.post.PostContent;
import com.michael.bloopmedia.model.post.UserPost;
import com.michael.bloopmedia.model.user.User;
import com.michael.bloopmedia.repository.UserPostRepository;
import com.michael.bloopmedia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserPostService {
    @Autowired
    UserPostRepository postRepository;

    @Autowired
    UserRepository userRepo;

    public UserPost createPost(List<MultipartFile> files, String description,String userId ){
        User user = userRepo.findById(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        MiniUserInfo userInfo = new MiniUserInfo();
        userInfo.setUsername(user.getUserName());
        userInfo.setProfileImage(user.getProfilePicture());


        // set postContent
        PostContent content = new PostContent();
        content.setPostFiles(new ArrayList<>());
        content.setDescription(description);


        // set actual userPost
        UserPost post = new UserPost();
        post.setUser(userInfo);
        post.setContent(content);
        post.setPostDateTime(new Date());


        return postRepository.save(post);


    }
}
