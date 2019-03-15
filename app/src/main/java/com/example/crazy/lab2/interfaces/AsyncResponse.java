package com.example.crazy.lab2.interfaces;

import com.example.crazy.lab2.utils.WhetherJSON;

import java.util.List;

public interface AsyncResponse {
    void processFinish(List<WhetherJSON> output);
}
