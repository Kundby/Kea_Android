package com.example.eksamensprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eksamensprojekt.auth.FirebaseManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSignIn, btnSignUp;
    private String email, password;
    private EditText emailText, passwordText;
    private FirebaseManager firebaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);

        btnSignIn = findViewById(R.id.buttonSignIn);
        btnSignIn.setOnClickListener(this);
        btnSignUp = findViewById(R.id.buttonSignUp);
        btnSignUp.setOnClickListener(this);
        //New instance of FirebaseManager
        firebaseManager = new FirebaseManager();
        //Call logout method in onCreate to improve security
        firebaseManager.logout();
    }



    //Handles onClick events, using a switch
    @Override
    public void onClick(View v) {
        switch (v.getId()){
                //Sign in button pressed, runs method to get text, then checks length of user input. If long enough run signIn method, else display toast to user
            case R.id.buttonSignIn:
                fetchInput();
                if (email.length() > 0 && password.length() > 0){
                    if (firebaseManager.signIn(email, password, this)){
                    }
                }else{
                    Toast.makeText(this, "Please fill both fields", Toast.LENGTH_SHORT).show();
                }
                break;
                //Sign up button pressed, runs method to get text, then checks length of user input. If long enough run signUp method, else display toast to user
            case R.id.buttonSignUp:
                fetchInput();
                if (email.length() > 0 && password.length() > 0){
                    firebaseManager.signUp(email, password);
                }else{
                    Toast.makeText(this, "Please fill both fields", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    //Grabs the info from 2 EditText fields
    private void fetchInput() {
        email = emailText.getText().toString();
        password = passwordText.getText().toString();
    }
}
