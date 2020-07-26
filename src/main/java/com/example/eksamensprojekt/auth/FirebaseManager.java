package com.example.eksamensprojekt.auth;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.eksamensprojekt.LogOverview;
import com.example.eksamensprojekt.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FirebaseManager {

    private static final String TAG = "FirebaseManager";

    private boolean signInStatus = false;
    FirebaseAuth auth;

    //Constructor which assign value to auth, and runs the auth state listener method
    public FirebaseManager() {
        auth = FirebaseAuth.getInstance();
        setupAuthStateListener();
    }

    //Listener for knowing if a user is logged in or not (recognised in the list of users on firebase)
    private void setupAuthStateListener(){
        auth.addIdTokenListener(new FirebaseAuth.IdTokenListener() {
            @Override
            public void onIdTokenChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null){
                    Log.i(TAG,"Signed out from firebase");
                }else{
                    Log.i(TAG,"Signed in to firebase");
                }
            }
        });
    }

    //Method for signing in an existing user
    public boolean signIn(String email, String pwd, final MainActivity activity){
        auth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //If login is successful write it in log, start new intent for LogOverview class, and finish MainActivity so user can't return to login screen without logging out
                    Log.i(TAG,"LOGGED IN " + task.getResult().getUser().getEmail());
                    Intent intentLogin = new Intent(activity, LogOverview.class);
                    activity.startActivity(intentLogin);
                    activity.finish();
                    signInStatus = true;
                }else{
                    //If login failed write exception in logs, and display toast to user
                    Log.i(TAG,"Login failed " + task.getException());
                    Toast.makeText(activity, "Login Failed", Toast.LENGTH_SHORT).show();
                    signInStatus = false;

                }
            }
        });
        return signInStatus;
    }

    //Method for signing up a new user
    public void signUp(String email, String pwd){
        //Listener which gives response when the createUser method has run
        auth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.i(TAG, "Sign up Success " + task.getResult().getUser().getEmail());

                }else{
                    Log.i(TAG,"Sign up failed " + task.getException());

                }
            }
        });
    }

    //Method for logging out the user
    public void logout(){
        auth.signOut();
       Log.i(TAG, "logged out");
    }

}