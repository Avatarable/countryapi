package com.klashatask.countryapi.remote.request;

import lombok.Builder;

@Builder
public class CitiesWithPopulationDataRequest {
    private int limit;
    private String order;
    private String orderBy;
    private String country;
}
