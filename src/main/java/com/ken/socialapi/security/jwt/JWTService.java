package com.ken.socialapi.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ken.socialapi.exceptions.AuthorizationFailException;
import com.ken.socialapi.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JWTService {

    @Value("${jwt.api.secret}")
    private String secret;

    @Value("${jwt.api.issuer}")
    private String issuer;

    public ResponseEntity<String> generateToken(User user) throws AuthorizationFailException {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
             String jwt = JWT.create()
                    .withIssuer(issuer)
                    .withSubject(user.getEmail())
                    .withClaim("id", user.getId())
                    .withExpiresAt(expirationTime())
                    .sign(algorithm);
            return ResponseEntity.ok(jwt);
        }catch (JWTCreationException exception){
            throw new AuthorizationFailException("Error creating a token.");
        }

    }

    private Instant expirationTime(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }


    public String getSubject(String token) throws AuthorizationFailException {
        if(token == null){
            throw new AuthorizationFailException("The token cannot be null.");
        }
        DecodedJWT decodedJWT;
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            decodedJWT = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build()
                    .verify(token);
        }catch (JWTVerificationException exception){
            throw new AuthorizationFailException("The token is not valid.");
        }

        assert decodedJWT != null;
        return decodedJWT.getSubject();
    }
}
