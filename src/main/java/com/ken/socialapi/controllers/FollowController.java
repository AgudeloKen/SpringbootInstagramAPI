package com.ken.socialapi.controllers;

import com.ken.socialapi.dto.UserDTO;
import com.ken.socialapi.exceptions.UserException;
import com.ken.socialapi.responses.UserResponse;
import com.ken.socialapi.services.follow.FollowServiceIMPL;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class FollowController {

    private final FollowServiceIMPL followServiceIMPL;

    public FollowController(FollowServiceIMPL followServiceIMPL){
        this.followServiceIMPL = followServiceIMPL;
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
    @PostMapping("/follow/{userId}/{followId}")
    public ResponseEntity<UserDTO> followUser(@PathVariable Long userId, @PathVariable Long followId) throws UserException {
        return ResponseEntity.ok(new UserDTO(followServiceIMPL.followUser(userId, followId)));
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
    @PostMapping("/unfollow/{userId}/{followId}")
    public ResponseEntity<UserDTO> unfollowUser(@PathVariable Long userId, @PathVariable Long followId) throws UserException {
        return ResponseEntity.ok(new UserDTO(followServiceIMPL.unfollowUser(userId, followId)));
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
    @GetMapping("/followers/{id}")
    public ResponseEntity<List<UserDTO>> getFollowersByUser(@PathVariable Long id) throws UserException {
        return ResponseEntity.ok(followServiceIMPL.getFollowersByUser(id).stream().map(UserDTO::new).toList());
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
    @GetMapping("/following/{id}")
    public ResponseEntity<List<UserDTO>> getFollowingByUser(@PathVariable Long id) throws UserException {
        return ResponseEntity.ok(followServiceIMPL.getFollowingByUser(id).stream().map(UserDTO::new).toList());
    }
}
