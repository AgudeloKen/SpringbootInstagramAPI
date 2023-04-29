package com.ken.socialapi.dto;

import com.ken.socialapi.models.User;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.*;

@Getter
@Setter
public class UserDTO implements Serializable {

    private Long id;
    private String role;
    private String username;
    private String email;
    private String phone;
    private String website;
    private String bio;
    private String gender;
    private String imageURL;
    private Date verifiedAt;
    private Date createdAt;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.website = user.getWebsite();
        this.bio = user.getBio();
        this.gender = user.getGender();
        if(user.getImage() != null){
            this.imageURL = user.getImage().getImgURL();
        }
        this.createdAt = user.getCreatedAt();
        this.role = user.getAuthority().name();
        this.verifiedAt = user.getVerifiedAt();
    }
}
