package com.fastcampus.toyproject.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum DefaultExceptionCode implements ExceptionCode {


    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR" ,"서버에 오류가 발생했습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "잘못된 입력값 입니다."),
    STARTDATE_IS_LATER_THAN_ENDDATE(HttpStatus.BAD_REQUEST, "STARTDATE_IS_LATER_THAN_ENDDATE", "출발 일정이 도착 일정보다 늦습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String msg;


}
