package com.vpigadas.weatherapp.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.vpigadas.weatherapp.activities.LocationActivity;
import com.vpigadas.weatherapp.activities.MainActivity;
import com.vpigadas.weatherapp.database.LocationStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ActivityRouter {

    private static ActivityRouter instance;

    public static ActivityRouter getInstance() {
        if (instance == null) {
            instance = new ActivityRouter();
        }

        return instance;
    }

    public void gotoMainScreen(@NonNull Activity activity) {
        if (activity.isDestroyed() || activity.isFinishing()) {
            return;
        }

        Intent intent = new Intent(activity, MainActivity.class);

        activity.startActivity(intent);
        activity.finish();
    }


    public void gotoLocationScreen(@NonNull Context activity, @NonNull LocationStore locationStore) {
        Intent intent = new Intent(activity, LocationActivity.class);
        intent.putExtra(LocationActivity.ARG_CITY, locationStore.getCityName());
        intent.putExtra(LocationActivity.ARG_REGION, locationStore.getRegionName());
        intent.putExtra(LocationActivity.ARG_SAVED, locationStore.isSaved());

        activity.startActivity(intent);
    }
}
