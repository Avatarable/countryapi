package com.klashatask.countryapi.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@Builder
public class BaseResponse {
    private String message = "Successful";
    private Object data;
    private List<Error> errors;
}
