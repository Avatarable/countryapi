package com.klashatask.countryapi.remote.request;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CountryRequest {
    private String country;
}
