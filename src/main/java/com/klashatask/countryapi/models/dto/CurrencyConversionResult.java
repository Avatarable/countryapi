package com.klashatask.countryapi.models.dto;

import lombok.Builder;

@Builder
public class CurrencyConversionResult {
    private String sourceCurrency;
    private String targetCurrency;
    private String convertedAmount;
}
