package com.ken.socialapi.controllers;

import com.ken.socialapi.dto.UserDTO;
import com.ken.socialapi.exceptions.AuthorizationFailException;
import com.ken.socialapi.exceptions.UserException;
import com.ken.socialapi.requests.LoginRequest;
import com.ken.socialapi.requests.SignupRequest;
import com.ken.socialapi.responses.JWTResponse;
import com.ken.socialapi.responses.UserResponse;
import com.ken.socialapi.services.auth.AuthServiceIMPL;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {

   private final AuthServiceIMPL authServiceIMPL;

   public AuthController(AuthServiceIMPL authServiceIMPL){
       this.authServiceIMPL = authServiceIMPL;
   }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = JWTResponse.class))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
            })
    })
    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody @Valid LoginRequest request) throws UserException, AuthorizationFailException {
        return ResponseEntity.ok(authServiceIMPL.loginUser(request));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
            })
    })
    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody @Valid SignupRequest request) throws UserException {
       return ResponseEntity.ok(new UserDTO(authServiceIMPL.registerUser(request)));
    }
}
