package com.example.myappointement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotpassword extends AppCompatActivity {
    private EditText emailEditText;
    private Button resetPassword;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        emailEditText = (EditText) findViewById(R.id.editTextTextEmailAddress2);
        resetPassword = (Button) findViewById(R.id.resetPassword);

        auth = FirebaseAuth.getInstance();

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

        private void resetPassword(){
             String email  = emailEditText.getText().toString().trim();

             if(email.isEmpty()){
                 emailEditText.setError("Email is required");
                 emailEditText.requestFocus();
                 return;
             }
             if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                 emailEditText.setError("Please provide valide mail");
                 emailEditText.requestFocus();
                 return;
             }

           auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
               @Override
               public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(forgotpassword.this,"Check your email to reset password",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(forgotpassword.this,"Try again! Something went wrong", Toast.LENGTH_LONG).show();
                    }
               }
           });

        }

    }