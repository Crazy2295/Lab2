package com.example.crazy.lab2.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WhetherJSON {
    @SerializedName("AtmosphericPressure")
    @Expose
    public Integer atmosphericPressure;

    @SerializedName("Temperature")
    @Expose
    public Integer temperature;

    @SerializedName("WeatherCondition")
    @Expose
    public String weatherCondition;

    @SerializedName("Weekday")
    @Expose
    public String weekday;

    @SerializedName("Wind")
    @Expose
    public Integer wind;
}
