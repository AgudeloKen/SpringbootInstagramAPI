package com.ken.socialapi.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    private String username;
    private String phone;
    private String website;
    private String bio;
    private String gender;
}
