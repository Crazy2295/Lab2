package com.example.crazy.lab2.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.crazy.lab2.R;
import com.example.crazy.lab2.activities.ActivityMain;
import com.example.crazy.lab2.adapters.AdapterSavedCity;
import com.example.crazy.lab2.utils.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class FragmentForTesting extends Fragment {
    DBHelper dbHelper;

    private List<String> recyclerData = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_for_testing, container, false);

        List<String> citiesList = new ArrayList<>();


        dbHelper = new DBHelper(((ActivityMain)getActivity()).getBaseContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query("Cities", new String[]{"name"}, null,
                null, null, null, "name");
        cursor.moveToFirst();
        int nameColIndex = cursor.getColumnIndex("name");
        do {
            citiesList.add(cursor.getString(nameColIndex));
        } while (cursor.moveToNext());
        cursor.close();

        mRecyclerView = (RecyclerView)view.findViewById(R.id.cities_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new AdapterSavedCity(citiesList);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}