package com.example.crazy.lab2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

public class Fragment3 extends Fragment implements AsyncResponse {
    MyAsyncTask myAsyncTask = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, container, false);

        myAsyncTask = new MyAsyncTask();


        //this to set delegate/listener back to this class
        myAsyncTask.delegate = this;
        myAsyncTask.execute("InputStr");

        return view;
    }

    //this override the implemented method from asyncTask
    @Override
    public void processFinish(String output){
        //Here you will receive the result fired from async class
        Log.i("Activity", "Output = " + output);

        Gson gson = new Gson();
        ForJSON[] myItems = gson.fromJson(output, ForJSON[].class);


        for(int i = 0; i< myItems.length; i++){

            Log.i("ForJSON", "#" + i + "; AtmosphericPressure  = " + myItems[i].atmosphericPressure +
                    "; Temperature = " + myItems[i].temperature +  "; WeatherCondition = " + myItems[i].weatherCondition +
                    "; Weekday = " + myItems[i].weekday + "; Wind = " + myItems[i].wind);

        }



//        TextView textView = (TextView) findViewById(R.id.textView7);
//        textView.setText(myItems.fruits.get(2));
    }
}