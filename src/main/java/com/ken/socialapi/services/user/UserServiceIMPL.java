package com.ken.socialapi.services.user;

import com.ken.socialapi.exceptions.UserException;
import com.ken.socialapi.models.User;
import com.ken.socialapi.repository.UserRepository;
import com.ken.socialapi.requests.UserRequest;
import com.ken.socialapi.responses.UserResponse;
import com.ken.socialapi.services.file.FileServiceIMPL;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
public class UserServiceIMPL implements IUserService {

    private final UserRepository userRepository;
    private final FileServiceIMPL fileServiceIMPL;

    public UserServiceIMPL(UserRepository userRepository, FileServiceIMPL fileServiceIMPL){
        this.userRepository = userRepository;
        this.fileServiceIMPL = fileServiceIMPL;
    }


    @Override
    public List<User> allUsers(){
        return userRepository.findAll();
    }

    @Override
    public User uploadImage(MultipartFile image, Long userId) throws UserException {
        if(!userRepository.existsById(userId)){
            throw new UserException("User not found.");
        }
        User user = userRepository.getReferenceById(userId);
        user.setImage(fileServiceIMPL.getUserFilePath(image, user));
        return userRepository.save(user);
    }

    @Override
    public User findOne(Long id) throws UserException {
        if(!userRepository.existsById(id)){
            throw new UserException("User not found.");
        }
        return userRepository.getReferenceById(id);
    }

    @Override
    public User updateUser(Long id, UserRequest request) throws UserException {
        if(!userRepository.existsById(id)){
            throw new UserException("User not found.");
        }
        User user = userRepository.getReferenceById(id);
        if(request.getUsername() != null) {
            user.setUsername(request.getUsername());
        }
        if(request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }
        if(request.getBio() != null) {
            user.setBio(request.getBio());
        }
        if(request.getWebsite() != null) {
            user.setWebsite(request.getWebsite());
        }
        if(request.getGender() != null) {
            user.setGender(request.getGender());
        }
        return userRepository.save(user);
    }

    @Override
    public UserResponse deleteUser(Long id) throws UserException {
        if(!userRepository.existsById(id)){
            throw new UserException("User not found.");
        }
        userRepository.deleteById(id);
        return new UserResponse("The user has been deleted.");
    }

    @Override
    public UserDetails findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public List<User> findAllByUsernameContaining(String query) {
        return userRepository.findAllByUsernameContaining(query.toLowerCase());
    }
}
