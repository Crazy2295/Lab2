package com.example.crazy.lab2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;

public class Fragment2 extends Fragment {
    private FusedLocationProviderClient mFusedLocationClient;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private String textCity = "address";

    private Activity activity;
    private Context context;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment2, container, false);

        activity = getActivity();
        context = activity.getBaseContext();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                Log.i("Location permission", "asked for permissions");
            } else {
                Log.i("Location permission", "some text");
                getLocation();
            }
        } else {
            getLocation();
        }
        
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[]
                                                   grantResults) {
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.i("Permission", "access!");
            getLocation();

        } else {
            Log.i("Permission", "denied!");
        }
    }


    @SuppressLint("MissingPermission")
    private void getLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(activity, new
                        OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                Log.i("Location", "started!");
                                if (location != null) {
                                    Log.i("Location", "success!");
                                    Geocoder geocoder = new Geocoder(getActivity().getBaseContext(),
                                            Locale.getDefault());
                                    try {
                                        Address address =
                                                geocoder.getFromLocation(location.getLatitude(),
                                                        location.getLongitude(), 1).get(0);
                                        Log.i("Location", "my location is " +
                                                address.getLocality());

                                        textCity = address.getLocality();
                                        
                                        mRecyclerView = view.findViewById(R.id.save_city);
                                        mRecyclerView.setHasFixedSize(true);
                                        mLayoutManager = new LinearLayoutManager(view.getContext());
                                        mRecyclerView.setLayoutManager(mLayoutManager);

                                        if (textCity == null) {
                                            textCity = "Местоположение не найдено";
                                        }

                                        mRecyclerView = (RecyclerView)view.findViewById(R.id.save_city);
                                        mRecyclerView.setHasFixedSize(true);
                                        mLayoutManager = new LinearLayoutManager(getActivity());
                                        mRecyclerView.setLayoutManager(mLayoutManager);

                                        List<String> recyclerData = new ArrayList<String>() {{
                                            add(textCity);
                                        }};
                                        mAdapter = new SecondAdapter(recyclerData);
                                        mRecyclerView.setAdapter(mAdapter);
                                        

//                                        myString[0] = textCity;
//                                        List<String> recyclerData = Arrays.asList(myString);
//                                        List<String> countries = new ArrayList();
//                                        countries.add("Россия");        //используется для надписи и картиночки
//                                        mAdapter = new MyRecyclerViewAdapter(recyclerData, countries);
//                                        mRecyclerView.setAdapter(mAdapter);

                                    } catch (IOException e) {

                                        Log.i("Location", "error!");
                                        e.printStackTrace();
                                    }
                                }
                            }
                        })
                .addOnFailureListener(activity, new
                        OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i("Location", "failed!");
                            }
                        });
    }
}