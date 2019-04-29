package com.example.android.quakereport;

public class earthquakes {
    private String date ;
    private String time ;
    private double magnitude ;
    private String city ;
    public earthquakes (double magnitude  , String city ,String date , String time){
        this.date = date ;
        this.time = time ;
        this.magnitude = magnitude ;
        this.city = city ;

    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getCity() {
        return city;
    }
}
