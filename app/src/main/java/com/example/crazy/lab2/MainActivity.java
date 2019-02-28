package com.example.crazy.lab2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    EditText iLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iLogin = (EditText) findViewById(R.id.editLogin);
    }



    public void onMyButtonClick(View view) {
        Intent intent = new Intent(this, Main3Activity.class);
        intent.putExtra("type", "3");
        startActivity(intent);
    }
}
