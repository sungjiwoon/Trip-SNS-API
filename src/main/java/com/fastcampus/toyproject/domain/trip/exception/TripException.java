package com.fastcampus.toyproject.domain.trip.exception;


import com.fastcampus.toyproject.common.exception.DefaultException;
import com.fastcampus.toyproject.common.exception.ExceptionCode;
import lombok.Getter;

@Getter
public class TripException extends DefaultException {

    ExceptionCode errorCode;
    String errorMsg;

    public TripException(TripExceptionCode errorCode) {
        super();
        this.errorCode = errorCode;
        this.errorMsg = errorCode.getMsg();
    }
}
