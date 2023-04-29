package com.ken.socialapi.services.auth;

import com.ken.socialapi.exceptions.AuthorizationFailException;
import com.ken.socialapi.exceptions.UserException;
import com.ken.socialapi.models.User;
import com.ken.socialapi.requests.LoginRequest;
import com.ken.socialapi.requests.SignupRequest;
import com.ken.socialapi.responses.JWTResponse;
import org.springframework.stereotype.Service;

@Service
public interface IAuthService {

    JWTResponse loginUser(LoginRequest request) throws UserException, AuthorizationFailException;

    User registerUser(SignupRequest request) throws UserException;
}
