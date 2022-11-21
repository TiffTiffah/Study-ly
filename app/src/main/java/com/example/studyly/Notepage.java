package com.example.studyly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;

public class Notepage extends AppCompatActivity {

    DatabaseReference dbRef;
    Button save;
    EditText createTitle;
    EditText createNote;
    boolean isNoteChecked = false;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_page);

        createTitle = findViewById(R.id.createTitle);
        createNote = findViewById(R.id.createNote);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Notes");
        userID = user.getUid();



        //save the notes to database
        save = findViewById(R.id.addNote);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child(userID).child("Title").push().setValue(createTitle.getText().toString());
                reference.child(userID).child("Content").push().setValue(createNote.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(Notepage.this, "Note added!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),Notes.class));

                                }
                                else {
                                    Toast.makeText(Notepage.this, "Note failed to save!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }

}