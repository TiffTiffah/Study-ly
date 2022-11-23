package com.example.studyly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Notepage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID,task;
    private EditText add_noteTitle, add_note;
    private Button add_note_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepage);



        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference("Notes").child(userID).child("note");

        //find recyclerView
        recyclerView = findViewById(R.id.recycleview);


        //add data to the database
        add_note_btn = findViewById(R.id.save_notes);
        add_noteTitle = findViewById(R.id.note_title);
        add_note = findViewById(R.id.note_page);

        add_note_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String str_addTitle= add_noteTitle.getText().toString();
                String str_addNote = add_note.getText().toString();

                Note note = new Note(str_addTitle,str_addNote);
                reference.push().setValue(note).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Notepage.this, "Note added!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(Notepage.this, "Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

}