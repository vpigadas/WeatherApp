package com.vpigadas.weatherapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.vpigadas.weatherapp.database.LocationDAO;
import com.vpigadas.weatherapp.database.LocationStore;

@Database(entities = {LocationStore.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract LocationDAO userDao();
}
