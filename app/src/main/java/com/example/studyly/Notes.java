package com.example.studyly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Notes extends AppCompatActivity implements  RecyclerViewInterface{

    RecyclerView recyclerView;
    List<Note> list;
    ListAdapter adapter;
    DatabaseReference notesDbRef;

    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);


        notesDbRef = FirebaseDatabase.getInstance().getReference("Notes");
        recyclerView= findViewById(R.id.recycleview);
        list = new ArrayList<>();

        adapter = new ListAdapter(this, (ArrayList<Note>) list, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notesDbRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Note note = dataSnapshot.getValue(Note.class);
                    list.add(note);
                }
                adapter.notifyDataSetChanged();
            }

//

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        fab = findViewById(R.id.add_new_note);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),Notepage.class));
                finish();
            }
        });

    }




    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "note clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(int pos) {
        list.remove(pos);
        Toast.makeText(this, "note deleted", Toast.LENGTH_SHORT).show();
        adapter.notifyItemRemoved(pos);
    }
}