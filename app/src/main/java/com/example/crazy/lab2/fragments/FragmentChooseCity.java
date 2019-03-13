package com.example.crazy.lab2.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.crazy.lab2.adapters.AdapterChooseCity;
import com.example.crazy.lab2.R;

import java.util.Arrays;
import java.util.List;

public class FragmentChooseCity extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_city, container, false);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.cities_rec);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        String[] myString = getResources().getStringArray(R.array.city_arr);
        List<String> recyclerData = Arrays.asList(myString);
        mAdapter = new AdapterChooseCity(recyclerData);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}