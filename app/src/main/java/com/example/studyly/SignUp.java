package com.example.studyly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {





        //links
        private TextView sign_in_link;
        private Button sign_up_btn;

        //defines data
        private EditText reg_name, reg_email,reg_password1, reg_password2;

        //authentication
        private FirebaseAuth mAuth;
        private  DatabaseReference databaseReference;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sign_up);


            //initializing firebase instance
            mAuth = FirebaseAuth.getInstance();

            //referencing
            reg_email = findViewById(R.id.email);
            reg_name = findViewById(R.id.full_name);
            reg_password1 = findViewById(R.id.password);
            reg_password2 = findViewById(R.id.confirm_password);

            //confirm if the user exists, for the user to use the screen without signing up again
            if(mAuth.getCurrentUser()!= null)
            {
                Intent intent = new Intent(getApplicationContext(),HomePage.class);
                startActivity(intent);
                finish();
            }

            //on clicking the signup button
            sign_up_btn = findViewById(R.id.signup_btn);
            sign_up_btn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    //get the user input data
                    String str_email = reg_email.getText().toString();
                    String str_password = reg_password1.getText().toString();
                    String str_confirm_password = reg_password2.getText().toString();
                    String str_full_name = reg_name.getText().toString();


                    //validating the user's input
                    if(TextUtils.isEmpty(str_email))
                    {
                        reg_email.setError("Email is required!");
                        return;
                    }
                    if(TextUtils.isEmpty(str_full_name))
                    {
                        reg_name.setError("Fullname is required!");
                        return;
                    }
                    if(TextUtils.isEmpty(str_password))
                    {
                        reg_password1.setError("Password is required!");
                        return;
                    }
                    if(TextUtils.isEmpty(str_confirm_password))
                    {
                        reg_password2.setError("Confirm password is required!");
                        return;
                    }
                    if(str_password.length() < 8)
                    {
                        reg_password1.setError("Password must be atleast 8 characters long!");
                        return;
                    }
                    if(!str_confirm_password.equals(str_password))
                    {
                        reg_password2.setError("Passwords do not match!");
                        return;
                    }
                    //validation complete

                    //create user
                    mAuth.createUserWithEmailAndPassword(str_email,str_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {


                                        User user = new User(str_full_name,str_email,str_confirm_password);

                                        //send data to the database
                                        FirebaseDatabase.getInstance().getReference("Users")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                        .setValue(user);

                                        Toast.makeText(SignUp.this,"Successful Registration!",
                                                Toast.LENGTH_SHORT).show();

                                        //if signup is successful takes the user to the HomePage
                                        Intent intent = new Intent(SignUp.this, HomePage.class);
                                        startActivity(intent);

                                    }
                                    else
                                    {
                                        Toast.makeText(SignUp.this,"Registration Failed!",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });



                }
            });

        //if a user already has an account
        sign_in_link = findViewById(R.id.signin_link);
        sign_in_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SignIn.class);
                startActivity(intent);
            }
        });
    }



}