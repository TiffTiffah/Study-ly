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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomePage extends AppCompatActivity {

    //create object bottom nav menu
    BottomNavigationView bottom_nav_menu;
    Button new_note;
    Button new_event;
    Button new_tasks;
    Button add_task;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

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
        new_tasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }
        });

        new_event = findViewById(R.id.create_event);


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();


        final TextView greetings = findViewById(R.id.greetings);
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null)
                {

                    String username = userProfile.username;


                    greetings.setText("Hello "+username+"!");



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(HomePage.this, "Something wrong happened!", Toast.LENGTH_SHORT).show();
            }
        });







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


                    case R.id.settings_page:
                        startActivity(new Intent(getApplicationContext(),Options.class));
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
        dialog.setCancelable(true);
        dialog.show();

        //hooks
        final ImageView cancel = note_view.findViewById(R.id.cancel);
        final Button add_note = note_view.findViewById(R.id.save_notes);
        final TextView note_title = note_view.findViewById(R.id.note_title);
        final TextView note_content = note_view.findViewById(R.id.note_page);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Notes");
        userID = user.getUid();

        //close the alert dialog
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        //save the notes to database
        add_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child(userID).child("Title").push().setValue(note_title.getText().toString());
                reference.child(userID).child("Content").push().setValue(note_content.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(HomePage.this, "Note added!", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                                else {
                                    Toast.makeText(HomePage.this, "Task failed to save!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });


    }
    //newTask function
    private void addTask() {
        AlertDialog.Builder taskDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);

        View task_view = inflater.inflate(R.layout.activity_new_task, null);
        taskDialog.setView(task_view);

        AlertDialog dialog = taskDialog.create();
        dialog.setCancelable(false);
        dialog.show();


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Tasks").child(userID).child("tasks");
        userID = user.getUid();

        //hooks
        final ImageView cancel = task_view.findViewById(R.id.cancel);
        final Button add_task = task_view.findViewById(R.id.addTask_btn);
         TextView description = task_view.findViewById(R.id.task_descrpt);

        //close the alert dialog
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        //save the tasks to database
        add_task.setOnClickListener(new View.OnClickListener() {


            String descrpt = description.getText().toString();
            @Override
            public void onClick(View view) {
                TaskModel taskModel = new TaskModel(descrpt);
                reference.push().setValue(taskModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(HomePage.this, "Task added!", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                                else {
                                    Toast.makeText(HomePage.this, "Task failed to save!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });





    }
}