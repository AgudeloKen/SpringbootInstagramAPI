package com.ken.socialapi.exceptions;

import com.ken.socialapi.responses.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<UserResponse> userExceptionHandler(UserException exception){
        return ResponseEntity.badRequest().body(new UserResponse(exception.getMessage()));
    }

    @ExceptionHandler(PostException.class)
    public ResponseEntity<PostResponse> postExceptionHandler(PostException exception){
        return ResponseEntity.badRequest().body(new PostResponse(exception.getMessage()));
    }

    @ExceptionHandler(CommentException.class)
    public ResponseEntity<CommentResponse> commentExceptionHandler(CommentException exception){
        return ResponseEntity.badRequest().body(new CommentResponse(exception.getMessage()));
    }

    @ExceptionHandler(StoryException.class)
    public ResponseEntity<StoryResponse> commentExceptionHandler(StoryException exception){
        return ResponseEntity.badRequest().body(new StoryResponse(exception.getMessage()));
    }

    @ExceptionHandler(AuthorizationFailException.class)
    public ResponseEntity<JWTResponse> userExceptionHandler(AuthorizationFailException exception){
        return ResponseEntity.badRequest().body(new JWTResponse(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValidHandler(MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<>();
        for ( FieldError error : exception.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<InternalError> exceptionHandler(){
        return ResponseEntity.internalServerError().build();
    }
}
