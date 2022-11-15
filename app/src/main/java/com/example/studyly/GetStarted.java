package com.example.studyly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GetStarted extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);


        Button get_btn = (Button) findViewById(R.id.get_button);
        get_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),SignUp.class);
                startActivity(intent);
                finish();
            }

        });
    }
}