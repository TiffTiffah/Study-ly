package com.example.studyly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Tasks extends AppCompatActivity {

    //create object bottom nav menu
    private BottomNavigationView bottom_nav_menu;
    private RecyclerView tasksRecyclerView;
    private ToDoAdapter tasksAdapter;
    private List<ToDoModel> taskList;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tasks);


            reference = FirebaseDatabase.getInstance().getReference("Tasks");
            taskList = new ArrayList<>();


            tasksRecyclerView=findViewById(R.id.taskRecyclerview);
            tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            tasksAdapter = new ToDoAdapter(this);

            tasksRecyclerView.setAdapter(tasksAdapter);


            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        ToDoModel tasks = dataSnapshot.getValue(ToDoModel.class);
                        taskList.add(tasks);
                    }
                    tasksAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            tasksAdapter.setTasks(taskList);


        //reference the bottom nav menu
        bottom_nav_menu = findViewById(R.id.bottom_nav);

        //setting the activity to the selected item
        bottom_nav_menu.setSelectedItemId(R.id.tasks);


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
}