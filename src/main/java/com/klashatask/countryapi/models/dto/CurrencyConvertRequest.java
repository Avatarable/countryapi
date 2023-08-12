package com.klashatask.countryapi.models.dto;

import lombok.Data;

@Data
public class CurrencyConvertRequest {
    private String country;
    private String targetCurrency;
    private double amount;
}
