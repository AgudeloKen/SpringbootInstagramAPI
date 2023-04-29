package com.ken.socialapi.requests;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class StoryRequest {

    private MultipartFile image;
}
