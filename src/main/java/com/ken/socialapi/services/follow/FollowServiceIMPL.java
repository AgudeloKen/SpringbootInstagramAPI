package com.ken.socialapi.services.follow;

import com.ken.socialapi.exceptions.UserException;
import com.ken.socialapi.models.User;
import com.ken.socialapi.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class FollowServiceIMPL implements IFollowService {

    private final UserRepository userRepository;

    public FollowServiceIMPL(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public User followUser(Long userId, Long followId) throws UserException {
        if(!userRepository.existsById(userId) || !userRepository.existsById(followId)){
            throw new UserException("User not found.");
        }

        User user = userRepository.getReferenceById(userId);
        User userFollow = userRepository.getReferenceById(followId);

        userFollow.getFollowers().add(user);
        user.getFollowing().add(userFollow);

        return userRepository.save(user);
    }

    @Override
    public User unfollowUser(Long userId, Long followId) throws UserException {
        if(!userRepository.existsById(userId) || !userRepository.existsById(followId)){
            throw new UserException("User not found.");
        }
        User user = userRepository.getReferenceById(userId);
        User userFollow = userRepository.getReferenceById(followId);

        user.getFollowing().remove(userFollow);
        userFollow.getFollowers().remove(user);

        return userRepository.save(user);
    }

    @Override
    public Set<User> getFollowersByUser(Long id) throws UserException {
        if(!userRepository.existsById(id)){
            throw new UserException("User not found.");
        }
        User user = userRepository.getReferenceById(id);

        return user.getFollowers();
    }

    @Override
    public Set<User> getFollowingByUser(Long id) throws UserException {
        if(!userRepository.existsById(id)){
            throw new UserException("User not found.");
        }
        User user = userRepository.getReferenceById(id);

        return user.getFollowing();
    }
}
