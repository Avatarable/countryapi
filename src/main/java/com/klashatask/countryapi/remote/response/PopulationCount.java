package com.klashatask.countryapi.remote.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PopulationCount {
    private long year;
    private long value;
}
