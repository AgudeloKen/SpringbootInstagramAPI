package com.ken.socialapi.requests;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class PostRequest {

    private String caption;

    private List<MultipartFile> images;
}
