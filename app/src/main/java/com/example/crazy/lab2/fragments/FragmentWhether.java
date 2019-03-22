package com.example.crazy.lab2.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.crazy.lab2.activities.ActivityMain;
import com.example.crazy.lab2.interfaces.AsyncResponse;
import com.example.crazy.lab2.utils.DBHelper;
import com.example.crazy.lab2.utils.WhetherJSON;
import com.example.crazy.lab2.utils.AsyncTaskDownloadJSON;
import com.example.crazy.lab2.R;
import com.google.gson.Gson;

import java.util.List;

public class FragmentWhether extends Fragment {
    DBHelper dbHelper;

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

        dbHelper = new DBHelper(((ActivityMain)getActivity()).getBaseContext());

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

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("cities", null, "name = ?",
                new String[] {city}, null, null, "name", "1");

        if (cursor.moveToFirst()) {
            cityNameView.setText(city);
            pressureView.setText(cursor.getString(cursor.getColumnIndex("atmospheric_pressure")));
            temperatureView.setText(cursor.getString(cursor.getColumnIndex("temperature")));
            whetherCondView.setText(cursor.getString(cursor.getColumnIndex("weather_condition")));
            weekdayView.setText(cursor.getString(cursor.getColumnIndex("weekday")));
            windView.setText(cursor.getString(cursor.getColumnIndex("wind")));
        }
        cursor.close();

        return view;
    }
}