package com.ken.socialapi.exceptions;

public class AuthorizationFailException extends  Exception{

    public AuthorizationFailException(String message){
        super(message);
    }
}
