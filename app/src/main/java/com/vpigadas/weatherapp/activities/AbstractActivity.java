package com.vpigadas.weatherapp.activities;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tapadoo.alerter.Alerter;
import com.vpigadas.weatherapp.AppDatabase;
import com.vpigadas.weatherapp.R;
import com.vpigadas.weatherapp.manager.CustomConnectivityManager;

public abstract class AbstractActivity extends AppCompatActivity {

    @NonNull
    protected AppDatabase appDatabase;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        appDatabase = Room.databaseBuilder(this, AppDatabase.class, "weather-app").build();

        initUi();
    }

    @Override
    protected void onStart() {
        super.onStart();

        CustomConnectivityManager.register(this, new CustomConnectivityManager.ConnectivityListener() {
            @Override
            public void onConnectionEstablished() {
                if (Alerter.isShowing()) {
                    Alerter.create(AbstractActivity.this)
                            .setText(getString(R.string.string_connection_restore))
                            .setIcon(R.drawable.ic_wifi_black_24dp)
                            .setDuration(1500)
                            .setDismissable(false)
                            .show();
                }
            }

            @Override
            public void onConnectionLost() {
                Alerter.create(AbstractActivity.this)
                        .setText(getString(R.string.string_no_connection))
                        .setIcon(R.drawable.ic_signal_wifi_off_black_24dp)
                        .enableInfiniteDuration(true)
                        .setDismissable(false)
                        .show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        CustomConnectivityManager.unregister(this);
    }

    protected abstract void initUi();
}
