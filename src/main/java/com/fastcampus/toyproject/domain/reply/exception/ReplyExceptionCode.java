package com.fastcampus.toyproject.domain.reply.exception;

import com.fastcampus.toyproject.common.exception.ExceptionCode;
import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReplyExceptionCode implements ExceptionCode {
    REPLY_NOT_FOUND(HttpStatus.NOT_FOUND, "REPLY_NOT_FOUND", "댓글을 찾을 수 없습니다."),
    INVALID_REPLY_OPERATION(HttpStatus.BAD_REQUEST, "INVALID_REPLY_OPERATION", "댓글 작업이 유효하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String msg;
}
