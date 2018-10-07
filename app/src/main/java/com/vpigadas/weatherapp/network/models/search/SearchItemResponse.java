package com.vpigadas.weatherapp.network.models.search;

import java.util.List;

public class SearchItemResponse {

    private List<SearchDataResponse> areaName;
    private List<SearchDataResponse> country;
    private List<SearchDataResponse> region;

    public List<SearchDataResponse> getAreaName() {
        return areaName;
    }

    public List<SearchDataResponse> getCountry() {
        return country;
    }

    public List<SearchDataResponse> getRegion() {
        return region;
    }

    @Override
    public String toString() {
        return "SearchItemResponse{" +
                "areaName=" + areaName +
                ", country=" + country +
                ", region=" + region +
                '}';
    }
}
