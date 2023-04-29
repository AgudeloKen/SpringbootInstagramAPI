package com.ken.socialapi.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequest {

    @Email
    @NotBlank(message = "Cannot be null.")
    private String email;

    @NotBlank(message = "Cannot be null.")
    private String password;
}
