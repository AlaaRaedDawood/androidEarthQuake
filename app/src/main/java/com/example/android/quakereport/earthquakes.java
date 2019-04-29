package com.example.android.quakereport;

public class earthquakes {
    private String date ;
    private float magnitude ;
    private String city ;
    public earthquakes (float magnitude  , String city ,String date ){
        this.date = date ;
        this.magnitude = magnitude ;
        this.city = city ;

    }

    public String getDate() {
        return date;
    }

    public float getMagnitude() {
        return magnitude;
    }

    public String getCity() {
        return city;
    }
}
