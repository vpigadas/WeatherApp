package com.vpigadas.weatherapp.network.models.weather;

public class WeatherResponse {

    private WeatherItemResponse data;

    public WeatherItemResponse getData() {
        return data;
    }

    @Override
    public String toString() {
        return "WeatherResponse{" +
                "data=" + data +
                '}';
    }
}
