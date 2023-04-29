package com.ken.socialapi.controllers;

import com.ken.socialapi.dto.PostDTO;
import com.ken.socialapi.exceptions.PostException;
import com.ken.socialapi.exceptions.UserException;
import com.ken.socialapi.requests.PostRequest;
import com.ken.socialapi.responses.PostResponse;
import com.ken.socialapi.responses.UserResponse;
import com.ken.socialapi.services.post.PostServiceIMPL;
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

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostServiceIMPL postServiceIMPL;

    public PostController(PostServiceIMPL postServiceIMPL){
        this.postServiceIMPL = postServiceIMPL;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PostDTO.class))
            })
    })
    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true)
    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts(){
        return ResponseEntity.ok(postServiceIMPL.findAll().stream().map(PostDTO::new).toList());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PostDTO.class))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
            })
    })
    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true)
    @GetMapping("/{userId}")
    public ResponseEntity<List<PostDTO>> getAllPostsByUser(@PathVariable Long userId) throws UserException {
        return ResponseEntity.ok(postServiceIMPL.findAllByUser(userId).stream().map(PostDTO::new).toList());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PostDTO.class))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponse.class))
            })
    })
    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true)
    @GetMapping("/post/{id}")
    public ResponseEntity<PostDTO> getOnePost(@PathVariable Long id) throws PostException {
        return ResponseEntity.ok(new PostDTO(postServiceIMPL.findOne(id)));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PostDTO.class))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponse.class))
            })
    })
    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true)
    @PutMapping("/post/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @RequestBody PostRequest request) throws PostException {
        return ResponseEntity.ok(new PostDTO(postServiceIMPL.updatePost(id, request)));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PostDTO.class))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponse.class))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
            })
    })
    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true)
    @PostMapping(value = "/post/{userId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<PostDTO> createPost(@PathVariable Long userId, @ModelAttribute PostRequest request) throws PostException, UserException {
        return ResponseEntity.ok(new PostDTO(postServiceIMPL.createPost(userId, request)));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponse.class))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponse.class))
            })
    })
    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true)
    @DeleteMapping("/post/{id}")
    public ResponseEntity<PostResponse> deletePost(@PathVariable Long id) throws PostException {
        return ResponseEntity.ok(postServiceIMPL.deletePost(id));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PostDTO.class))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponse.class))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
            })
    })
    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true)
    @GetMapping("/post/like/{postId}/{userId}")
    public ResponseEntity<PostDTO> likePost(@PathVariable Long postId, @PathVariable Long userId) throws PostException, UserException {
        return ResponseEntity.ok(new PostDTO(postServiceIMPL.likePost(postId, userId)));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PostDTO.class))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponse.class))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
            })
    })
    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true)
    @GetMapping("/post/dislike/{postId}/{userId}")
    public ResponseEntity<PostDTO> dislikePost(@PathVariable Long postId, @PathVariable Long userId) throws PostException, UserException {
        return ResponseEntity.ok(new PostDTO(postServiceIMPL.dislikePost(postId, userId)));
    }
}
