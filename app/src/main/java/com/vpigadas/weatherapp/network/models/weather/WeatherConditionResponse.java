package com.vpigadas.weatherapp.network.models.weather;

import java.util.List;

public class WeatherConditionResponse {

    private String temp_C;
    private String temp_F;
    private String weatherCode;
    private List<WeatherDataResponse> weatherIconUrl;
    private List<WeatherDataResponse> weatherDesc;
    private List<WeatherDataResponse> lang_el;
    private String precipMM;
    private String humidity;
    private String visibility;
    private String pressure;
    private String cloudcover;
    private String FeelsLikeC;
    private String FeelsLikeF;

    public String getTemp_C() {
        return temp_C;
    }

    public String getTemp_F() {
        return temp_F;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public List<WeatherDataResponse> getWeatherIconUrl() {
        return weatherIconUrl;
    }

    public List<WeatherDataResponse> getWeatherDesc() {
        return weatherDesc;
    }

    public List<WeatherDataResponse> getLang_el() {
        return lang_el;
    }

    public String getPrecipMM() {
        return precipMM;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getVisibility() {
        return visibility;
    }

    public String getPressure() {
        return pressure;
    }

    public String getCloudcover() {
        return cloudcover;
    }

    public String getFeelsLikeC() {
        return FeelsLikeC;
    }

    public String getFeelsLikeF() {
        return FeelsLikeF;
    }

    public String getDescription() {
        if (lang_el != null) {
            for (WeatherDataResponse weatherDataResponse : lang_el) {
                return weatherDataResponse.getValue();
            }
        } else if (weatherDesc != null) {
            for (WeatherDataResponse weatherDataResponse : weatherDesc) {
                return weatherDataResponse.getValue();
            }
        }

        return "";
    }

    @Override
    public String toString() {
        return "WeatherConditionResponse{" +
                "temp_C='" + temp_C + '\'' +
                ", temp_F='" + temp_F + '\'' +
                ", weatherCode='" + weatherCode + '\'' +
                ", weatherIconUrl=" + weatherIconUrl +
                ", weatherDesc=" + weatherDesc +
                ", lang_el=" + lang_el +
                ", precipMM='" + precipMM + '\'' +
                ", humidity='" + humidity + '\'' +
                ", visibility='" + visibility + '\'' +
                ", pressure='" + pressure + '\'' +
                ", cloudcover='" + cloudcover + '\'' +
                ", FeelsLikeC='" + FeelsLikeC + '\'' +
                ", FeelsLikeF='" + FeelsLikeF + '\'' +
                '}';
    }
}
