package com.klashatask.countryapi.remote.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CountryCapitalResponse {
    private String name;
    private String capital;
    private String iso2;
    private String iso3;
}
