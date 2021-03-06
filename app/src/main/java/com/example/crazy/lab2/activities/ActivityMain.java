package com.example.crazy.lab2.activities;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.crazy.lab2.fragments.FragmentChooseCity;
import com.example.crazy.lab2.fragments.FragmentSavedCity;
import com.example.crazy.lab2.fragments.FragmentWhether;
import com.example.crazy.lab2.fragments.FragmentAboutAuthor;
import com.example.crazy.lab2.R;
import com.example.crazy.lab2.interfaces.CityClickResponse;

public class ActivityMain extends AppCompatActivity implements CityClickResponse {
    public long userId;

    FragmentChooseCity fragChooseCity = new FragmentChooseCity();
    FragmentSavedCity fragSavedCity = new FragmentSavedCity();
    FragmentWhether fragWhether = new FragmentWhether();
    FragmentAboutAuthor fragAboutAuthor = new FragmentAboutAuthor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null)
            itemResponse("Таганрог");

        Intent intent = getIntent();
        String login = intent.getStringExtra("login");
        userId = intent.getLongExtra("userId", -1);
        getSupportActionBar().setTitle(login);

        final DrawerLayout mDrawerLayout = findViewById(R.id.my_drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        FragmentTransaction _fragmentTransaction = getSupportFragmentManager().beginTransaction();

                        switch (menuItem.getItemId()) {
                            case R.id.choose_city:
                                _fragmentTransaction.replace(R.id.fragment_place, fragChooseCity);
                                break;
                            case R.id.saved_city:
                                _fragmentTransaction.replace(R.id.fragment_place, fragSavedCity);
                                break;
                            case R.id.whether:
                                _fragmentTransaction.replace(R.id.fragment_place, fragWhether);
                                break;
                            case R.id.about_author:
                                _fragmentTransaction.replace(R.id.fragment_place, fragAboutAuthor);
                                break;
                        }
                        _fragmentTransaction.addToBackStack(null);
                        _fragmentTransaction.commit();

                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();

                        return true;
                    }
                });
    }

    @Override
    public void itemResponse(String city) {
        Bundle bundle = new Bundle();
        bundle.putString("city", city);
        fragWhether.setArguments(bundle);

        FragmentTransaction _fragmentTransaction = getSupportFragmentManager().beginTransaction();
        _fragmentTransaction.replace(R.id.fragment_place, fragWhether);
        _fragmentTransaction.addToBackStack(null);
        _fragmentTransaction.commit();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(2).setChecked(true);
    }
}
