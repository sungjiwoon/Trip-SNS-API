package com.fastcampus.toyproject.domain.user.exception;

import com.fastcampus.toyproject.common.exception.DefaultException;
import com.fastcampus.toyproject.common.exception.ExceptionCode;
import com.fastcampus.toyproject.config.security.exception.SecurityExcpetionCode;

public class UserException extends DefaultException {
    public UserException(ExceptionCode errorCode) {
        super(errorCode);
    }
}
