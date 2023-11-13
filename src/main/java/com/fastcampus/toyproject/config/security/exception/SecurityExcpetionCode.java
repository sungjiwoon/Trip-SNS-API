package com.fastcampus.toyproject.config.security.exception;

import com.fastcampus.toyproject.common.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SecurityExcpetionCode implements ExceptionCode {

    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "토큰에 권한 정보가 없습니다."),
    BAD_TOKEN(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "잘 못 된 JWT 토큰입니다."),
    INTENAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "Security Context에 인증 정보가 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String msg;
}
