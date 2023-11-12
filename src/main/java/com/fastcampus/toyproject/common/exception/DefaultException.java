package com.fastcampus.toyproject.common.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DefaultException extends RuntimeException {

    ExceptionCode errorCode;
    String errorMsg;

    public DefaultException(DefaultExceptionCode errorCode, String errorMsg) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public DefaultException(DefaultExceptionCode errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
        this.errorMsg = errorCode.getMsg();
    }
}
