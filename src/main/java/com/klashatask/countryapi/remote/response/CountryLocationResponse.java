package com.klashatask.countryapi.remote.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CountryLocationResponse {
    private String name;
    private String iso2;
    @JsonProperty("long")
    private int longitude;
    @JsonProperty("lat")
    private int latitude;
}
