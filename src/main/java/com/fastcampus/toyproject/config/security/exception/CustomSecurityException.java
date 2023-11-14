package com.fastcampus.toyproject.config.security.exception;

import com.fastcampus.toyproject.common.exception.DefaultException;
import com.fastcampus.toyproject.common.exception.ExceptionCode;

public class CustomSecurityException extends DefaultException {

    ExceptionCode errorCode;
    String errorMsg;

    public CustomSecurityException(SecurityExcpetionCode errorCode) {
        super();
        this.errorCode = errorCode;
        this.errorMsg = errorCode.getMsg();
    }
}
