package com.vpigadas.weatherapp.ui.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Pair;

import com.vpigadas.weatherapp.network.ServerClient;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private MutableLiveData<List<Pair<String, String>>> searchResult;

    public MainViewModel(@NonNull Application application) {
        super(application);

        searchResult = new MutableLiveData<>();
    }

    public LiveData<List<Pair<String, String>>> getSearchResult() {
        return searchResult;
    }

    public void searchFor(@Nullable String location) {
        if (!TextUtils.isEmpty(location)) {
            ServerClient.getIntance(getApplication()).getLocations(location, searchResult);
        } else {
            searchResult.postValue(new ArrayList<Pair<String, String>>());
        }
    }
}
