package com.example.c2327190.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class logIN extends AppCompatActivity implements View.OnClickListener{

    private TextView register;
    private EditText logEmail, logPassword;
    private Button signIn;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private FirebaseAuth ParseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        signIn = (Button) findViewById(R.id.signIn);
        signIn.setOnClickListener(this);

        logEmail = (EditText) findViewById(R.id.logEmail);
        logPassword = (EditText) findViewById(R.id.logPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.register:
                startActivity(new Intent(this, signUP.class));
                break;

            case R.id.signIn:
                userLogin();
                break;
        }
    }
    private void userLogin(){
        String email = logEmail.getText().toString().trim();
        String password = logPassword.getText().toString().trim();

        if (email.isEmpty()){
            logEmail.setError("Email is required!");
            logEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            logEmail.setError("Please enter a valid Email!");
            logEmail.requestFocus();
            return;
        }
        if (password.isEmpty()){
            logPassword.setError("Password is required!");
            logPassword.requestFocus();
            return;
        }
        if (password.length() < 6){
            logPassword.setError("Min password length is 6 characters");
            logPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password). addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    // redirect to user profile
                    startActivity(new Intent(logIN.this, MainActivity.class));

                }else{
                    Toast.makeText(logIN.this, "Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void redirectIfLoggedIn(){
        if(ParseUser.getCurrentUser()!=null){
            Intent intent = new Intent(logIN.this, MainActivity.class);
            startActivity(intent);
        }
    }



}