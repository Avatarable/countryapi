package com.klashatask.countryapi.service;

import com.klashatask.countryapi.models.CityPopulationData;
import com.klashatask.countryapi.remote.ApiClient;
import com.klashatask.countryapi.remote.response.CityPopulationResponse;
import com.klashatask.countryapi.remote.response.ClientResponse;
import com.klashatask.countryapi.remote.response.PopulationCount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryServiceTest {

    @Mock
    private ApiClient apiClient;

    @InjectMocks
    private CountryService underTest;

    @Test
    void getMostPopulatedCities() {
        List<PopulationCount> populationCounts = new ArrayList<>();
        populationCounts.add(PopulationCount.builder().year(2023).value(1999670).build());

        CityPopulationResponse mockCityPopulationResponse = CityPopulationResponse.builder()
                .city("lagos").country("nigeria")
                .populationCounts(populationCounts)
                .build();
        var cityPopulationResp = new ClientResponse<List<CityPopulationResponse>>();
        cityPopulationResp.setError(false);
        cityPopulationResp.setData(List.of(mockCityPopulationResponse));

        when(apiClient.filterCitiesAndPopulation(any())).thenReturn(cityPopulationResp);

        List<CityPopulationData> result = underTest.getMostPopulatedCities(2);

        assertThat(result).isNotNull().hasSizeLessThanOrEqualTo(2);
        assertThat(result).extracting(CityPopulationData::getPopulation)
                .isSortedAccordingTo(Comparator.reverseOrder());
    }
}