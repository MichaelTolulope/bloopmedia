package com.michael.bloopmedia.model.post;

import com.michael.bloopmedia.model.user.User;

import java.time.LocalDateTime;

public class Comment {
    private MiniUserInfo user;
    private String commentContent;
    private LocalDateTime dateTime;
}
