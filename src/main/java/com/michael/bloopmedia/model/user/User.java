package com.michael.bloopmedia.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document
@Data  // this adds getters and setters from lombok
@Builder
@NoArgsConstructor
@AllArgsConstructor // this adds constructors from lombok
public class User {
    @Id
    private String id;
    private String profilePicture;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
    private Gender gender;
    private Date dateOfBirth;
    private List<String> friendsList;
    private List<String> posts;
    private List<String> likedPosts;
}
