package com.example.week14_cloudlogin.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.week14_cloudlogin.MainActivity;
import com.facebook.AccessToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

public class FirebaseManager {
    private boolean signInStatus = false;
    FirebaseAuth auth;

    public FirebaseManager() {
        auth = FirebaseAuth.getInstance();
        setupAuthStateListener();
    }

    private void setupAuthStateListener(){
        auth.addIdTokenListener(new FirebaseAuth.IdTokenListener() {
            @Override
            public void onIdTokenChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null){
                    System.out.println("Signed out from firebase");
                }else{
                    System.out.println("Signed in to firebase");
                }
            }
        });
    }

    public boolean signIn(String email, String pwd, final MainActivity activity){
        auth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    System.out.println("LOGGED IN " + task.getResult().getUser().getEmail());
                    signInStatus = true;
                }else{
                    Log.e("FirebaseManager", "Login failed: " + task.getException().getMessage());
                    System.out.println("Login failed " + task.getException());
                    signInStatus = false;

                }
            }
        });
        return signInStatus;
    }

    public void signUp(String email, String pwd){
        auth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    System.out.println("Sign up Success " + task.getResult().getUser().getEmail());

                }else{
                    Log.e("FirebaseManager", "Sign up failed: " + task.getException().getMessage());
                    System.out.println("Sign up failed " + task.getException());

                }
            }
        });
    }

    public void logout(){
        auth.signOut();
        System.out.println("logged out");
    }

}
