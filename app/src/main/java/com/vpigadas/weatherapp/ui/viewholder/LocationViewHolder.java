package com.vpigadas.weatherapp.ui.viewholder;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.vpigadas.weatherapp.activities.LocationActivity;
import com.vpigadas.weatherapp.R;
import com.vpigadas.weatherapp.database.LocationStore;
import com.vpigadas.weatherapp.manager.ActivityRouter;

public class LocationViewHolder extends ViewHolder {

    @Nullable
    private TextView text;
    @Nullable
    private TextView subText;

    @Nullable
    private LocationStore localData;

    public static LocationViewHolder getInstance(@NonNull View itemView) {
        return new LocationViewHolder(itemView);
    }

    private LocationViewHolder(@NonNull View itemView) {
        super(itemView);

        text = itemView.findViewById(R.id.location_title);
        subText = itemView.findViewById(R.id.location_sub_title);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (localData == null) {
                    return;
                }

                Context context = v.getContext();
                if (context == null) {
                    return;
                }

                ActivityRouter.getInstance().gotoLocationScreen(context, localData);
            }
        });
    }

    public void bind(Pair<String, String> data) {
        localData = LocationStore.getInstance(data.first, data.second, false);

        if (text != null) {
            text.setText(localData.getCityName());
        }

        if (subText != null) {
            subText.setText(localData.getRegionName());
        }
    }

    public void bind(LocationStore data) {
        localData = data;
        localData.setSaved(true);

        if (text != null) {
            text.setText(data.getCityName());
        }

        if (subText != null) {
            subText.setText(data.getRegionName());
        }
    }
}
