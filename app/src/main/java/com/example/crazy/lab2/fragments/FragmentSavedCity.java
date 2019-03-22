package com.example.crazy.lab2.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.crazy.lab2.R;
import com.example.crazy.lab2.activities.ActivityMain;
import com.example.crazy.lab2.adapters.AdapterChooseCity;
import com.example.crazy.lab2.adapters.AdapterSavedCity;
import com.example.crazy.lab2.utils.DBHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FragmentSavedCity extends Fragment {
//    private FusedLocationProviderClient mFusedLocationClient;
//    private String textCity = "address";

    DBHelper dbHelper;

    private SwipeRefreshLayout swipeRefreshLayout;

    private List<String> recyclerData = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    private long userId = -1;

//    private Activity activity;
//    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved_city, container, false);

        userId = ((ActivityMain)getActivity()).userId;
        dbHelper = new DBHelper(((ActivityMain)getActivity()).getBaseContext());

        swipeRefreshLayout = view.findViewById(R.id.test_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCities();
            }
        });

        mRecyclerView = (RecyclerView)view.findViewById(R.id.save_city);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new AdapterChooseCity(recyclerData, (ActivityMain)getActivity(),
                (ActivityMain)getActivity(), userId, 1);
        mRecyclerView.setAdapter(mAdapter);

        swipeRefreshLayout.setRefreshing(true);
        getCities();

//        activity = getActivity();
//        Context context = activity.getBaseContext();
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
//
//        if (ActivityCompat.checkSelfPermission(context,
//                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity,
//                    Manifest.permission.ACCESS_FINE_LOCATION)) {
//                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//                Log.i("Location permission", "asked for permissions");
//            } else {
//                Log.i("Location permission", "some text");
//                getLocation();
//            }
//        } else {
//            getLocation();
//        }

        return view;
    }

    void getCities () {
        List<String> outputList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("cities", null, "id in (SELECT city_id FROM saved_cities WHERE user_id = ?)",
                new String[] {String.valueOf(userId)}, null, null, "name");

        if (cursor.moveToFirst()) {
            int nameColIndex = cursor.getColumnIndex("name");
            do {
                outputList.add(cursor.getString(nameColIndex));
            } while (cursor.moveToNext());
            cursor.close();
        }

        recyclerData.clear();
        recyclerData.addAll(outputList);
        mAdapter.notifyDataSetChanged();

        swipeRefreshLayout.setRefreshing(false);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[]
//                                                   grantResults) {
//        if (grantResults.length > 0
//                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            Log.i("Permission", "access!");
//            getLocation();
//
//        } else {
//            Log.i("Permission", "denied!");
//        }
//    }
//
//
//    @SuppressLint("MissingPermission")
//    private void getLocation() {
//        mFusedLocationClient.getLastLocation()
//                .addOnSuccessListener(activity, new
//                        OnSuccessListener<Location>() {
//                            @Override
//                            public void onSuccess(Location location) {
//                                Log.i("Location", "started!");
//                                if (location != null) {
//                                    Log.i("Location", "success!");
//                                    Geocoder geocoder = new Geocoder(getActivity().getBaseContext(),
//                                            Locale.getDefault());
//                                    try {
//                                        Address address =
//                                                geocoder.getFromLocation(location.getLatitude(),
//                                                        location.getLongitude(), 1).get(0);
//                                        Log.i("Location", "my location is " +
//                                                address.getLocality());
//
//                                        textCity = address.getLocality();
//
//                                        if (textCity == null)
//                                            textCity = "Местоположение не найдено";
//
//                                    } catch (IOException e) {
//
//                                        Log.i("Location", "error!");
//                                        e.printStackTrace();
//                                    }
//                                } else {
//                                    textCity = "Нет данных";
//                                    Log.i("Location", "null");
//                                }
//
//                                mRecyclerView = (RecyclerView)view.findViewById(R.id.save_city);
//                                mRecyclerView.setHasFixedSize(true);
//                                mLayoutManager = new LinearLayoutManager(getActivity());
//                                mRecyclerView.setLayoutManager(mLayoutManager);
//
//                                List<String> recyclerData = new ArrayList<String>() {{
//                                    add(textCity);
//                                }};
//                                mAdapter = new AdapterSavedCity(recyclerData);
//                                mRecyclerView.setAdapter(mAdapter);
//                            }
//                        })
//                .addOnFailureListener(activity, new
//                        OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.i("Location", "failed!");
//                            }
//                        });
//    }
}