package com.klashatask.countryapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCode {
    SUCCESSFUL("00", "Successful"),
    PENDING("01", "Request is pending"),
    REJECT("02", "Request was rejected"),
    CANCEL("03", "Request was cancelled"),
    TIMEOUT("04", "Request timed out"),
    NO_RECORD_FOUND("05", "No record was found"),
    INVALID_INPUT("06", "Invalid data supplied"),
    ALREADY_EXIST("07", "Already exist"),
    UNABLE_TO_CONNECT("91", "We are unable to connect to service"),
    EXPIRED("92", "Request processing date has expired"),
    UNABLE_TO_PROCESS_REQUEST("99", "We are unable to process your request!");

    private final String code;
    private final String message;
}
