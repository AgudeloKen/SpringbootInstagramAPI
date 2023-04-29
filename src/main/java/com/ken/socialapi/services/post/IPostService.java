package com.ken.socialapi.services.post;

import com.ken.socialapi.exceptions.PostException;
import com.ken.socialapi.exceptions.UserException;
import com.ken.socialapi.models.Post;
import com.ken.socialapi.requests.PostRequest;
import com.ken.socialapi.responses.PostResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPostService {

    List<Post> findAllByUser(Long id) throws UserException;

    List<Post> findAll();

    Post findOne(Long id) throws PostException;

    Post updatePost(Long id, PostRequest request) throws PostException;

    Post createPost(Long userId, PostRequest request) throws PostException, UserException;

    PostResponse deletePost(Long id) throws PostException;

    Post likePost(Long postId, Long userId) throws PostException, UserException;

    Post dislikePost(Long postId, Long userId) throws PostException, UserException;

}
