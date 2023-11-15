package com.fastcampus.toyproject.domain.reply.exception;

public enum ReplyExceptionCode {
    REPLY_NOT_FOUND("댓글을 찾을 수 없습니다."),
    INVALID_REPLY_OPERATION("댓글 작업이 유효하지 않습니다.");

    private final String message;

    ReplyExceptionCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
