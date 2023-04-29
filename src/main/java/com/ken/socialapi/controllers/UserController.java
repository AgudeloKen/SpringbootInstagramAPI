package com.ken.socialapi.controllers;

import com.ken.socialapi.dto.UserDTO;
import com.ken.socialapi.exceptions.UserException;
import com.ken.socialapi.requests.UserRequest;
import com.ken.socialapi.responses.UserResponse;
import com.ken.socialapi.services.user.UserServiceIMPL;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceIMPL userServiceIMPL;

    public UserController(UserServiceIMPL userServiceIMPL){
        this.userServiceIMPL = userServiceIMPL;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))
            })
    })
    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true)
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return ResponseEntity.ok(userServiceIMPL.allUsers().stream().map(UserDTO::new).toList());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))
            })
    })
    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true)
    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> getAllUsersByUsername(@RequestParam("query") String query){
        return ResponseEntity.ok(userServiceIMPL.findAllByUsernameContaining(query).stream().map(UserDTO::new).toList());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
            })
    })
    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true)
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getOneUser(@PathVariable Long id) throws UserException {
        return ResponseEntity.ok(new UserDTO(userServiceIMPL.findOne(id)));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
            })
    })
    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true)
    @PutMapping("/user/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserRequest request) throws UserException {
        return ResponseEntity.ok(new UserDTO(userServiceIMPL.updateUser(id, request)));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
            })
    })
    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true)
    @DeleteMapping("/user/{id}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable Long id) throws UserException {
        return ResponseEntity.status(204).body(userServiceIMPL.deleteUser(id));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
            })
    })
    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true)
    @PostMapping(value = "/user/upload/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UserDTO> uploadImage(@ModelAttribute MultipartFile file, @PathVariable Long id) throws UserException {
        return ResponseEntity.ok(new UserDTO(userServiceIMPL.uploadImage(file, id)));
    }
}
