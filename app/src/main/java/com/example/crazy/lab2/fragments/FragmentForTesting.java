package com.example.crazy.lab2.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.crazy.lab2.adapters.AdapterChooseCity;
import com.example.crazy.lab2.R;
import com.example.crazy.lab2.interfaces.AsyncForTesting;
import com.example.crazy.lab2.utils.AsyncTaskForTesting;

import java.util.ArrayList;
import java.util.List;

public class FragmentForTesting extends Fragment implements AsyncForTesting {
    AsyncTaskForTesting asyncTaskForTesting = null;

    private SwipeRefreshLayout swipeRefreshLayout;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    private List<String> recyclerData = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_for_testing, container, false);

        swipeRefreshLayout = view.findViewById(R.id.test_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callAsyncTask();
            }
        });

        callAsyncTask();

        mRecyclerView = (RecyclerView)view.findViewById(R.id.test_rec);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new AdapterChooseCity(recyclerData);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    void callAsyncTask () {
        asyncTaskForTesting = new AsyncTaskForTesting();
        asyncTaskForTesting.delegate = this;
        asyncTaskForTesting.execute();
    }

    @Override
    public void processFinish(List<String> output) {
        recyclerData.clear();
        recyclerData.addAll(output);
        mAdapter.notifyDataSetChanged();

        swipeRefreshLayout.setRefreshing(false);
    }
}