package com.klashatask.countryapi.exceptions;

import com.klashatask.countryapi.enums.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CustomException extends RuntimeException {
    private ResponseCode code;
    private String message;
}
