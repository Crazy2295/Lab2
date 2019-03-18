package com.example.crazy.lab2.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.crazy.lab2.R;
import com.example.crazy.lab2.interfaces.AsyncResponse;
import com.example.crazy.lab2.utils.AsyncTaskDownloadJSON;
import com.example.crazy.lab2.utils.DBHelper;
import com.example.crazy.lab2.utils.WhetherJSON;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActivityLogin extends AppCompatActivity implements AsyncResponse {
    DBHelper dbHelper;
    AsyncTaskDownloadJSON asyncTaskDownloadJSON = null;


    public List<String> usersList = new ArrayList<>();;
    public List<String> passwordsList = new ArrayList<>();;

    TextView login;
    TextView password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        asyncTaskDownloadJSON = new AsyncTaskDownloadJSON();
        asyncTaskDownloadJSON.delegate = this;
        asyncTaskDownloadJSON.execute();

        login = findViewById(R.id.editLogin);
        password = findViewById(R.id.editPassword);

        dbHelper = new DBHelper(this.getBaseContext());

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query("Users", new String[]{"login", "password"}, null,
                null, null, null, "login");
        cursor.moveToFirst();
        int loginColIndex = cursor.getColumnIndex("login");
        int passwordColIndex = cursor.getColumnIndex("password");
        do {
            usersList.add(cursor.getString(loginColIndex));
            passwordsList.add(cursor.getString(passwordColIndex));
        } while (cursor.moveToNext());
        cursor.close();
    }

    @Override
    public void processFinish(List<WhetherJSON> output) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for (int i = 0; i < output.size(); i++) {
            ContentValues cv = new ContentValues();
            cv.put("name", output.get(i).city);
            cv.put("atmospheric_pressure", output.get(i).atmosphericPressure);
            cv.put("temperature", output.get(i).temperature);
            cv.put("weather_condition", output.get(i).weatherCondition);
            cv.put("weekday", output.get(i).weekday);
            cv.put("wind", output.get(i).wind);
            db.insert("Cities", null, cv);
        }

        Log.i("processFinish", "Completed");
    }



    public void onBtnLoginClick(View view) {
        String _login = login.getText().toString();
        String _password = password.getText().toString();
        Log.i("Login", "login = " + _login + " password = " + _password);
        Log.i("LoginSize", "usersList.size() = " + usersList.size() + " passwordsList = " + passwordsList.size());


        for (int i = 0; i < usersList.size(); i++) {
            if(usersList.get(i).equals(_login) && passwordsList.get(i).equals(_password)) {
                Intent intent = new Intent(this, ActivityMain.class);
                startActivity(intent);
                break;
            }
        }
    }
}
