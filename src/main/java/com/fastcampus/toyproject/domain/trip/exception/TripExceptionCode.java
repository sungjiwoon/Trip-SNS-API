package com.fastcampus.toyproject.domain.trip.exception;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import com.fastcampus.toyproject.common.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum TripExceptionCode implements ExceptionCode {

    NO_SUCH_TRIP(NOT_FOUND, "NO_SUCH_TRIP", "해당하는 여행 정보가 없습니다."),
    TRIP_ALREADY_DELETED(FORBIDDEN, "TRIP_ALREADY_DELETED", "이미 삭제된 여행 정보입니다."),
    NOT_MATCH_BETWEEN_USER_AND_TRIP(FORBIDDEN, "NOT_MATCH_BETWEEN_USER_AND_TRIP", "로그인 유저와 유저의 여행 정보가 일치하지 않습니다.")

    ;

    private final HttpStatus status;
    private final String code;
    private final String msg;

}
