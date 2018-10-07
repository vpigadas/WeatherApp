package com.vpigadas.weatherapp.network;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vpigadas.weatherapp.AppDatabase;
import com.vpigadas.weatherapp.manager.CustomConnectivityManager;
import com.vpigadas.weatherapp.network.models.search.SearchResponse;
import com.vpigadas.weatherapp.network.models.weather.WeatherResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerClient {

    private static final String PRODUCTION_URL = "https://api.worldweatheronline.com/premium/v1/";

    private static ServerClient instance;

    private OkHttpClient.Builder httpClient;
    private Retrofit.Builder retroBuilder;


    public static ServerClient getIntance(Context context) {
        if (instance == null) {
            instance = new ServerClient();
        }

        return instance;

    }

    private Retrofit.Builder getRetrofitInstance() {
        if (retroBuilder == null) {
            retroBuilder = new Retrofit.Builder();
            retroBuilder.baseUrl(PRODUCTION_URL);

            Gson gson = new GsonBuilder().setLenient().create();

            retroBuilder
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson));
        }

        return retroBuilder;
    }

    private OkHttpClient getHttpInstance() {
        if (httpClient == null) {
            httpClient = new OkHttpClient.Builder();
        }

        return httpClient.build();
    }

    private <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = getRetrofitInstance().client(getHttpInstance()).build();

        return retrofit.create(serviceClass);
    }

    @SuppressLint("CheckResult")
    public void getWeather(String location, final MutableLiveData<WeatherResponse> stream) {

        if (!CustomConnectivityManager.isConnected()) {
            return;
        }

        Map<String, Object> query = new HashMap<>();
        query.put("q", location);
        query.put("num_of_days", 6);
        query.put("format", "json");
        query.put("key", "922540e66a7342dc801180310182809");
        query.put("lang", "el");


        ServerInterface client = createService(ServerInterface.class);
        Observable<WeatherResponse> call = client.getWeather(query);

        call.subscribeOn(Schedulers.single())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<WeatherResponse>() {
                    @Override
                    public void accept(WeatherResponse items) {
                        stream.postValue(items);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.d(ServerClient.class.getSimpleName(), "", throwable);
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void getLocations(String location, final MutableLiveData<List<Pair<String, String>>> stream) {

        if (!CustomConnectivityManager.isConnected()) {
            return;
        }

        Map<String, Object> query = new HashMap<>();
        query.put("query", location);
        query.put("num_of_results", 25);
        query.put("format", "json");
        query.put("key", "922540e66a7342dc801180310182809");
        query.put("lang", "el");


        ServerInterface client = createService(ServerInterface.class);
        Observable<SearchResponse> call = client.getLocations(query);

        call.subscribeOn(Schedulers.single())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<SearchResponse>() {
                    @Override
                    public void accept(SearchResponse items) {
                        stream.postValue(items.getResult());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.d(ServerClient.class.getSimpleName(), "", throwable);
                    }
                });
    }
}
