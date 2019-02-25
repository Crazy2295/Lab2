package com.example.crazy.lab2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        login = (TextView) findViewById(R.id.textLogin);

        Intent intent = getIntent();

        String iLogin = intent.getStringExtra("iLogin");

        login.setText(iLogin);
    }
}
