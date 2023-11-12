package com.fastcampus.toyproject.common.dto;

import static com.fastcampus.toyproject.common.exception.DefaultExceptionCode.INTERNAL_SERVER_ERROR;

import com.fastcampus.toyproject.common.exception.DefaultExceptionCode;
import com.fastcampus.toyproject.common.exception.ExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class ErrorResponseDTO {

    private final HttpStatus status;
    private final String code;
    private final String msg;

    public static ErrorResponseDTO error(ExceptionCode exceptionCode) {
        return new ErrorResponseDTO(
                exceptionCode.getStatus(),
                exceptionCode.getCode(),
                exceptionCode.getMsg()
        );
    }

    public static ErrorResponseDTO error() {
        return new ErrorResponseDTO(
                INTERNAL_SERVER_ERROR.getStatus(),
                INTERNAL_SERVER_ERROR.getCode(),
                INTERNAL_SERVER_ERROR.getMsg()
        );
    }


}
