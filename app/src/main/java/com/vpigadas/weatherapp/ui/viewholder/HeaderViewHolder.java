package com.vpigadas.weatherapp.ui.viewholder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import com.vpigadas.weatherapp.R;

public class HeaderViewHolder extends ViewHolder {

    @Nullable
    private TextView text;

    public static HeaderViewHolder getInstance(@NonNull View itemView) {
        return new HeaderViewHolder(itemView);
    }

    private HeaderViewHolder(@NonNull View itemView) {
        super(itemView);

        text = itemView.findViewById(R.id.location_title);
    }

    public void bind(String data) {
        if (text != null) {
            text.setText(data);
        }
    }
}
