package com.klashatask.countryapi.remote.request;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StateRequest {
    private String state;
}
