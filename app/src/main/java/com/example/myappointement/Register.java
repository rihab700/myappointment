package com.example.myappointement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private TextView welcome_re,registeruser;
    private EditText editTextTextPersonName,editTextAge,editTextTextEmailAddress,editTextTextPassword;;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        welcome_re = (TextView) findViewById(R.id.welcome_re);
        welcome_re.setOnClickListener(this);
        registeruser=(Button) findViewById(R.id.registeruser);
        registeruser.setOnClickListener(this);

        editTextTextPersonName=(EditText) findViewById(R.id.editTextTextPersonName);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextTextEmailAddress= (EditText) findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword = (EditText) findViewById(R.id.editTextTextPassword);
    }

    @Override
    public void onClick(View v) {
    switch (v.getId()){

        case R.id.welcome_re:
            startActivity( new Intent(this,MainActivity.class));
            break;
        case R.id.registeruser:
            registeruser();
            break;

    }
    }

    private void registeruser() {
        String email= editTextTextEmailAddress.getText().toString().trim();
        String password = editTextTextPassword.getText().toString().trim();
        String fullname = editTextTextPersonName.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();

        if(fullname.isEmpty()){
            editTextTextPersonName.setError("full name is required");
            editTextTextPersonName.requestFocus();
            return;
        }
        if(age.isEmpty()){
            editTextAge.setError("age is required");
            editTextAge.requestFocus();
            return;
        }
        if(email.isEmpty()){
            editTextTextEmailAddress.setError(" Email is requiered");
            editTextTextEmailAddress.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextTextEmailAddress.setError("please enter a valid email");
            editTextTextEmailAddress.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextTextPassword.setError("password is required");
            editTextTextPassword.requestFocus();
            return;
        }
        if(password.length()<6){
            editTextTextPassword.setError("password min lengh is 6 characters!");
            editTextTextPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Users users = new Users(fullname,age,email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                     .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Register.this, "User has been registred successfully", Toast.LENGTH_LONG).show();

                                    }else{
                                        Toast.makeText(Register.this," Failed to register! Try again", Toast.LENGTH_LONG).show();
                                    }

                                }
                            });

                        }else {
                            Toast.makeText(Register.this," Failed to register! Try again", Toast.LENGTH_LONG).show();


                        }
                    }
                });

    }
}