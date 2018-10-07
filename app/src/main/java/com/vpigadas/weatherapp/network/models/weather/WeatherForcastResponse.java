package com.vpigadas.weatherapp.network.models.weather;

public class WeatherForcastResponse {

    private String date;
    private String maxtempC;
    private String maxtempF;
    private String mintempC;
    private String mintempF;

    public String getDate() {
        return date;
    }

    public String getMaxtempC() {
        return maxtempC;
    }

    public String getMaxtempF() {
        return maxtempF;
    }

    public String getMintempC() {
        return mintempC;
    }

    public String getMintempF() {
        return mintempF;
    }

    @Override
    public String toString() {
        return "WeatherForcastResponse{" +
                "date='" + date + '\'' +
                ", maxtempC='" + maxtempC + '\'' +
                ", maxtempF='" + maxtempF + '\'' +
                ", mintempC='" + mintempC + '\'' +
                ", mintempF='" + mintempF + '\'' +
                '}';
    }
}
