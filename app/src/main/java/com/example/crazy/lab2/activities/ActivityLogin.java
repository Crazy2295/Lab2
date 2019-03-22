package com.example.crazy.lab2.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
            db.insert("Cities", null, cv);
        }

        Log.i("processFinish", "Completed");
    }


    public void onBtnLoginClick(View view) {
        String _login = login.getText().toString();
        String _password = password.getText().toString();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT COUNT(*) FROM Users WHERE login ='" + _login +
                "' AND password ='" + _password + "';";
        SQLiteStatement sqlSt = db.compileStatement(query);

        if (sqlSt.simpleQueryForLong() > 0) {
            Intent intent = new Intent(this, ActivityMain.class);
            intent.putExtra("login", _login);
            startActivity(intent);
        } else
            Toast.makeText(this, "Wrong Login or Password.", Toast.LENGTH_SHORT).show();
    }
}
