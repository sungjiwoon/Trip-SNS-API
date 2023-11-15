package com.fastcampus.toyproject.domain.reply.exception;

public class ReplyException extends RuntimeException {

    private final ReplyExceptionCode code;

    public ReplyException(ReplyExceptionCode code) {
        super(code.getMessage());
        this.code = code;
    }


}
