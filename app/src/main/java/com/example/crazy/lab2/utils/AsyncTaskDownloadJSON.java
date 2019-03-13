package com.example.crazy.lab2.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.example.crazy.lab2.interfaces.AsyncResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncTaskDownloadJSON extends AsyncTask<String, Integer, String> {
    public AsyncResponse delegate = null;

    protected void onPreExecute() {
        Log.i("AsyncTaskDownloadJSON", "PreExecute");
    }

    protected String doInBackground(String... arg) {
        String responseStr = null;
        Log.i("AsyncTaskDownloadJSON", "InBackground");

        String urlString = "https://raw.githubusercontent.com/Dmtittrriy/testjasonlab/master/Weather.json";
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
                responseStr = response.toString();
                Log.i("AsyncTaskDownloadJSON", "Response " + response);
                input.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseStr;
    }

    protected void onProgressUpdate(Integer... values) {
        Log.i("AsyncTaskDownloadJSON", "Progress is " + values[0]);
    }

    protected void onPostExecute(String output) {
        Log.i("AsyncTaskDownloadJSON", "Output string = " + output);
        delegate.processFinish(output);
    }
}
