package com.ken.socialapi.services.user;

import com.ken.socialapi.exceptions.UserException;
import com.ken.socialapi.models.User;
import com.ken.socialapi.requests.UserRequest;
import com.ken.socialapi.responses.UserResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface IUserService {

    UserDetails findUserByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findAllByUsernameContaining(String query);
    UserResponse deleteUser(Long id) throws UserException;
    User updateUser(Long id, UserRequest request) throws UserException;
    User findOne(Long id) throws UserException;
    List<User> allUsers();
    User uploadImage(MultipartFile image, Long userId) throws UserException;
}
