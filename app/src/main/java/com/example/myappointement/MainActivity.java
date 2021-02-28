package com.example.myappointement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register,forget;
    private EditText EmailAddress,password;
    private Button signin;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register= (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        signin = (Button) findViewById(R.id.signin);
        signin.setOnClickListener(this);

        EmailAddress= (EditText) findViewById(R.id.EmailAddress);
        password =(EditText) findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();

        forget = (TextView) findViewById(R.id.forget);
        forget.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                startActivity(new Intent(this, Register.class));
                break;
            case R.id.signin:
                userLogin();
                break;
            case R.id.forget:
                startActivity(new Intent(this, forgotpassword.class));
                break;

        }
    }

    private void userLogin() {
        String email = EmailAddress.getText().toString().trim();
        String pwd = password.getText().toString().trim();

        if(email.isEmpty()){
            EmailAddress.setError("Email is required");
            EmailAddress.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            EmailAddress.setError("Please entre valid email");
            EmailAddress.requestFocus();
            return;
        }
        if(pwd.isEmpty()){
            password.setError("password is required!");
            password.requestFocus();
            return;
        }
        if(pwd.length()<6){
            password.setError("Min password lengh is 6 characters!");
            password.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                   FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                   if(user.isEmailVerified()){
                       // direcet to user profile
                       startActivity(new Intent(MainActivity.this, profileActivity.class));

                   }else{
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "Check email to verify your account!",Toast.LENGTH_LONG).show();


                   }

               }else{
                   Toast.makeText(MainActivity.this,"Failed to login! Please  check your credentials",Toast.LENGTH_LONG).show();
               }
            }
        });



    }
}
