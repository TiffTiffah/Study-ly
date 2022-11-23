package com.example.studyly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Tasks extends AppCompatActivity {

    BottomNavigationView bottom_nav_menu;
    private RecyclerView tasksRecyclerView;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID,task;
    private EditText add_taskText;
    private Button add_task_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);


        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference("Tasks").child(userID).child("task");

        //find recyclerView
        tasksRecyclerView = findViewById(R.id.taskRecyclerview);


        //add data to the database
        add_taskText = findViewById(R.id.etNewItem);
        add_task_btn = findViewById(R.id.add_task_button);


        add_task_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String str_addTask = add_taskText.getText().toString();

                TaskModel taskModel = new TaskModel(str_addTask);
                reference.push().setValue(taskModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Tasks.this, "Task added!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(Tasks.this, "Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });





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


                    case R.id.settings_page:
                        startActivity(new Intent(getApplicationContext(),Options.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<TaskModel> options = new FirebaseRecyclerOptions.Builder<TaskModel>()
                .setQuery(reference,TaskModel.class)
                .build();

        FirebaseRecyclerAdapter<TaskModel,TaskViewHolder> adapter = new FirebaseRecyclerAdapter<TaskModel, TaskViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull TaskViewHolder holder, int position, @NonNull TaskModel model) {

                holder.setTask(model.getTask());
            }

            @NonNull
            @Override
            public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_design,parent,false);
                return new TaskViewHolder(view);
            }
        };

        tasksRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public static  class TaskViewHolder extends RecyclerView.ViewHolder{
        View tView;

        public TaskViewHolder(@NonNull View itemView){
            super(itemView);

            tView =itemView;

        }
        public void setTask(String task){
            CheckBox todo = tView.findViewById(R.id.todoCheckBox);
            TextView taskTextView = tView.findViewById(R.id.fetchedTask);
            taskTextView.setText(task);
        }
    }





}