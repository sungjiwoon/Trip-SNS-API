package com.fastcampus.toyproject.domain.trip.exception;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import com.fastcampus.toyproject.common.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum TripExceptionCode implements ExceptionCode {
    NO_SUCH_TRIP(UNPROCESSABLE_ENTITY, "NO_SUCH_TRIP", "해당하는 Trip이 없습니다."),
    NOT_MATCH_BETWEEN_USER_AND_TRIP(UNPROCESSABLE_ENTITY, "NOT_MATCH_BETWEEN_USER_AND_TRIP", "유저의 여행 정보가 일치하지 않습니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String msg;

}
