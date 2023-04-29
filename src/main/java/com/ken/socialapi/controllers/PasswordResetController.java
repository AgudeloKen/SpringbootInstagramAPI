package com.ken.socialapi.controllers;

import com.ken.socialapi.exceptions.UserException;
import com.ken.socialapi.requests.PasswordResetRequest;
import com.ken.socialapi.responses.EmailResponse;
import com.ken.socialapi.responses.PasswordResponse;
import com.ken.socialapi.responses.UserResponse;
import com.ken.socialapi.services.PasswordResetService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/email")
@RestController
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    public PasswordResetController(PasswordResetService passwordResetService){
        this.passwordResetService = passwordResetService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EmailResponse.class))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
            })
    })
    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true)
    @GetMapping("/password-send/{id}")
    public ResponseEntity<EmailResponse> sendResetPasswordEmail(@PathVariable Long id) throws MessagingException, UserException {
        return passwordResetService.sendPasswordResetEmail(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PasswordResponse.class))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PasswordResponse.class))
            })
    })
    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true)
    @PostMapping ("/password-reset")
    public ResponseEntity<PasswordResponse> resetPassword(@RequestParam String token, @RequestBody PasswordResetRequest request) throws UserException {
        return passwordResetService.resetPassword(token, request);
    }
}
