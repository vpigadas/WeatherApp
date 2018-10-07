package com.vpigadas.weatherapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface LocationDAO {

    @Query("SELECT * FROM location")
    LiveData<List<LocationStore>> getAll();

    @Insert
    void insert(LocationStore... users);

}
