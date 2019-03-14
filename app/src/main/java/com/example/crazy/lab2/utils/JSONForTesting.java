package com.example.crazy.lab2.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JSONForTesting {
    @SerializedName("fruits")
    @Expose
    private List<String> fruits = null;

    public List<String> getFruits() {
        return fruits;
    }
}
