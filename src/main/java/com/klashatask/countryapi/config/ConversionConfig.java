package com.klashatask.countryapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "conversion")
public class ConversionConfig {

    private Map<String, Map<String, Double>> rates = new HashMap<>();

    public Map<String, Map<String, Double>> getRates() {
        return rates;
    }

    public void setRates(Map<String, Map<String, Double>> rates) {
        this.rates = rates;
    }
}
