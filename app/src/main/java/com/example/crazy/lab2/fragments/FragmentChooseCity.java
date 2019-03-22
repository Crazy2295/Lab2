package com.example.crazy.lab2.fragments;

import android.database.Cursor;
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
import com.example.crazy.lab2.interfaces.CityClickResponse;
import com.example.crazy.lab2.utils.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class FragmentChooseCity extends Fragment {
    DBHelper dbHelper;

    private SwipeRefreshLayout swipeRefreshLayout;

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
                getCities();
            }
        });

        mRecyclerView = (RecyclerView)view.findViewById(R.id.cities_rec);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new AdapterChooseCity(recyclerData, (ActivityMain)getActivity(),
                (ActivityMain)getActivity(), ((ActivityMain)getActivity()).userId, 0);
        mRecyclerView.setAdapter(mAdapter);

        swipeRefreshLayout.setRefreshing(true);
        getCities();

        return view;
    }

    void getCities () {
        List<String> outputList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("cities", new String[] {"name"}, null,
                null, null, null, "name");

        cursor.moveToFirst();
        int nameColIndex = cursor.getColumnIndex("name");
        do {
            outputList.add(cursor.getString(nameColIndex));
        } while (cursor.moveToNext());
        cursor.close();

        recyclerData.clear();
        recyclerData.addAll(outputList);
        mAdapter.notifyDataSetChanged();

        swipeRefreshLayout.setRefreshing(false);
    }
}