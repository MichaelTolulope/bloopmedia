package com.michael.bloopmedia.repository;

import com.michael.bloopmedia.model.post.UserPost;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPostRepository extends MongoRepository<UserPost, String> {
}
