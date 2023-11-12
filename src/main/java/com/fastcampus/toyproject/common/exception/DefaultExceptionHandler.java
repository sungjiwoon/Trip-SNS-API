package com.fastcampus.toyproject.common.exception;

import static com.fastcampus.toyproject.common.exception.DefaultExceptionCode.BAD_REQUEST;

import com.fastcampus.toyproject.common.dto.ErrorResponseDTO;
import com.fastcampus.toyproject.common.dto.ResponseDTO;
import com.fastcampus.toyproject.domain.itinerary.exception.ItineraryException;
import com.fastcampus.toyproject.domain.trip.exception.TripException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class DefaultExceptionHandler {

    /**
     * 예상한 예외들 잡는 곳
     *
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(
        value = {TripException.class, ItineraryException.class,
                DefaultException.class, RuntimeException.class})
    public ResponseEntity<ErrorResponseDTO> handleDefaultException(
        DefaultException e,
        HttpServletRequest request
    ) {
        log.error("error!!!! status : {} , errorCode : {}, message : {}, url : {}",
            e.getErrorCode().getStatus(),
            e.getErrorCode().getCode(),
            e.getErrorCode().getMsg(),
            request.getRequestURI()
        );

        return new ResponseEntity<>(
                ErrorResponseDTO.error(e.getErrorCode()),
                e.getErrorCode().getStatus()
        );
    }

    /**
     * 커스텀 에러를 제외한 badRequset 만 중점으로 처리하는 에러 핸들러
     *
     * @param
     * @param request
     * @return
     */
    @ExceptionHandler(value = {
        HttpRequestMethodNotSupportedException.class,
        HttpMessageNotReadableException.class,
        InvalidFormatException.class
    })
    public ResponseEntity<ErrorResponseDTO> handleBadRequest(
        Exception e, HttpServletRequest request
    ) {
        log.error("request error url : {}, message : {}",
            request.getRequestURI(),
            e.getMessage()
        );

        ExceptionCode badError = BAD_REQUEST;

        return new ResponseEntity<>(
            ErrorResponseDTO.error(badError),
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = {
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<ErrorResponseDTO> handleArgumentNotVaildException(
            MethodArgumentNotValidException e, HttpServletRequest request
    ) {
        log.error("request error url : {}, message : {}",
                request.getRequestURI(),
                e.getMessage()
        );

        return new ResponseEntity<>(
                ErrorResponseDTO.error(e),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = {
        Exception.class
    })
    public ResponseEntity<ErrorResponseDTO> handleException(
        Exception e, HttpServletRequest request
    ) {
        log.error("exception error class : {}, url : {}, message : {}, trace : {}",
            e.getClass(),
            request.getRequestURI(),
            e.getMessage(),
            e.getStackTrace()
        );

        ExceptionCode serverError = DefaultExceptionCode.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<>(
            ErrorResponseDTO.error(serverError),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
