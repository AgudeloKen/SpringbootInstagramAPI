package com.ken.socialapi.services.auth;

import com.ken.socialapi.exceptions.AuthorizationFailException;
import com.ken.socialapi.exceptions.UserException;
import com.ken.socialapi.models.User;
import com.ken.socialapi.repository.UserRepository;
import com.ken.socialapi.requests.LoginRequest;
import com.ken.socialapi.requests.SignupRequest;
import com.ken.socialapi.responses.JWTResponse;
import com.ken.socialapi.security.jwt.JWTService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceIMPL implements IAuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    private final JWTService jwtService;


    public AuthServiceIMPL(UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JWTService jwtService){
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public JWTResponse loginUser(LoginRequest request) throws UserException, AuthorizationFailException {
        if(!userRepository.existsByEmail(request.getEmail())){
            throw new UserException("User not found.");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        Authentication userAuth = authenticationManager.authenticate(authentication);

        String token = jwtService.generateToken((User) userAuth.getPrincipal()).getBody();

        return new JWTResponse(token);
    }

    @Override
    public User registerUser(SignupRequest request) throws UserException {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new UserException("Email already exist.");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername().toLowerCase());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setImage(null);

        return userRepository.save(user);
    }
}
