package com.vpigadas.weatherapp.network.models.search;

import java.util.List;

public class SearchResultResponse {

    private List<SearchItemResponse> result;

    public List<SearchItemResponse> getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "SearchResultResponse{" +
                "result=" + result +
                '}';
    }
}
