package com.example.studyly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfile extends AppCompatActivity {
ImageView back_button;
Button update;

private FirebaseUser user;
private DatabaseReference reference;
private String userID, name, email, password, confirm_password, username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        back_button = findViewById(R.id.back_arrow);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),Options.class));
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final EditText name_view = (EditText) findViewById(R.id.edit_name);
        final EditText email_view = (EditText) findViewById(R.id.edit_email);
        final EditText password_view = (EditText) findViewById(R.id.edit_password);
        final EditText con_password_view = (EditText) findViewById(R.id.new_confirm_pass);
        final EditText username_view = (EditText) findViewById(R.id.edit_username);


        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null)
                {
                     name =userProfile.full_name;
                     email = userProfile.email;
                     password  =userProfile.password;
                     confirm_password =userProfile.password;
                     username =userProfile.username;

                    name_view.setText(name);
                    email_view.setText(email);
                    password_view.setText(password);
                    con_password_view.setText(password);
                    username_view.setText(username);



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(EditProfile.this, "Something wrong happened!", Toast.LENGTH_SHORT).show();
            }
        });


        //update user profile

         update = findViewById(R.id.edit_save);
         update.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 reference.child(userID).child("full_name").setValue(name_view.getText().toString());
                 reference.child(userID).child("username").setValue(username_view.getText().toString());
                 reference.child(userID).child("email").setValue(email_view.getText().toString());
                 reference.child(userID).child("password").setValue(password_view.getText().toString());

                 Toast.makeText(EditProfile.this, "Changes Made!", Toast.LENGTH_SHORT).show();



             }
         });


    }

    private boolean isPasswordChanged() {

        return true;
    }

    private boolean isEmailChanged() {
        return  true;
    }

    private boolean isNameChanged() {
        return  true;
    }
}