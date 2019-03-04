package com.example.crazy.lab2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.Locale;

public class Main3Activity extends AppCompatActivity {
    private FusedLocationProviderClient mFusedLocationClient;
    private String textAddress = "address";

    Fragment1 frag1 = new Fragment1();
    Fragment2 frag2 = new Fragment2();
    Fragment3 frag3 = new Fragment3();
    Fragment4 frag4 = new Fragment4();

    FragmentManager fm = getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        if (savedInstanceState == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_place, frag3);
            ft.commit();
        }

        final DrawerLayout mDrawerLayout = findViewById(R.id.my_drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                        switch(menuItem.getItemId()) {
                            case R.id.choose_city:
                                ft.replace(R.id.fragment_place, frag1);
                                break;
                            case R.id.saved_city:
                                ft.replace(R.id.fragment_place, frag2);
                                break;
                            case R.id.whether:
                                ft.replace(R.id.fragment_place, frag3);
                                break;
                            case R.id.author:
                                ft.replace(R.id.fragment_place, frag4);
                                break;
                        }
                        ft.addToBackStack(null);
                        ft.commit();

                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();

                        return true;
                    }
                });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if(!ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            else {
                getLocation();
            }
        }
        else {
            getLocation();
        }
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new
                        OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                    Geocoder geocoder = new Geocoder(getBaseContext(),
                                            Locale.getDefault());
                                    try {
                                        Address address =
                                                geocoder.getFromLocation(location.getLatitude(),
                                                        location.getLongitude(), 1).get(0);
                                        textAddress = address.getLocality();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else {
                                    textAddress = "null";
                                }
                            }
                        });
    }





    public String getData() {
        return textAddress;
    }
}
