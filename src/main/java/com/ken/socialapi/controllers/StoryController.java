package com.ken.socialapi.controllers;

import com.ken.socialapi.dto.StoryDTO;
import com.ken.socialapi.exceptions.StoryException;
import com.ken.socialapi.exceptions.UserException;
import com.ken.socialapi.requests.StoryRequest;
import com.ken.socialapi.responses.StoryResponse;
import com.ken.socialapi.responses.UserResponse;
import com.ken.socialapi.services.story.StoryServiceIMPL;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/stories")
@RestController
public class StoryController {

    private final StoryServiceIMPL storyServiceIMPL;

    public StoryController(StoryServiceIMPL storyServiceIMPL){
        this.storyServiceIMPL = storyServiceIMPL;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = StoryDTO.class))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
            })
    })
    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true)
    @GetMapping("/story/user/{userId}")
    public ResponseEntity<List<StoryDTO>> findAllByUser(@PathVariable Long userId) throws UserException {
        return ResponseEntity.ok(storyServiceIMPL.findAllByUser(userId).stream().map(StoryDTO::new).toList());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = StoryDTO.class))
            }),
    })
    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true)
    @GetMapping
    public ResponseEntity<List<StoryDTO>> findAll(){
        return ResponseEntity.ok(storyServiceIMPL.findAll().stream().map(StoryDTO::new).toList());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = StoryDTO.class))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
            })
    })
    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true)
    @PostMapping(value = "/story/{userId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<StoryDTO> createStory(@PathVariable Long userId, @ModelAttribute StoryRequest request) throws UserException {
        return ResponseEntity.ok(new StoryDTO(storyServiceIMPL.createStory(userId, request)));
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = StoryDTO.class))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = StoryResponse.class))
            })
    })
    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true)
    @GetMapping("/story/{id}")
    public ResponseEntity<StoryDTO> findOne(@PathVariable Long id) throws StoryException {
        return ResponseEntity.ok(new StoryDTO(storyServiceIMPL.findOne(id)));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = StoryResponse.class))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = StoryResponse.class))
            })
    })
    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true)
    @DeleteMapping("/story/{id}")
    public ResponseEntity<StoryResponse> deleteStory(@PathVariable Long id) throws StoryException {
        return ResponseEntity.ok(storyServiceIMPL.deleteStory(id));
    }
}
