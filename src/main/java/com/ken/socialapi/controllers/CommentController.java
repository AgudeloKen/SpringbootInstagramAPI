package com.ken.socialapi.controllers;

import com.ken.socialapi.dto.CommentDTO;
import com.ken.socialapi.exceptions.CommentException;
import com.ken.socialapi.exceptions.PostException;
import com.ken.socialapi.exceptions.UserException;
import com.ken.socialapi.requests.CommentRequest;
import com.ken.socialapi.responses.CommentResponse;
import com.ken.socialapi.responses.PostResponse;
import com.ken.socialapi.responses.UserResponse;
import com.ken.socialapi.services.comment.CommentServiceIMPL;
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
@RequestMapping("/comments")
public class CommentController {

    private final CommentServiceIMPL commentServiceIMPL;

    public CommentController(CommentServiceIMPL commentServiceIMPL){
        this.commentServiceIMPL = commentServiceIMPL;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDTO.class))
            })
    })
    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true)
    @GetMapping
    public ResponseEntity<List<CommentDTO>> findAll(){
        return ResponseEntity.ok(commentServiceIMPL.findAll().stream().map(CommentDTO::new).toList());
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDTO.class))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponse.class))
            })
    })
    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true)
    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDTO>> findAllByPost(@PathVariable Long postId) throws PostException {
        return ResponseEntity.ok(commentServiceIMPL.findAllByPost(postId).stream().map(CommentDTO::new).toList());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDTO.class))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponse.class))
            })
    })
    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true)
    @GetMapping("/comment/{id}")
    public ResponseEntity<CommentDTO> findOne(@PathVariable Long id) throws CommentException {
        return ResponseEntity.ok(new CommentDTO(commentServiceIMPL.findOne(id)));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDTO.class))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponse.class))
            })
    })
    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true)
    @PutMapping("/comment/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long id, @RequestBody CommentRequest request) throws CommentException {
        return ResponseEntity.ok(new CommentDTO(commentServiceIMPL.updateComment(id, request)));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDTO.class))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponse.class))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
            })
    })
    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true)
    @PostMapping("/comment/{userId}/{postId}")
    public ResponseEntity<CommentDTO> createComment(@PathVariable Long userId, @PathVariable Long postId, @RequestBody CommentRequest request) throws UserException, PostException {
        return ResponseEntity.ok(new CommentDTO(commentServiceIMPL.createComment(userId, postId, request)));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponse.class))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponse.class))
            })
    })
    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true)
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<CommentResponse> deleteComment(@PathVariable Long id) throws CommentException {
        return ResponseEntity.ok(commentServiceIMPL.deleteComment(id));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDTO.class))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponse.class))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
            })
    })
    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true)
    @GetMapping("/comment/like/{commentId}/{userId}")
    public ResponseEntity<CommentDTO> likeComment(@PathVariable Long commentId, @PathVariable Long userId) throws UserException, CommentException {
        return ResponseEntity.ok(new CommentDTO(commentServiceIMPL.likeComment(userId, commentId)));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDTO.class))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponse.class))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
            })
    })
    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true)
    @GetMapping("/comment/dislike/{commentId}/{userId}")
    public ResponseEntity<CommentDTO> dislikeComment(@PathVariable Long commentId, @PathVariable Long userId) throws UserException, CommentException {
        return ResponseEntity.ok(new CommentDTO(commentServiceIMPL.dislikeComment(userId, commentId)));
    }
}
