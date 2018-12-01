package com.sourav.foregroundservice.DB;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "location")
public class Location {

    private String latitude="";
    private String longitude="";
    @PrimaryKey
    @NonNull
    private String timeStamp="";
    private String gpsSpeed="";

    @Override
    public String toString() {
        return "Location{" +
                "latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", gpsSpeed='" + gpsSpeed + '\'' +
                '}';
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getGpsSpeed() {
        return gpsSpeed;
    }

    public void setGpsSpeed(String gpsSpeed) {
        this.gpsSpeed = gpsSpeed;
    }
}
