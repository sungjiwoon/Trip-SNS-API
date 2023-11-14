package com.fastcampus.toyproject.domain.user.exception;

import com.fastcampus.toyproject.common.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum UserExceptionCode implements ExceptionCode {

    EXSITED_EMAIL(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "중복 된 이메일이 있습니다."),
    WRONG_PASSWORD(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "잘 못 된 비밀번호 입니다."),
    NO_SUCH_EMAIL(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "존재 하지 않는 이메일 입니다.")

    ;

    private HttpStatus status;
    private String code;
    private String msg;

}
