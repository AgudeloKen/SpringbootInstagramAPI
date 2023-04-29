package com.ken.socialapi.controllers;

import com.ken.socialapi.exceptions.UserException;
import com.ken.socialapi.responses.EmailResponse;
import com.ken.socialapi.responses.UserResponse;
import com.ken.socialapi.services.EmailVerificationService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailVerifyController {

    private final EmailVerificationService emailVerificationService;

    public EmailVerifyController(EmailVerificationService emailVerificationService){
        this.emailVerificationService = emailVerificationService;
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
    @GetMapping("/email-send/{id}")
    public ResponseEntity<EmailResponse> sendEmailVerification(@PathVariable Long id) throws MessagingException, UserException {
       return emailVerificationService.sendVerifyEmail(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EmailResponse.class))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EmailResponse.class))
            })
    })
    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true)
    @GetMapping("/verify")
    public ResponseEntity<EmailResponse> emailVerified(@RequestParam String token){
        return emailVerificationService.verifyEmail(token);
    }
}
