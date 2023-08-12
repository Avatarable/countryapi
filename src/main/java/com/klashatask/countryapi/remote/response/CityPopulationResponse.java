package com.klashatask.countryapi.remote.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Builder
public class CityPopulationResponse {
    private String city;
    private String country;
    private List<PopulationCount> populationCounts;
}
