package com.klashatask.countryapi.remote.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorModel {
    private String code;
    private String message;
}
