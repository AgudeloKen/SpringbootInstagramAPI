package com.ken.socialapi.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {

    @Email
    @NotNull(message = "Cannot be null.")
    private String email;

    @NotBlank(message = "Cannot be null.")
    private String password;

    @NotBlank(message = "Cannot be null.")
    private String phone;
    @NotBlank(message = "Cannot be null.")
    private String username;
}
