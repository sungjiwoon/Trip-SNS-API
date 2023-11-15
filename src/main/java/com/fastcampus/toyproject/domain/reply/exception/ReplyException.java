package com.fastcampus.toyproject.domain.reply.exception;

import com.fastcampus.toyproject.common.exception.DefaultException;
import com.fastcampus.toyproject.common.exception.ExceptionCode;
import lombok.Getter;

@Getter
public class ReplyException extends DefaultException {
    public ReplyException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
