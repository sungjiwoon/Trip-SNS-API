package com.fastcampus.toyproject.domain.trip.exception;


import com.fastcampus.toyproject.common.exception.DefaultException;
import com.fastcampus.toyproject.common.exception.ExceptionCode;
import lombok.Getter;

@Getter
public class TripException extends DefaultException {

    ExceptionCode errorCode;

    public TripException(TripExceptionCode errorCode) {
        super(errorCode);
    }
}
