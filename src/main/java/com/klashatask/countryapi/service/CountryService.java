package com.klashatask.countryapi.service;

import com.google.gson.Gson;
import com.klashatask.countryapi.config.ConversionConfig;
import com.klashatask.countryapi.enums.ResponseCode;
import com.klashatask.countryapi.exceptions.CustomException;
import com.klashatask.countryapi.models.CityPopulationData;
import com.klashatask.countryapi.models.dto.CountryInfo;
import com.klashatask.countryapi.models.dto.CurrencyConversionResult;
import com.klashatask.countryapi.models.dto.CurrencyConvertRequest;
import com.klashatask.countryapi.models.dto.Location;
import com.klashatask.countryapi.remote.ApiClient;
import com.klashatask.countryapi.remote.request.CitiesWithPopulationDataRequest;
import com.klashatask.countryapi.remote.request.CountryRequest;
import com.klashatask.countryapi.remote.request.StateRequest;
import com.klashatask.countryapi.remote.response.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CountryService {
    private final ApiClient apiClient;
    private final MessageSource messageSource;
    private ConversionConfig conversionConfig;

    public List<CityPopulationData> getMostPopulatedCities(int n) {
        List<CityPopulationResponse> cityPopulationResponse = new ArrayList<>();
        List<CityPopulationData> data = new ArrayList<>();
        List<String> countries = List.of("italy", "new zealand", "ghana");

        for(String country:countries){
            var requestData = CitiesWithPopulationDataRequest.builder().country(country).build();
            cityPopulationResponse.addAll(apiClient.filterCitiesAndPopulation(requestData).getData());
        }

        for(CityPopulationResponse res : cityPopulationResponse){
            var city = res.getCity();
            List<CityPopulationData> cityData = res.getPopulationCounts().stream().map(er -> {
                var population = er.getValue();
                return CityPopulationData.builder().city(city).population(population).build();
            }).toList();
            data.addAll(cityData);
        }

        data.sort(Comparator.comparing(CityPopulationData::getPopulation).reversed());
        return data.subList(0, Math.min(n, data.size()));
    }

    public CountryInfo getCountryInfo(String country) {
        var countryReq = CountryRequest.builder().country(country).build();

        CountryCapitalResponse capitalResponse = apiClient.getCountryCapital(countryReq).getData();
        CountryLocationResponse locationResponse = apiClient.getCountryLocation(countryReq).getData();
        Location location = Location.builder().latitude(locationResponse.getLatitude()).longitude(locationResponse.getLongitude()).build();
        String currency = apiClient.getCountryCurrency(countryReq).getData().getCurrency();

        List<PopulationCount> populationCounts = (apiClient.getCountryPopulation(countryReq).getData()).getPopulationCounts();
        var latestPopulation = populationCounts.stream()
                .max(Comparator.comparingLong(PopulationCount::getYear))
                .orElseThrow(() -> new CustomException(ResponseCode.NO_RECORD_FOUND, messageSource.getMessage("NotFoundException.message", null, LocaleContextHolder.getLocale())));

        return CountryInfo.builder()
                .currency(currency)
                .capital(capitalResponse.getCapital())
                .iso2(capitalResponse.getIso2())
                .iso3(capitalResponse.getIso3())
                .location(location)
                .population(String.valueOf(latestPopulation.getValue()))
                .build();
    }

    public Map<String, List<String>> getStatesAndCities(String country) {
        Map<String, List<String>> stateAndCities = new HashMap<>();
        var request = CountryRequest.builder().country(country).build();
        List<State> states = apiClient.getCountryStates(request).getData().getStates();

        for(State state:states){
            List<String> cities = apiClient
                    .getStateCities(StateRequest.builder().state(state.getName()).build())
                    .getData();
            stateAndCities.put(state.getName(), cities);
        }
        return stateAndCities;
    }


    public CurrencyConversionResult convertCurrency(CurrencyConvertRequest request) {
        String sourceCurrency = apiClient
                .getCountryCurrency(CountryRequest.builder().country(request.getCountry()).build())
                .getData().getCurrency();

        double conversionRate = getConversionRate(sourceCurrency, request.getTargetCurrency());
        double convertedAmount = request.getAmount() * conversionRate;
        String formattedConvertedAmount = formatCurrency(convertedAmount, request.getTargetCurrency());

        return CurrencyConversionResult.builder()
                .sourceCurrency(sourceCurrency)
                .targetCurrency(request.getTargetCurrency())
                .convertedAmount(formattedConvertedAmount)
                .build();
    }

    private double getConversionRate(String sourceCurrency, String targetCurrency) {
        Map<String, Double> sourceRates = conversionConfig.getRates().get(sourceCurrency);
        if (sourceRates != null) {
            Double conversionRate = sourceRates.get(targetCurrency);
            if (conversionRate != null) {
                return conversionRate;
            }
        }
        throw new CustomException(ResponseCode.NO_RECORD_FOUND, messageSource.getMessage("NotFoundException.message", null, LocaleContextHolder.getLocale()));
    }

    private String formatCurrency(double amount, String currencyCode) {
        Locale locale = new Locale("en", currencyCode);
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        return currencyFormat.format(amount);
    }
}
