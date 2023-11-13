package com.fastcampus.toyproject.domain.itinerary.exception;


import com.fastcampus.toyproject.common.exception.DefaultException;
import com.fastcampus.toyproject.common.exception.DefaultExceptionCode;
import com.fastcampus.toyproject.common.exception.ExceptionCode;
import lombok.Getter;

@Getter
public class ItineraryException extends DefaultException {

    ExceptionCode errorCode;
    String errorMsg;


    public ItineraryException(ItineraryExceptionCode errorCode) {
        super();
        this.errorCode = errorCode;
        this.errorMsg = errorCode.getMsg();
    }
}
