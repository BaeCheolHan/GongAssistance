package com.herren.gongassistance.base.exception;

import com.herren.gongassistance.base.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    @ExceptionHandler(value = GongAssistanceException.class)
    public ResponseEntity<BaseResponse> handleGongAssistanceException(GongAssistanceException e) {
        BaseResponse response = BaseResponse.builder()
                .code(e.getErrorCode())
                .message(e.getErrorCode().getMessage())
                .build();

        switch (e.getErrorCode()) {
            case E0002:
            case E0006:
            case E0007:
                log.error(e.getErrorCode().getMessage());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            case E0003:
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            case E0005:
                log.error(e.getErrorCode().getMessage());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<BaseResponse> BindExceptionHandler(BindException e) {
        BaseResponse response = BaseResponse.builder()
                .code(GongAssistanceCode.E0004)
                .message(GongAssistanceCode.E0004.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<BaseResponse> internalServerError(Exception e) {

        BaseResponse response = BaseResponse.builder()
                .code(GongAssistanceCode.INTERNAL_ERROR)
                .message(GongAssistanceCode.INTERNAL_ERROR.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}
