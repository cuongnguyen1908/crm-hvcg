package com.hvcg.api.crm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(NotFoundException e) {
        ErrorResponse error =
                new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                        e.getMessage(), ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exc) {
        ErrorResponse error =
                new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage(), ZonedDateTime.now(ZoneId.of("Z")));

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
