package com.ken.socialapi.dto;


import com.ken.socialapi.models.Comment;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
public class CommentDTO {

    private Long id;
    private String content;
    private Long likes;
    private Long userId;
    private List<Long> likedByUsers = new ArrayList<>();
    private Date createdAt;

    public CommentDTO(Comment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.userId = comment.getUser().getId();
        comment.getLikedBy().forEach(u -> this.likedByUsers.add(u.getId()));
        this.likes = (long) this.likedByUsers.size();
        this.createdAt = comment.getCreatedAt();
    }
}
