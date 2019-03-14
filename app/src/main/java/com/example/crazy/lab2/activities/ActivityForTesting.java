package com.example.crazy.lab2.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.crazy.lab2.fragments.FragmentForTesting;
import com.example.crazy.lab2.R;

public class ActivityForTesting extends AppCompatActivity {
    FragmentForTesting fragTest = new FragmentForTesting();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_testing);

        if (savedInstanceState == null) {
            FragmentTransaction _fragmentTransaction = getSupportFragmentManager().beginTransaction();
            _fragmentTransaction.replace(R.id.test_fragment_place, fragTest);
            _fragmentTransaction.commit();
        }
    }
}