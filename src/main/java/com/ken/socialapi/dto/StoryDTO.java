package com.ken.socialapi.dto;

import com.ken.socialapi.models.Story;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class StoryDTO {

    private Long id;
    private String imageURL;
    private Long userId;
    private Date createdAt;

    public StoryDTO(Story story){
        this.id = story.getId();
        this.imageURL = story.getImage().getImgURL();
        this.userId = story.getUser().getId();
        this.createdAt = story.getCreatedAt();
    }
}
