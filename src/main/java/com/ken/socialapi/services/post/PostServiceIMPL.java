package com.ken.socialapi.services.post;

import com.ken.socialapi.exceptions.PostException;
import com.ken.socialapi.exceptions.UserException;
import com.ken.socialapi.models.Image;
import com.ken.socialapi.models.Post;
import com.ken.socialapi.models.User;
import com.ken.socialapi.repository.PostRepository;
import com.ken.socialapi.repository.UserRepository;
import com.ken.socialapi.requests.PostRequest;
import com.ken.socialapi.responses.PostResponse;
import com.ken.socialapi.services.file.FileServiceIMPL;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PostServiceIMPL implements IPostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final FileServiceIMPL fileServiceIMPL;

    public PostServiceIMPL(UserRepository userRepository, PostRepository postRepository, FileServiceIMPL fileServiceIMPL){
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.fileServiceIMPL = fileServiceIMPL;
    }

    @Override
    public List<Post> findAllByUser(Long id) throws UserException {
        if(!userRepository.existsById(id)){
            throw new UserException("User not found.");
        }
        User user = userRepository.getReferenceById(id);
        return postRepository.findAllByUser(user);
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Post findOne(Long id) throws PostException {
        if(!postRepository.existsById(id)){
            throw new PostException("Post not found.");
        }
        return postRepository.getReferenceById(id);
    }

    @Override
    public Post updatePost(Long id, PostRequest request) throws PostException {
        if(!postRepository.existsById(id)){
            throw new PostException("Post not found.");
        }

        Post post = postRepository.getReferenceById(id);

        if(request.getCaption() != null) {
            post.setCaption(request.getCaption());
        }
        return postRepository.save(post);
    }

    @Override
    public Post createPost(Long userId, PostRequest request) throws PostException, UserException {
        if(!userRepository.existsById(userId)){
            throw new UserException("User not found.");
        }
        if(request.getCaption() == null && request.getImages() == null){
            throw new PostException("Error creating a post.");
        }
        User user = userRepository.getReferenceById(userId);
        Post post = new Post();

        post.setCaption(request.getCaption());
        post.setUser(user);

        if(request.getImages() != null && !request.getImages().isEmpty()){
            request.getImages().forEach(image -> {
                try {
                    Image img = fileServiceIMPL.getFilePath(image);
                    post.getImages().add(img);
                } catch (UserException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        return postRepository.save(post);
    }

    @Override
    public PostResponse deletePost(Long id) throws PostException {
        if(!postRepository.existsById(id)){
            throw new PostException("Post not found.");
        }
        postRepository.deleteById(id);
        return new PostResponse("Post has been deleted.");
    }

    @Override
    public Post likePost(Long postId, Long userId) throws PostException, UserException {
        if(!userRepository.existsById(userId)){
            throw new UserException("User not found.");
        }
        if(!postRepository.existsById(postId)){
            throw new PostException("Post not found.");
        }
        Post post = postRepository.getReferenceById(postId);
        User user = userRepository.getReferenceById(userId);

        if(post.getLikedBy().contains(user)){
            throw new UserException("User already like this post.");
        }

        post.getLikedBy().add(user);
        return postRepository.save(post);
    }

    @Override
    public Post dislikePost(Long postId, Long userId) throws PostException, UserException {
        if(!userRepository.existsById(userId)){
            throw new UserException("User not found.");
        }
        if(!postRepository.existsById(postId)){
            throw new PostException("Post not found.");
        }
        Post post = postRepository.getReferenceById(postId);
        User user = userRepository.getReferenceById(userId);

        if(!post.getLikedBy().contains(user)){
            throw new UserException("User has not liked this post.");
        }
        post.getLikedBy().remove(user);
        return postRepository.save(post);
    }
}
