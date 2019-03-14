package com.example.crazy.lab2.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.example.crazy.lab2.interfaces.AsyncForTesting;
import com.example.crazy.lab2.interfaces.AsyncResponse;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class AsyncTaskForTesting extends AsyncTask<String, Integer, List<String>> {
    public AsyncForTesting delegate = null;

    protected void onPreExecute() {
        Log.i("AsyncTaskForTesting", "PreExecute");
    }

    protected List<String> doInBackground(String... arg) {
        Log.i("AsyncTaskForTesting", "InBackground");

        String urlString =  "https://drive.google.com/uc?id=1K0ZijTMjN_AYGVQ8I1dd3gA_Jw-CYA7a&export=download";
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
                Log.i("AsyncTaskForTesting", "Response " + response);

                Gson gson = new Gson();
                JSONForTesting fruitItems = gson.fromJson(response.toString(), JSONForTesting.class);
                return fruitItems.getFruits();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void onProgressUpdate(Integer... values) {
        Log.i("AsyncTaskForTesting", "Progress is " + values[0]);
    }

    protected void onPostExecute(List<String> output) {
        Log.i("AsyncTaskForTesting", "Output list = " + output);
        if (output != null)
            delegate.processFinish(output);
    }
}
