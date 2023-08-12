package com.klashatask.countryapi.remote.response;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class CountryPopulationResponse {
    private String country;
    private String code;
    private String iso3;
    private List<PopulationCount> populationCounts;
}
