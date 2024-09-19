package com.michael.bloopmedia.service;

import com.cloudinary.utils.ObjectUtils;
import com.michael.bloopmedia.configuration.CloudinaryConfig;
import com.michael.bloopmedia.model.post.Like;
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

    public String deletePost(String userId, String postId){
        // delete post from user post list
        User user = userRepo.findById(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
            List<String> postIdList = user.getPosts();
        for(String individualPostId: postIdList){
            if(postId.equals(individualPostId)){
                postIdList.remove(individualPostId);
            }

        }
        user.setPosts(postIdList);
        userRepo.save(user);

        // delete post from database
        postRepository.deleteById(postId);
        return "post deleted successfully!";
    }

    public String addLike(String userId, String postId){

        // adding the post liked to the user's likedPosts
        User user = userRepo.findById(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<String> likedPostList = user.getLikedPosts();
        likedPostList.add(postId);
        user.setLikedPosts(likedPostList);

        // updates user
        userRepo.save(user);

        // adding like to the post
        UserPost post = postRepository.findById(postId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));

        MiniUserInfo miniUserInfo = new MiniUserInfo();
        miniUserInfo.setId(user.getId());
        miniUserInfo.setUsername(user.getUserName());
        miniUserInfo.setProfileImage(user.getUserName());

        Like like = new Like(miniUserInfo,new Date());

        List<Like> likes = post.getLikes();
        likes.add(like);

        post.setLikes(likes);

        // update like count
        post.setLikeCount(post.getLikes().size());

        // updates post
        postRepository.save(post);


        return "like added!";
    }

    public String removeLike(String userId, String postId){

        // removing post from liked post list
        User user = userRepo.findById(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<String> likedPosts = user.getLikedPosts();
        for(String post: likedPosts){
            if(post.equals(postId)){
                likedPosts.remove(post);
            }

        }
        user.setLikedPosts(likedPosts);
        userRepo.save(user);

        // removing the like from the post
        UserPost post = postRepository.findById(postId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<Like> likes = post.getLikes();
        for(Like like: likes){
            // checks and confirms if this post was liked by the user
            if(userId.equals(like.getUser().getId())){
                likes.remove(like);
                return "like removed from post!";

            }

        }


        return null;
    }

}
