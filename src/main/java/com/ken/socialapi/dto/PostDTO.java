package com.ken.socialapi.dto;

import com.ken.socialapi.models.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PostDTO {

    private Long id;
    private String caption;
    private List<String> images = new ArrayList<>();
    private Long likes;
    private Long comments;
    private Long userId;
    private List<Long> likedByUsers = new ArrayList<>();
    private Date createdAt;

    public PostDTO(Post post){
        this.id = post.getId();
        this.caption = post.getCaption();
        post.getImages().forEach(img -> this.images.add(img.getImgURL()));
        this.userId = post.getUser().getId();
        post.getLikedBy().forEach(u -> this.likedByUsers.add(u.getId()));
        this.likes = (long) this.getLikedByUsers().size();
        this.comments = (long) post.getComments().size();
        this.createdAt = post.getCreatedAt();
    }
}
