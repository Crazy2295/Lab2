package com.example.crazy.lab2;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MyAsyncTask extends AsyncTask<String, Integer, String> {
    public AsyncResponse delegate = null;

    protected void onPreExecute() {
        Log.i("MyAsyncTask", "PreExecute");
    }

    protected String doInBackground(String... arg) {
        String responseStr = null;
        Log.i("MyAsyncTask", "InBackground");

        String urlString = "https://doc-0k-08-docs.googleusercontent.com/docs/securesc/ha0ro937gcuc7l7deffksulhg5h7mbp1/ac0pg108o4hso2296hu1lnkcqu1qtbt9/1552291200000/06522568430508153457/*/1OXE-M6WP0-yMbvgy0JF35CuQzEsaSsAh?e=download";
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
                Log.i("MyAsyncTask", "Response " + response);
                input.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseStr;
    }

    protected void onProgressUpdate(Integer... values) {
        Log.i("MyAsyncTask", "Progress is " + values[0]);
    }

    protected void onPostExecute(String output) {
        Log.i("MyAsyncTask", "Output string = " + output);
        delegate.processFinish(output);
    }
}
