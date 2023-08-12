package com.klashatask.countryapi.remote.response;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Data
public class ClientResponse<T extends Object> {
    private boolean error;
    private String msg;
    private T data;
}
