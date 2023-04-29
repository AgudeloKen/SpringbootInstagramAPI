package com.ken.socialapi.services.comment;

import com.ken.socialapi.exceptions.CommentException;
import com.ken.socialapi.exceptions.PostException;
import com.ken.socialapi.exceptions.UserException;
import com.ken.socialapi.models.Comment;
import com.ken.socialapi.models.Post;
import com.ken.socialapi.models.User;
import com.ken.socialapi.repository.CommentRepository;
import com.ken.socialapi.repository.PostRepository;
import com.ken.socialapi.repository.UserRepository;
import com.ken.socialapi.requests.CommentRequest;
import com.ken.socialapi.responses.CommentResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceIMPL implements ICommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentServiceIMPL(PostRepository postRepository, CommentRepository commentRepository, UserRepository userRepository){
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }
    @Override
    public List<Comment> findAllByPost(Long id) throws PostException {
        if(!postRepository.existsById(id)){
            throw new PostException("Post not found.");
        }
        Post post = postRepository.getReferenceById(id);
        return commentRepository.findAllByPost(post);
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment findOne(Long id) throws CommentException {
        if(!commentRepository.existsById(id)){
            throw new CommentException("Comment not found.");
        }
        return commentRepository.getReferenceById(id);
    }

    @Override
    public Comment updateComment(Long id, CommentRequest request) throws CommentException {
        if(!commentRepository.existsById(id)){
            throw new CommentException("Comment not found.");
        }
        Comment comment = commentRepository.getReferenceById(id);
        comment.setContent(request.getContent());
        return commentRepository.save(comment);
    }

    @Override
    public Comment createComment(Long userId, Long postId, CommentRequest request) throws UserException, PostException {
        if(!postRepository.existsById(postId)){
            throw new PostException("Post not found.");
        }
        if(!userRepository.existsById(userId)){
            throw new UserException("User not found.");
        }
        User user = userRepository.getReferenceById(userId);
        Post post = postRepository.getReferenceById(postId);
        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setUser(user);
        comment.setPost(post);
        return commentRepository.save(comment);
    }

    @Override
    public CommentResponse deleteComment(Long id) throws CommentException {
        if(!commentRepository.existsById(id)){
            throw new CommentException("Comment not found.");
        }
        commentRepository.deleteById(id);
        return new CommentResponse("Comment has been deleted.");
    }

    @Override
    public Comment likeComment(Long userId, Long commentId) throws CommentException, UserException {
        if(!commentRepository.existsById(commentId)){
            throw new CommentException("Comment not found.");
        }
        if(!userRepository.existsById(userId)){
            throw new UserException("User not found.");
        }

        Comment comment = commentRepository.getReferenceById(commentId);
        User user = userRepository.getReferenceById(userId);

        if(comment.getLikedBy().contains(user)){
            throw new UserException("User already like this comment.");
        }

        comment.getLikedBy().add(user);
        return commentRepository.save(comment);
    }

    @Override
    public Comment dislikeComment(Long userId, Long commentId) throws CommentException, UserException {
        if(!commentRepository.existsById(commentId)){
            throw new CommentException("Comment not found.");
        }
        if(!userRepository.existsById(userId)){
            throw new UserException("User not found.");
        }
        Comment comment = commentRepository.getReferenceById(commentId);
        User user = userRepository.getReferenceById(userId);

        if(!comment.getLikedBy().contains(user)){
            throw new UserException("User has not liked this comment.");
        }

        comment.getLikedBy().remove(user);
        return commentRepository.save(comment);
    }
}
