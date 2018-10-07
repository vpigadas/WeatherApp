package com.vpigadas.weatherapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vpigadas.weatherapp.R;
import com.vpigadas.weatherapp.network.models.weather.WeatherForcastResponse;
import com.vpigadas.weatherapp.ui.viewholder.LocationViewHolder;
import com.vpigadas.weatherapp.ui.viewholder.WeatherViewHolder;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<WeatherForcastResponse> array;

    public static WeatherAdapter getInstance(List<WeatherForcastResponse> data) {
        return new WeatherAdapter(data);
    }

    private WeatherAdapter(List<WeatherForcastResponse> data) {
        array = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        Context context = viewGroup.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.holder_weather, viewGroup, false);

        return WeatherViewHolder.getInstance(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof WeatherViewHolder) {
            ((WeatherViewHolder) viewHolder).bind(getItem(position));
        }
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    private WeatherForcastResponse getItem(int position) {
        return array.get(position);
    }
}
