package com.fastcampus.toyproject.config.security.exception;

import com.fastcampus.toyproject.common.exception.DefaultException;
import com.fastcampus.toyproject.common.exception.ExceptionCode;

public class CustomSecurityException extends DefaultException {

    public CustomSecurityException(ExceptionCode errorCode) {
        super(errorCode);
    }
}
