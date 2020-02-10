package com.example.kea_week6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class About extends AppCompatActivity implements View.OnClickListener {

    Button button_front, button_page4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        button_front = findViewById(R.id.but_front);
        button_front.setOnClickListener(this);
        button_page4 = findViewById(R.id.but_contact2);
        button_page4.setOnClickListener(this);

        getSupportActionBar().setTitle("About");

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.but_front:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.but_contact2:
                Intent intent1 = new Intent(this, Contact.class);
                startActivity(intent1);
                break;
        }
    }
}
