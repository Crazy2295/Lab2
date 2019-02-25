package com.example.crazy.lab2;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class Main3Activity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Intent intent = getIntent();

        String type = intent.getStringExtra("type");

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        switch (type) {
            case "0":
                ft.replace(R.id.your_placeholder, new SecondFragment());
                break;
            case "1":
                ft.replace(R.id.your_placeholder, new FirstFragment());
                break;
            case "2":
                ft.replace(R.id.your_placeholder, new BlankFragment());
                break;
            case "3":
                ft.replace(R.id.your_placeholder, new ThirdFragment());
                break;
            default:
                ft.replace(R.id.your_placeholder, new BlankFragment());
                break;

        }

        ft.commit();


        mDrawerLayout = findViewById(R.id.my_drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch(menuItem.getItemId()) {
                            case R.id.choose_city:
                                Intent intent = new Intent(Main3Activity.this, Main3Activity.class);
                                intent.putExtra("type", "0");
                                startActivity(intent);
                                break;
                            case R.id.saved_city:
                                Intent intent1 = new Intent(Main3Activity.this, Main3Activity.class);
                                intent1.putExtra("type", "1");
                                startActivity(intent1);
                                break;
                            case R.id.whether:
                                Intent intent2 = new Intent(Main3Activity.this, Main3Activity.class);
                                intent2.putExtra("type", "2");
                                startActivity(intent2);
                                break;
                            case R.id.author:
                                Intent intent3 = new Intent(Main3Activity.this, Main3Activity.class);
                                intent3.putExtra("type", "3");
                                startActivity(intent3);
                                break;
                        }
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();

                        return true;
                    }
                });
    }
}