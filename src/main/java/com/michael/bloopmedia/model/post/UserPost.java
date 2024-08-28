package com.michael.bloopmedia.model.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPost {
    @Id
    private String id;
    private MiniUserInfo user;
    private PostContent content;
    private Date postDateTime;

}
