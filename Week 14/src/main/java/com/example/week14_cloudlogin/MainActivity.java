package com.example.week14_cloudlogin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.week14_cloudlogin.auth.FirebaseManager;
import com.facebook.login.widget.LoginButton;

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
        firebaseManager = new FirebaseManager();
        firebaseManager.logout();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.buttonSignIn:
                email = emailText.getText().toString();
                password = passwordText.getText().toString();
                if (email.length() > 0 && password.length() > 0){
                    if (firebaseManager.signIn(email, password, this)){
                        //Bug lige pt, som kræver 2 tryk på login før man sendes videre. Det sker pga firebase laver Async kald, som tager tid før man får et svar
                        finish();
                        Intent intentLogin = new Intent(this, NotesActivity.class);
                        startActivity(intentLogin);
                        break;
                    }
                }else{
                    Toast.makeText(this, "Please fill both fields", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.buttonSignUp:
                email = emailText.getText().toString();
                password = passwordText.getText().toString();
                if (email.length() > 0 && password.length() > 0){
                    firebaseManager.signUp(email, password);
                }else{
                    Toast.makeText(this, "Please fill both fields", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }
}
