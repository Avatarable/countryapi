package com.klashatask.countryapi.remote;

import com.klashatask.countryapi.remote.request.CitiesWithPopulationDataRequest;
import com.klashatask.countryapi.remote.request.CountryRequest;
import com.klashatask.countryapi.remote.request.StateRequest;
import com.klashatask.countryapi.remote.response.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

@HttpExchange(url = "/api/v0.1/countries", accept = MediaType.APPLICATION_JSON_VALUE, contentType = MediaType.APPLICATION_JSON_VALUE )
public interface ApiClient {
    @PostExchange(value = "/population")
    ClientResponse<CountryPopulationResponse> getCountryPopulation(@RequestBody CountryRequest request);

    @PostExchange(value = "/population/cities/filter")
    ClientResponse<List<CityPopulationResponse>> filterCitiesAndPopulation(@RequestBody CitiesWithPopulationDataRequest request);

    @PostExchange(value = "/currency")
    ClientResponse<CountryCurrencyResponse> getCountryCurrency(@RequestBody CountryRequest request);

    @PostExchange(value = "/capital")
    ClientResponse<CountryCapitalResponse> getCountryCapital(@RequestBody CountryRequest request);

    @PostExchange(value = "/location")
    ClientResponse<CountryLocationResponse> getCountryLocation(@RequestBody CountryRequest request);

    @PostExchange(value = "/states")
    ClientResponse<StateResponseData> getCountryStates(@RequestBody CountryRequest request);

    @PostExchange(value = "/state/cities")
    ClientResponse<List<String>> getStateCities(@RequestBody StateRequest state);
}
