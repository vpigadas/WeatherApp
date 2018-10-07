package com.vpigadas.weatherapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vpigadas.weatherapp.R;
import com.vpigadas.weatherapp.database.LocationStore;
import com.vpigadas.weatherapp.ui.viewholder.HeaderViewHolder;
import com.vpigadas.weatherapp.ui.viewholder.LocationViewHolder;

import java.util.ArrayList;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int TYPE_HEADER = 1;

    private List<Object> array;

    public static LocationAdapter getInstance() {
        return new LocationAdapter();
    }

    private LocationAdapter() {
        array = new ArrayList<>();
    }

    public void addItems(Context context, String title, @NonNull List data) {
        if (data.isEmpty()) {
            return;
        }

        if (array.contains(title)) {
            if (context.getString(R.string.string_favorite_location).equals(title)) {
                array.remove(title);
                array.removeAll(data);
            } else {
                int position = array.indexOf(title);
                array.removeAll(array.subList(position, array.size()));
            }

            addItems(context, title, data);
        } else {
            if (context.getString(R.string.string_favorite_location).equals(title)) {
                array.addAll(0, data);
                array.add(0, title);
            } else {
                array.add(title);
                array.addAll(data);
            }

            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {

        Context context = viewGroup.getContext();

        if (position == TYPE_HEADER) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.holder_header, viewGroup, false);
            return HeaderViewHolder.getInstance(itemView);
        } else {
            View itemView = LayoutInflater.from(context).inflate(R.layout.holder_location, viewGroup, false);
            return LocationViewHolder.getInstance(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Object item = getItem(position);
        if (viewHolder instanceof LocationViewHolder) {
            if (item instanceof Pair) {
                ((LocationViewHolder) viewHolder).bind((Pair<String, String>) item);
            } else if (item instanceof LocationStore) {
                ((LocationViewHolder) viewHolder).bind((LocationStore) item);
            }
        } else if (viewHolder instanceof HeaderViewHolder) {
            if (item instanceof String) {
                ((HeaderViewHolder) viewHolder).bind((String) item);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object item = getItem(position);
        if (item instanceof String) {
            return TYPE_HEADER;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    @NonNull
    private Object getItem(int position) {
        return array.get(position);
    }
}
