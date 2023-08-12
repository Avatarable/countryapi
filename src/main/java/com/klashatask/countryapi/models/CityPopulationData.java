package com.klashatask.countryapi.models;

import com.klashatask.countryapi.remote.response.PopulationCount;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CityPopulationData {
    private String city;
    private long population;
}