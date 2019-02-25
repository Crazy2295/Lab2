package com.example.crazy.lab2;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    TextView login;

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        login = (TextView) findViewById(R.id.textLogin);

        Intent intent = getIntent();

        String iLogin = intent.getStringExtra("iLogin");

        login.setText(iLogin);

        mDrawerLayout = findViewById(R.id.my_drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch(menuItem.getItemId()) {
                            case R.id.choose_city:
                                Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                                intent.putExtra("type", "0");
                                startActivity(intent);
                                break;
                            case R.id.saved_city:
                                Intent intent1 = new Intent(Main2Activity.this, Main3Activity.class);
                                intent1.putExtra("type", "1");
                                startActivity(intent1);
                                break;
                            case R.id.whether:
                                Intent intent2 = new Intent(Main2Activity.this, Main3Activity.class);
                                intent2.putExtra("type", "2");
                                startActivity(intent2);
                                break;
                            case R.id.author:
                                Intent intent3 = new Intent(Main2Activity.this, Main3Activity.class);
                                intent3.putExtra("type", "3");
                                startActivity(intent3);
                                break;
                        }
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();

                        return true;
                    }
                });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.choose_city:
                Intent intent = new Intent(this, Main3Activity.class);
                this.startActivity(intent);
                break;
            case R.id.saved_city:
                // another startActivity, this is for item with id "menu_item2"
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
}
