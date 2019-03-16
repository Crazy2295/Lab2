package com.example.crazy.lab2.activities;

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

public class ActivityMain extends AppCompatActivity {
    FragmentChooseCity frag1 = new FragmentChooseCity();
    FragmentSavedCity frag2 = new FragmentSavedCity();
    FragmentWhether frag3 = new FragmentWhether();
    FragmentAboutAuthor frag4 = new FragmentAboutAuthor();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null)
            clickToCity("Таганрог");


        final DrawerLayout mDrawerLayout = findViewById(R.id.my_drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        FragmentTransaction _fragmentTransaction = getSupportFragmentManager().beginTransaction();

                        switch (menuItem.getItemId()) {
                            case R.id.choose_city:
                                _fragmentTransaction.replace(R.id.fragment_place, frag1);
                                break;
                            case R.id.saved_city:
                                _fragmentTransaction.replace(R.id.fragment_place, frag2);
                                break;
                            case R.id.whether:
                                _fragmentTransaction.replace(R.id.fragment_place, frag3);
                                break;
                            case R.id.about_author:
                                _fragmentTransaction.replace(R.id.fragment_place, frag4);
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

    public void clickToCity(String city) {
        Bundle bundle = new Bundle();
        bundle.putString("city", city);
        frag3.setArguments(bundle);

        FragmentTransaction _fragmentTransaction = getSupportFragmentManager().beginTransaction();
        _fragmentTransaction.replace(R.id.fragment_place, frag3);
        _fragmentTransaction.addToBackStack(null);
        _fragmentTransaction.commit();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(2).setChecked(true);
    }
}
