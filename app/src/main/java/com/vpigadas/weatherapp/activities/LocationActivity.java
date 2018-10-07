package com.vpigadas.weatherapp.activities;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vpigadas.weatherapp.R;
import com.vpigadas.weatherapp.database.LocationStore;
import com.vpigadas.weatherapp.network.models.weather.WeatherConditionResponse;
import com.vpigadas.weatherapp.network.models.weather.WeatherDataResponse;
import com.vpigadas.weatherapp.network.models.weather.WeatherForcastResponse;
import com.vpigadas.weatherapp.network.models.weather.WeatherItemResponse;
import com.vpigadas.weatherapp.network.models.weather.WeatherResponse;
import com.vpigadas.weatherapp.ui.adapter.WeatherAdapter;
import com.vpigadas.weatherapp.ui.viewmodel.LocationViewModel;
import com.vpigadas.weatherapp.utils.UiHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LocationActivity extends AbstractActivity {

    public static final String ARG_CITY = LocationActivity.class.getSimpleName() + ".ARG_CITY";
    public static final String ARG_REGION = LocationActivity.class.getSimpleName() + ".ARG_REGION";
    public static final String ARG_SAVED = LocationActivity.class.getSimpleName() + ".ARG_SAVED";

    @Nullable
    private ImageView weatherIcon;
    @Nullable
    private TextView cityName, regionName, weatherDesc, weatherTemp, weatherTempLow, weatherTempHigh, weatherHumidity;
    @Nullable
    private RecyclerView recyclerView;
    @Nullable
    private FloatingActionButton fab;

    private LocationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        viewModel = ViewModelProviders.of(this).get(LocationViewModel.class);

    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void initUi() {
        weatherIcon = findViewById(R.id.location_weather_icon);
        cityName = findViewById(R.id.location_weather_city);
        regionName = findViewById(R.id.location_weather_region);
        weatherDesc = findViewById(R.id.location_weather_desc);

        weatherTemp = findViewById(R.id.location_weather_temperature);
        weatherTempLow = findViewById(R.id.location_weather_temp_low);
        weatherTempHigh = findViewById(R.id.location_weather_temp_high);
        weatherHumidity = findViewById(R.id.location_weather_other);

        recyclerView = findViewById(R.id.location_weather_list);

        fab = findViewById(R.id.fab);
        if (fab != null) {
            boolean isSaved = getIntent().getBooleanExtra(ARG_SAVED , false);

            if (isSaved) {
                fab.setVisibility(View.GONE);
            }

            fab.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("CheckResult")
                @Override
                public void onClick(View view) {
                    String location = getIntent().getStringExtra(ARG_CITY);
                    String region = getIntent().getStringExtra(ARG_REGION);
                    boolean isSaved = getIntent().getBooleanExtra(ARG_SAVED, false);

                    Observable.just(LocationStore.getInstance(location, region, isSaved))
                            .subscribeOn(Schedulers.single())
                            .observeOn(Schedulers.io())
                            .subscribe(new Consumer<LocationStore>() {
                                @Override
                                public void accept(LocationStore locationStore) {
                                    appDatabase.userDao().insert(locationStore);
                                }
                            });

                    fab.setVisibility(View.GONE);
                }
            });
        }

        viewModel.getWeatherStream().observe(this, new Observer<WeatherResponse>() {
            @Override
            public void onChanged(@android.support.annotation.Nullable WeatherResponse weatherResponse) {
                if (weatherResponse == null) {
                    return;
                }

                String location = getIntent().getStringExtra(ARG_CITY);
                if (cityName != null && !TextUtils.isEmpty(location)) {
                    cityName.setText(location);
                }

                String region = getIntent().getStringExtra(ARG_REGION);
                if (regionName != null && !TextUtils.isEmpty(region)) {
                    regionName.setText(region);
                }

                WeatherItemResponse itemResponse = weatherResponse.getData();
                if (itemResponse != null) {
                    for (WeatherConditionResponse conditionResponse : itemResponse.getCurrent_condition()) {
                        if (weatherTemp != null) {
                            weatherTemp.setText(UiHelper.showAsCelcius(conditionResponse.getTemp_C()));
                        }

                        if (weatherDesc != null) {
                            weatherDesc.setText(conditionResponse.getDescription());
                        }

                        if (weatherHumidity != null) {
                            weatherHumidity.setText(getString(R.string.temperature_humidity,
                                    String.format("%s%%", conditionResponse.getHumidity())));
                        }

                        if (weatherIcon != null) {
                            List<WeatherDataResponse> dataResponse = conditionResponse.getWeatherIconUrl();
                            if (dataResponse != null) {
                                for (WeatherDataResponse data : dataResponse) {
                                    Picasso.get().load(data.getValue()).into(weatherIcon);
                                }
                            }

                        }
                    }

                    String currectDay = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                    List<WeatherForcastResponse> prediction = new ArrayList<>();

                    for (WeatherForcastResponse forecastResponse : itemResponse.getWeather()) {
                        if (currectDay.equals(forecastResponse.getDate())) {
                            if (weatherTempLow != null) {
                                weatherTempLow.setText(getString(R.string.temperature_low,
                                        UiHelper.showAsCelcius(forecastResponse.getMintempC())));
                            }

                            if (weatherTempHigh != null) {
                                weatherTempHigh.setText(getString(R.string.temperature_high,
                                        UiHelper.showAsCelcius(forecastResponse.getMaxtempC())));
                            }
                        } else {
                            prediction.add(forecastResponse);
                        }
                    }

                    if (recyclerView != null) {
                        recyclerView.setAdapter(WeatherAdapter.getInstance(prediction));
                    }
                }
            }
        });

        String location = null;

        String city = getIntent().getStringExtra(ARG_CITY);
        if (!TextUtils.isEmpty(city)) {
            location = city;
        }
        String region = getIntent().getStringExtra(ARG_REGION);
        if (!TextUtils.isEmpty(region)) {
            if (TextUtils.isEmpty(location)) {
                location = region;
            } else {
                location = String.format("%s,%s", location, region);
            }
        }

        viewModel.getWeatherFor(location);
    }
}
