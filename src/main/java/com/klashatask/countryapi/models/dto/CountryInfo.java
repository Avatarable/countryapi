package com.klashatask.countryapi.models.dto;

import lombok.Builder;

@Builder
public class CountryInfo {
    private String population;
    private String capital;
    private Location location;
    private String currency;
    private String iso2;
    private String iso3;
}
