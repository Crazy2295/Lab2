package com.example.crazy.lab2.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDoneException;
import android.database.sqlite.SQLiteStatement;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crazy.lab2.R;
import com.example.crazy.lab2.interfaces.AsyncResponse;
import com.example.crazy.lab2.utils.AsyncTaskDownloadJSON;
import com.example.crazy.lab2.utils.DBHelper;
import com.example.crazy.lab2.utils.WhetherJSON;

import java.util.List;

public class ActivityLogin extends AppCompatActivity implements AsyncResponse {
    DBHelper dbHelper;
    AsyncTaskDownloadJSON asyncTaskDownloadJSON = null;

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
            db.insert("cities", null, cv);
        }

        Log.i("processFinish", "Completed");
    }


    public void onBtnLoginClick(View view) {
        String _login = login.getText().toString();
        String _password = password.getText().toString();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT id FROM users WHERE login ='" + _login +
                "' AND password ='" + _password + "' LIMIT 1;";
        SQLiteStatement sqlSt = db.compileStatement(query);

        try {
            long userId =  sqlSt.simpleQueryForLong();

            Intent intent = new Intent(this, ActivityMain.class);
            intent.putExtra("login", _login);
            intent.putExtra("userId", userId);
            startActivity(intent);

        } catch (SQLiteDoneException e) {
            Toast.makeText(this, "Wrong Login or Password.", Toast.LENGTH_SHORT).show();
        }
    }
}
