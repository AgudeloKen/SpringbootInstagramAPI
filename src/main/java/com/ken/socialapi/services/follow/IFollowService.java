package com.ken.socialapi.services.follow;

import com.ken.socialapi.exceptions.UserException;
import com.ken.socialapi.models.User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface IFollowService {

    User followUser(Long userId, Long followId) throws UserException;
    User unfollowUser(Long userId, Long followId) throws UserException;
    Set<User> getFollowersByUser(Long id) throws UserException;
    Set<User> getFollowingByUser(Long id) throws UserException;
}
