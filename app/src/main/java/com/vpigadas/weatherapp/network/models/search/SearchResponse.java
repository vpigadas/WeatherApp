package com.vpigadas.weatherapp.network.models.search;

import android.text.TextUtils;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class SearchResponse {

    private SearchResultResponse search_api;

    public SearchResultResponse getSearch_api() {
        return search_api;
    }

    @NonNull
    public List<Pair<String, String>> getResult() {
        List<Pair<String, String>> array = new ArrayList<>();

        if (search_api != null) {
            for (SearchItemResponse item : search_api.getResult()) {
                StringBuilder stringTitle = new StringBuilder();

                if (item.getAreaName() != null && item.getAreaName().size() > 0) {
                    for (SearchDataResponse data : item.getAreaName()) {
                        if (!TextUtils.isEmpty(data.getValue())) {
                            if (TextUtils.isEmpty(stringTitle)) {
                                stringTitle.append(data.getValue());
                            } else {
                                stringTitle.append(",").append(data.getValue());
                            }
                        }
                    }
                }

                StringBuilder stringLocation = new StringBuilder();

                if (item.getRegion() != null && item.getRegion().size() > 0) {
                    for (SearchDataResponse data : item.getRegion()) {
                        if (!TextUtils.isEmpty(data.getValue())) {
                            if (TextUtils.isEmpty(stringLocation)) {
                                stringLocation.append(data.getValue());
                            } else {
                                stringLocation.append(",").append(data.getValue());
                            }
                        }
                    }
                }

                if (item.getCountry() != null && item.getCountry().size() > 0) {
                    for (SearchDataResponse data : item.getCountry()) {
                        if (!TextUtils.isEmpty(data.getValue())) {
                            if (TextUtils.isEmpty(stringLocation)) {
                                stringLocation.append(data.getValue());
                            } else {
                                stringLocation.append(",").append(data.getValue());
                            }
                        }
                    }
                }

                array.add(new Pair<>(stringTitle.toString(), stringLocation.toString()));
            }
        }

        return array;
    }

    @Override
    public String toString() {
        return "SearchResponse{" +
                "search_api=" + search_api +
                '}';
    }
}
