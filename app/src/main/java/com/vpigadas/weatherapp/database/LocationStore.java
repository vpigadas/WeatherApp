package com.vpigadas.weatherapp.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "location")
public class LocationStore {

    public static LocationStore getInstance(String city, String region, boolean isSaved) {
        LocationStore locationStore = new LocationStore();
        locationStore.setLocationId(String.format("%s-%s", city, region).hashCode());
        locationStore.setCityName(city);
        locationStore.setRegionName(region);
        locationStore.setSaved(isSaved);
        return locationStore;
    }

    @PrimaryKey
    private int locationId;

    @ColumnInfo(name = "city")
    private String cityName;

    @ColumnInfo(name = "region")
    private String regionName;

    private boolean isSaved = true;

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }
}
