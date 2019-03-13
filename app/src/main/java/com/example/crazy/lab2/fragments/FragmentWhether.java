package com.example.crazy.lab2.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.crazy.lab2.interfaces.AsyncResponse;
import com.example.crazy.lab2.utils.WhetherJSON;
import com.example.crazy.lab2.utils.AsyncTaskDownloadJSON;
import com.example.crazy.lab2.R;
import com.google.gson.Gson;

public class FragmentWhether extends Fragment implements AsyncResponse {
    AsyncTaskDownloadJSON asyncTaskDownloadJSON = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_whether, container, false);

        asyncTaskDownloadJSON = new AsyncTaskDownloadJSON();
        asyncTaskDownloadJSON.delegate = this;
        asyncTaskDownloadJSON.execute("InputStr");

        return view;
    }

    @Override
    public void processFinish(String output) {
        Log.i("Activity", "Output = " + output);

        Gson gson = new Gson();
        WhetherJSON[] myItems = gson.fromJson(output, WhetherJSON[].class);

        if (myItems != null)
            for (int i = 0; i < myItems.length; i++) {

                Log.i("WhetherJSON", "#" + i + "; AtmosphericPressure  = " + myItems[i].atmosphericPressure +
                        "; Temperature = " + myItems[i].temperature + "; WeatherCondition = " + myItems[i].weatherCondition +
                        "; Weekday = " + myItems[i].weekday + "; Wind = " + myItems[i].wind);

            }
    }
}