package com.fastcampus.toyproject.domain.itinerary.exception;


import com.fastcampus.toyproject.common.exception.DefaultException;
import com.fastcampus.toyproject.common.exception.DefaultExceptionCode;
import com.fastcampus.toyproject.common.exception.ExceptionCode;
import lombok.Getter;

@Getter
public class ItineraryException extends DefaultException {

    public ItineraryException(ExceptionCode errorCode) {
        super(errorCode);
    }
}
