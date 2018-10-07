package com.vpigadas.weatherapp.network.models.weather;

public class WeatherDataResponse {

    private String value;

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "SearchDataResponse{" +
                "value='" + value + '\'' +
                '}';
    }
}
