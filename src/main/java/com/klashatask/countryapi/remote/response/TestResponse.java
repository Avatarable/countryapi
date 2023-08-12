package com.klashatask.countryapi.remote.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TestResponse {
    private boolean error;
    private String msg;
    private Object data;

    @Override
    public String toString() {
        return "TestResponse{" +
                "error=" + error +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
