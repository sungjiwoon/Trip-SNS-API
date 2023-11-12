package com.fastcampus.toyproject.domain.user.exception;

public class BadCredentialsException extends RuntimeException{


    public BadCredentialsException(String s) {
        super(s);
    }
}
