package com.fastcampus.toyproject.domain.user.exception;

public class ExistEmailExcpetion extends RuntimeException{

    public ExistEmailExcpetion(String msg) {
        super(msg);
    }
}
