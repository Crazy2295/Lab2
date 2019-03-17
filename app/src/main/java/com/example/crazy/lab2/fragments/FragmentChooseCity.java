package com.example.crazy.lab2.fragments;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.crazy.lab2.activities.ActivityMain;
import com.example.crazy.lab2.adapters.AdapterChooseCity;
import com.example.crazy.lab2.R;
import com.example.crazy.lab2.interfaces.AsyncResponse;
import com.example.crazy.lab2.interfaces.CityClickResponse;
import com.example.crazy.lab2.utils.AsyncTaskDownloadJSON;
import com.example.crazy.lab2.utils.DBHelper;
import com.example.crazy.lab2.utils.WhetherJSON;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FragmentChooseCity extends Fragment implements AsyncResponse, CityClickResponse {
    DBHelper dbHelper;

    private SwipeRefreshLayout swipeRefreshLayout;
    AsyncTaskDownloadJSON asyncTaskDownloadJSON = null;

    private List<String> recyclerData = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_city, container, false);

        dbHelper = new DBHelper(((ActivityMain)getActivity()).getBaseContext());

        swipeRefreshLayout = view.findViewById(R.id.test_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                callAsyncTask();
            }
        });

        callAsyncTask();

        mRecyclerView = (RecyclerView)view.findViewById(R.id.cities_rec);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new AdapterChooseCity(recyclerData);
        ((AdapterChooseCity) mAdapter).delegate = this;
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    void callAsyncTask () {
        asyncTaskDownloadJSON = new AsyncTaskDownloadJSON();
        asyncTaskDownloadJSON.delegate = this;
        asyncTaskDownloadJSON.execute();
    }

    @Override
    public void processFinish(List<WhetherJSON> output) {
        List<String> outputList = new ArrayList<>();
        Collections.shuffle(output);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for (int i = 0; i < output.size(); i++) {
            outputList.add(output.get(i).city);

            ContentValues  cv = new ContentValues();
            cv.put("name", output.get(i).city);
            db.insert("Cities", null, cv);
        }

        recyclerData.clear();
        recyclerData.addAll(outputList);
        mAdapter.notifyDataSetChanged();

        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void itemResponse(int number) {
        ((ActivityMain)getActivity()).clickToCity(recyclerData.get(number));
    }
}