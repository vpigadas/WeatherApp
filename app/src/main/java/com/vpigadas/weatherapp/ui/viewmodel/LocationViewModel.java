package com.vpigadas.weatherapp.ui.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.vpigadas.weatherapp.network.ServerClient;
import com.vpigadas.weatherapp.network.models.weather.WeatherResponse;

public class LocationViewModel extends AndroidViewModel {

    private MutableLiveData<WeatherResponse> weatherStream;

    public LocationViewModel(@NonNull Application application) {
        super(application);

        weatherStream = new MutableLiveData<>();
    }

    public MutableLiveData<WeatherResponse> getWeatherStream() {
        return weatherStream;
    }

    public void getWeatherFor(@Nullable String location) {
        if (!TextUtils.isEmpty(location)) {
            ServerClient.getIntance(getApplication()).getWeather(location, weatherStream);
        }
    }
}
