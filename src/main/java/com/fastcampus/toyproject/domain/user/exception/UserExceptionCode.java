package com.fastcampus.toyproject.domain.user.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.fastcampus.toyproject.common.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum UserExceptionCode implements ExceptionCode {

    EXSITED_EMAIL(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "중복된 이메일이 있습니다."),
    WRONG_PASSWORD(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "잘못된 비밀번호 입니다."),
    NO_SUCH_EMAIL(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "존재 하지 않는 이메일 입니다."),
    NO_LIKE_TRIP(NOT_FOUND, "NO_LIKE_TRIP", "좋아요 여행 정보가 없습니다.")

    ;

    private HttpStatus status;
    private String code;
    private String msg;

}
