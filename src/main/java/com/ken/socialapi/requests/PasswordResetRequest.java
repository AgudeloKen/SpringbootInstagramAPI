package com.ken.socialapi.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PasswordResetRequest {

    @NotNull(message = "Cannot be null.")
    private String password;
}
