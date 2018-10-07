package com.vpigadas.weatherapp.network.models.weather;

import java.util.List;

public class WeatherItemResponse {

    private List<WeatherConditionResponse> current_condition;
    private List<WeatherForcastResponse> weather;

    public List<WeatherConditionResponse> getCurrent_condition() {
        return current_condition;
    }

    public List<WeatherForcastResponse> getWeather() {
        return weather;
    }

    @Override
    public String toString() {
        return "WeatherItemResponse{" +
                "current_condition=" + current_condition +
                ", weather=" + weather +
                '}';
    }
}
