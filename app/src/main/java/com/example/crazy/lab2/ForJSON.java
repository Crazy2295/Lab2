package com.example.crazy.lab2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForJSON {
    @SerializedName("fruits")
    @Expose
    public List<String> fruits = null;
}
