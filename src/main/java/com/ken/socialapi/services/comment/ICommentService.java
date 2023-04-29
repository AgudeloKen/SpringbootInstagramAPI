package com.ken.socialapi.services.comment;

import com.ken.socialapi.exceptions.CommentException;
import com.ken.socialapi.exceptions.PostException;
import com.ken.socialapi.exceptions.UserException;
import com.ken.socialapi.models.Comment;
import com.ken.socialapi.requests.CommentRequest;
import com.ken.socialapi.responses.CommentResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICommentService {

    List<Comment> findAllByPost(Long id) throws PostException;

    List<Comment> findAll();

    Comment findOne(Long id) throws CommentException;

    Comment updateComment(Long id, CommentRequest request) throws CommentException;

    Comment createComment(Long userId, Long postId, CommentRequest request) throws UserException, PostException;

    CommentResponse deleteComment(Long id) throws CommentException;

    Comment likeComment(Long userId, Long commentId) throws CommentException, UserException;

    Comment dislikeComment(Long userId, Long commentId) throws CommentException, UserException;
}
