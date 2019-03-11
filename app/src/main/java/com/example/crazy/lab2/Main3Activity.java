package com.example.crazy.lab2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

public class Main3Activity extends AppCompatActivity implements AsyncResponse {

    MyAsyncTask myAsyncTask = new MyAsyncTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //this to set delegate/listener back to this class
        myAsyncTask.delegate = this;
        myAsyncTask.execute("InputStr");
    }

    //this override the implemented method from asyncTask
    @Override
    public void processFinish(String output){
        //Here you will receive the result fired from async class
        Log.i("Activity", "Output = " + output);

        Gson gson = new Gson();
        ForJSON fruitItems = gson.fromJson(output, ForJSON.class);

        TextView textView = (TextView) findViewById(R.id.textView7);
//        textView.setText(fruitItems.fruits.get(2));
    }
}