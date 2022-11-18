package com.example.studyly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class GetStarted extends AppCompatActivity {


    //authentication
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);


//        if(mAuth.getCurrentUser()!= null)
//        {
//            Intent intent = new Intent(getApplicationContext(),HomePage.class);
//            startActivity(intent);
//            finish();
//        }

        Button get_btn = (Button) findViewById(R.id.getstartedbtn);
        get_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),SignUp.class);
                startActivity(intent);

            }

        });
    }
}