package com.example.crazy.lab2.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.crazy.lab2.interfaces.AsyncResponse;
import com.example.crazy.lab2.utils.AsyncTaskDownloadJSON;
import com.example.crazy.lab2.utils.WhetherJSON;
import com.example.crazy.lab2.R;
import com.google.gson.Gson;

public class ActivityForTesting extends AppCompatActivity implements AsyncResponse {

    AsyncTaskDownloadJSON asyncTaskDownloadJSON = new AsyncTaskDownloadJSON();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //this to set delegate/listener back to this class
        asyncTaskDownloadJSON.delegate = this;
        asyncTaskDownloadJSON.execute("InputStr");
    }

    //this override the implemented method from asyncTask
    @Override
    public void processFinish(String output){
        //Here you will receive the result fired from async class
        Log.i("Activity", "Output = " + output);

        Gson gson = new Gson();
        WhetherJSON fruitItems = gson.fromJson(output, WhetherJSON.class);

        TextView textView = (TextView) findViewById(R.id.textView7);
//        textView.setText(fruitItems.fruits.get(2));
    }
}