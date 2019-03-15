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

    TextView textView;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_whether, container, false);


        textView = view.findViewById(R.id.textView);
        textView2 = view.findViewById(R.id.textView2);
        textView3 = view.findViewById(R.id.textView3);
        textView4 = view.findViewById(R.id.textView4);
        textView5 = view.findViewById(R.id.textView5);

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
//                Log.i("ProcFin", String.valueOf(elem.atmosphericPressure));
                textView.setText(elem.atmosphericPressure.toString());
                textView2.setText(elem.temperature.toString());
                textView3.setText(elem.weatherCondition.toString());
                textView4.setText(elem.weekday.toString());
                textView5.setText(elem.wind.toString());
            }
        }
    }
}