package com.vpigadas.weatherapp.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.vpigadas.weatherapp.R;
import com.vpigadas.weatherapp.manager.ActivityRouter;

public class SplashScreen extends AppCompatActivity {

    private static final int DELAY = 1500;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityRouter.getInstance().gotoMainScreen(SplashScreen.this);
            }
        }, DELAY);
    }

    @Override
    protected void onStop() {
        super.onStop();

        mHandler.removeCallbacksAndMessages(null);
    }
}
