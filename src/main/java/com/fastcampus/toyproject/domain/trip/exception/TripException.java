package com.fastcampus.toyproject.domain.trip.exception;


import com.fastcampus.toyproject.common.exception.DefaultException;
import com.fastcampus.toyproject.common.exception.ExceptionCode;
import lombok.Getter;

@Getter
public class TripException extends DefaultException {

    public TripException(ExceptionCode errorCode) {
        super(errorCode);
    }
}
