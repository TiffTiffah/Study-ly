package com.example.studyly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Events extends AppCompatActivity {

    BottomNavigationView bottom_nav_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        //reference the bottom nav menu
        bottom_nav_menu = findViewById(R.id.bottom_nav);

        //setting the activity to the selected item
        bottom_nav_menu.setSelectedItemId(R.id.events);


        //if the item that is not selected is clicked, open the activity

        bottom_nav_menu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    //if the selected activity which is home is selected just set true
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),HomePage.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.tasks:
                        startActivity(new Intent(getApplicationContext(),Tasks.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.events:

                        return true;


                    case R.id.notes:
                        startActivity(new Intent(getApplicationContext(),Notes.class));
                        overridePendingTransition(0,0);
                        return true;


                    case R.id.settings_page:
                        startActivity(new Intent(getApplicationContext(),Options.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }


}