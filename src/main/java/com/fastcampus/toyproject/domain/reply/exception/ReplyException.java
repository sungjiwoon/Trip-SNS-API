package com.fastcampus.toyproject.domain.reply.exception;

import com.fastcampus.toyproject.common.exception.DefaultException;
import com.fastcampus.toyproject.common.exception.ExceptionCode;
import lombok.Getter;

@Getter
public class ReplyException extends DefaultException {

    ExceptionCode exceptionCode;
    String errorMsg;

    public ReplyException(ReplyExceptionCode exceptionCode) {
        super(exceptionCode);
        this.exceptionCode = exceptionCode;
        this.errorMsg = exceptionCode.getMsg();
    }



}
