package com.example.studyly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomePage extends AppCompatActivity {

    //create object bottom nav menu
    BottomNavigationView bottom_nav_menu;
    Button new_note;
    Button new_event;
    Button new_tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        //create a new note
        new_note = findViewById(R.id.create_note);
        new_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNote();
            }
        });
        new_tasks = findViewById(R.id.create_tasks);
        new_event = findViewById(R.id.create_event);


            //reference the bottom nav menu
            bottom_nav_menu = findViewById(R.id.bottom_nav);

            //setting the activity to the selected item
            bottom_nav_menu.setSelectedItemId(R.id.home);


            //if the item that is not selected is clicked, open the activity

            bottom_nav_menu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId())
                    {
                        //if the selected activity which is home is selected just set true
                        case R.id.home:
                            return true;

                        case R.id.tasks:
                            startActivity(new Intent(getApplicationContext(),Tasks.class));
                            overridePendingTransition(0,0);
                            return true;

                        case R.id.events:
                            startActivity(new Intent(getApplicationContext(),Events.class));
                            overridePendingTransition(0,0);
                            return true;

                        case R.id.notes:
                            startActivity(new Intent(getApplicationContext(),Notes.class));
                            overridePendingTransition(0,0);
                            return true;
                        case R.id.settings:
                            startActivity(new Intent(getApplicationContext(),Settings.class));
                            overridePendingTransition(0,0);
                            return true;
                    }
                    return false;
                }
            });

        }


        //new note function
    private void addNote() {
        AlertDialog.Builder noteDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);

        View note_view = inflater.inflate(R.layout.activity_new_note, null);
                noteDialog.setView(note_view);

        AlertDialog dialog = noteDialog.create();
        dialog.setCancelable(false);
        dialog.show();


    }
}