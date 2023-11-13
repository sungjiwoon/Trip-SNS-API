package com.fastcampus.toyproject.common.exception;

import static com.fastcampus.toyproject.common.exception.DefaultExceptionCode.BAD_REQUEST;
import static com.fastcampus.toyproject.common.exception.DefaultExceptionCode.INTERNAL_SERVER_ERROR;

import com.fastcampus.toyproject.common.dto.ErrorResponseDTO;
import com.fastcampus.toyproject.common.dto.ResponseDTO;
import com.fastcampus.toyproject.domain.itinerary.exception.ItineraryException;
import com.fastcampus.toyproject.domain.trip.exception.TripException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
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
                DefaultException.class})
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

        return new ResponseEntity<>(
            ErrorResponseDTO.error(BAD_REQUEST),
            HttpStatus.BAD_REQUEST
        );
    }

    /**
     * @Valid 를 통해 나타나는 예외 처리
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(value = {
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<ErrorResponseDTO> handleArgumentNotVaildException(
            MethodArgumentNotValidException e, HttpServletRequest request
    ) {
        log.error("MethodArgumentNotValid error url : {}, message : {}",
                request.getRequestURI(),
                e.getMessage()
        );

        List<String> msgList = e.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        return new ResponseEntity<>(
                new ErrorResponseDTO(
                        BAD_REQUEST.getStatus(),
                        BAD_REQUEST.getCode(),
                        String.join(", ", msgList)
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    /**
     * 기타 예외 처리
     * @param e
     * @param request
     * @return
     */

    @ExceptionHandler(value = {
        Exception.class, RuntimeException.class
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

        return new ResponseEntity<>(
            ErrorResponseDTO.error(INTERNAL_SERVER_ERROR),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
