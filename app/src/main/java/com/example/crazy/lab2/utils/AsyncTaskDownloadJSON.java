package com.example.crazy.lab2.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.example.crazy.lab2.interfaces.AsyncResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AsyncTaskDownloadJSON extends AsyncTask<String, Integer, List<WhetherJSON>> {
    public AsyncResponse delegate = null;

    protected void onPreExecute() {
        Log.i("AsyncTaskDownloadJSON", "PreExecute");
    }

    protected List<WhetherJSON> doInBackground(String... arg) {
        List<WhetherJSON> outputList = new ArrayList<>();
        Log.i("AsyncTaskDownloadJSON", "InBackground");

        String urlString = "https://raw.githubusercontent.com/Lpirskaya/JsonLab/master/Weather1.json";
        URL url = null;

        try{
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                StringBuilder response = new StringBuilder();
                BufferedReader input = new BufferedReader(new InputStreamReader
                        (conn.getInputStream()), 8192);
                String line = null;
                while ((line = input.readLine()) != null)
                {
                    response.append(line);
                }
                input.close();
                Log.i("AsyncTaskDownloadJSON", "Response " + response);

                Gson gson = new Gson();
                outputList = gson.fromJson(response.toString(),
                        new TypeToken<List<WhetherJSON>>(){}.getType());

                return outputList;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputList;
    }

    protected void onProgressUpdate(Integer... values) {
        Log.i("AsyncTaskDownloadJSON", "Progress is " + values[0]);
    }

    protected void onPostExecute(List<WhetherJSON> output) {
        Log.i("AsyncTaskDownloadJSON", "Output string = " + output);
        delegate.processFinish(output);
    }
}
