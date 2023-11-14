package com.fastcampus.toyproject.domain.user.exception;

import com.fastcampus.toyproject.common.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum UserExceptionCode implements ExceptionCode {

    EXSITED_EMAIL(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "존재하는 이메일"),
    WRONG_PASSWORD(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "잘 못 된 비밀번호"),
    NO_SUCH_EMAIL(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "존재 하지 않는 이메일")

    ;

    private HttpStatus status;
    private String code;
    private String msg;

}
