package com.example.crazy.lab2.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.crazy.lab2.interfaces.AsyncResponse;
import com.example.crazy.lab2.utils.WhetherJSON;
import com.example.crazy.lab2.utils.AsyncTaskDownloadJSON;
import com.example.crazy.lab2.R;
import com.google.gson.Gson;

import java.util.List;

public class FragmentWhether extends Fragment implements AsyncResponse {
    AsyncTaskDownloadJSON asyncTaskDownloadJSON = null;

    String city = "Таганрог";

    TextView cityNameView;
    TextView pressureView;
    TextView temperatureView;
    TextView whetherCondView;
    TextView weekdayView;
    TextView windView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_whether, container, false);


        cityNameView = view.findViewById(R.id.cityNameView);
        pressureView = view.findViewById(R.id.pressureView);
        temperatureView = view.findViewById(R.id.temperatureView);
        whetherCondView = view.findViewById(R.id.whetherCondView);
        weekdayView = view.findViewById(R.id.weekdayView);
        windView = view.findViewById(R.id.windView);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            city = bundle.getString("city", "Таганрог");
        }

        callAsyncTask();

        return view;
    }

    void callAsyncTask() {
        asyncTaskDownloadJSON = new AsyncTaskDownloadJSON();
        asyncTaskDownloadJSON.delegate = this;
        asyncTaskDownloadJSON.execute();
    }

    @Override
    public void processFinish(List<WhetherJSON> output) {
        Log.i("Activity", "Output = " + output);

        for (int i = 0; i < output.size(); i++) {
            WhetherJSON elem = output.get(i);

            if (elem.city.equals(city)) {
                cityNameView.setText(elem.city);
                pressureView.setText(elem.atmosphericPressure.toString());
                temperatureView.setText(elem.temperature.toString());
                whetherCondView.setText(elem.weatherCondition);
                weekdayView.setText(elem.weekday);
                windView.setText(elem.wind.toString());
                break;
            }
        }
    }
}