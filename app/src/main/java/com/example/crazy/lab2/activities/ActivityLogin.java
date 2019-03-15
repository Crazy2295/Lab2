package com.example.crazy.lab2.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.crazy.lab2.R;

public class ActivityLogin extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }



    public void onBtnLoginClick(View view) {
        Intent intent = new Intent(this, ActivityMain.class);
        startActivity(intent);
    }
}
