package com.vpigadas.weatherapp.network;

import com.vpigadas.weatherapp.network.models.search.SearchResponse;
import com.vpigadas.weatherapp.network.models.weather.WeatherResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ServerInterface {

    @GET("weather.ashx")
    Observable<WeatherResponse> getWeather(@QueryMap Map<String, Object> query);

    @GET("search.ashx")
    Observable<SearchResponse> getLocations(@QueryMap Map<String, Object> query);


}
