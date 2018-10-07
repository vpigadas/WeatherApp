package com.vpigadas.weatherapp.ui.viewholder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import com.vpigadas.weatherapp.R;
import com.vpigadas.weatherapp.network.models.weather.WeatherForcastResponse;
import com.vpigadas.weatherapp.utils.UiHelper;

public class WeatherViewHolder extends ViewHolder {

    @Nullable
    private TextView weatherDate, weatherTempLow, weatherTempHigh;

    public static WeatherViewHolder getInstance(@NonNull View itemView) {
        return new WeatherViewHolder(itemView);
    }

    private WeatherViewHolder(@NonNull View itemView) {
        super(itemView);

        weatherDate = itemView.findViewById(R.id.weather_date);
        weatherTempLow = itemView.findViewById(R.id.weather_temp_low);
        weatherTempHigh = itemView.findViewById(R.id.weather_temp_high);
    }

    public void bind(WeatherForcastResponse data) {

        if (weatherDate != null) {
            weatherDate.setText(data.getDate());
        }

        if (weatherTempLow != null) {
            weatherTempLow.setText(UiHelper.showAsCelcius(data.getMintempC()));
        }

        if (weatherTempHigh != null) {
            weatherTempHigh.setText(UiHelper.showAsCelcius(data.getMaxtempC()));
        }

    }
}
