package com.klashatask.countryapi.remote.response;

import lombok.Getter;

import java.util.List;

@Getter
public class StateResponseData {
    private String name;
    private String iso3;
    private List<State> states;
}
