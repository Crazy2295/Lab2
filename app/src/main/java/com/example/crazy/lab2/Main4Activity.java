package com.example.crazy.lab2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.internal.location.zzas;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.Locale;

public class Main4Activity extends AppCompatActivity {
    private FusedLocationProviderClient mFusedLocationClient;
    TextView textAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if(!ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            else {
                textAddress = (TextView)findViewById(R.id.textAddress);
                textAddress.setText("getLocation");

                getLocation();
            }
        }
        else {
            textAddress = (TextView)findViewById(R.id.textAddress);
            textAddress.setText("hi");

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
                                        textAddress = (TextView)findViewById(R.id.textAddress);
                                        textAddress.setText(address.getLocality());
                                        Log.i("Location", "my location is " +
                                                address.getLocality());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else {
                                    textAddress = (TextView)findViewById(R.id.textAddress);
                                    textAddress.setText("null");
                                }
                            }
                        });
    }
}
