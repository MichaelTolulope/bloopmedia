package com.michael.bloopmedia.service;

import com.cloudinary.utils.ObjectUtils;
import com.michael.bloopmedia.configuration.CloudinaryConfig;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserPostService {
    @Autowired
    UserPostRepository postRepository;

    @Autowired
    UserRepository userRepo;

    @Autowired
    CloudinaryConfig cloudinaryConfig;

    public UserPost createPost(List<MultipartFile> files, String description,String userId ) throws IOException {
        User user = userRepo.findById(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        MiniUserInfo userInfo = new MiniUserInfo();
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getUserName());
        userInfo.setProfileImage(user.getProfilePicture());

        List<String> uploadedFileUrlList = new ArrayList<>();

        // upload post images
        for(MultipartFile file: files){
        Map uploadResult =cloudinaryConfig.cloudinary().uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        uploadedFileUrlList.add(uploadResult.get("url").toString());

        }



        // set postContent
        PostContent content = new PostContent();
        content.setPostFiles(uploadedFileUrlList);
        content.setDescription(description);


        // set actual userPost
        UserPost post = new UserPost();
        post.setUser(userInfo);
        post.setContent(content);
        post.setPostDateTime(new Date());

        UserPost finalPost = postRepository.save(post);

        // update user post list
        List<String> prevPostList = user.getPosts();
        prevPostList.add(finalPost.getId());
        user.setPosts(prevPostList);
        userRepo.save(user);

        return finalPost;


    }
}
