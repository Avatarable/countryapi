package com.klashatask.countryapi.controller;

import com.klashatask.countryapi.enums.ResponseCode;
import com.klashatask.countryapi.exceptions.CustomException;
import com.klashatask.countryapi.models.BaseResponse;
import com.klashatask.countryapi.models.Error;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ApiExceptionHandler {

    private final MessageSource messageSource;

    private static BaseResponse responseBuilder(String message) {
        return BaseResponse.builder().message(message).errors(Collections.emptyList()).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse> handleValidationException(MethodArgumentNotValidException e){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        BindingResult result = e.getBindingResult();
        List<FieldError> errorList = result.getFieldErrors();

        List<Error> errors = new ArrayList<>();
        for (FieldError fieldError : errorList) {
            errors.add(new Error(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return ResponseEntity.status(status).body(BaseResponse.builder().message(status.getReasonPhrase()).errors(errors).build());
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException e) {
        HttpStatus httpStatus = resolveHttpStatus(e.getCode());
        return ResponseEntity.status(httpStatus).body(responseBuilder(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> handleException(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.internalServerError().body(responseBuilder(messageSource.getMessage("Exception.500", null, LocaleContextHolder.getLocale())));
    }

    public HttpStatus resolveHttpStatus(ResponseCode code) {
        return switch (code) {
            case NO_RECORD_FOUND -> HttpStatus.NOT_FOUND;
            case INVALID_INPUT, ALREADY_EXIST -> HttpStatus.BAD_REQUEST;
            case EXPIRED ->  HttpStatus.resolve(410);
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
