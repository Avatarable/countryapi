package com.klashatask.countryapi.controller;

import com.klashatask.countryapi.models.BaseResponse;
import com.klashatask.countryapi.models.CityPopulationData;
import com.klashatask.countryapi.models.dto.CurrencyConversionResult;
import com.klashatask.countryapi.models.dto.CurrencyConvertRequest;
import com.klashatask.countryapi.service.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/country")
@RequiredArgsConstructor
public class CountryController {
    private final CountryService countryService;

    BaseResponse response = BaseResponse.builder().errors(Collections.emptyList()).build();

    @Operation(summary = "Get most populated cities in a country")
    @GetMapping("/most-populated")
    public ResponseEntity<BaseResponse> getMostPopulatedCities(@Valid @RequestParam int n){
        response.setData(countryService.getMostPopulatedCities(n));
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get info about a country")
    @GetMapping("/{country}")
    public ResponseEntity<BaseResponse> getCountryInfo(@Valid @PathVariable String country){
        response.setData(countryService.getCountryInfo(country));
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all states and cities in a country")
    @GetMapping("/{country}/states-cities")
    public ResponseEntity<BaseResponse> getStatesAndCities(@PathVariable String country){
        Map<String, List<String>> statesAndCities = countryService.getStatesAndCities(country);
        response.setData(statesAndCities);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Convert amount in country's currency to a another currency")
    @PostMapping("/convert")
    public ResponseEntity<BaseResponse> convertCurrency(@RequestParam CurrencyConvertRequest request) {
        response.setData(countryService.convertCurrency(request));
        return ResponseEntity.ok(response);
    }

}
