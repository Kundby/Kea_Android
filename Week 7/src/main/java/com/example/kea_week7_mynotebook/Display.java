package com.example.kea_week7_mynotebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Display extends AppCompatActivity {

    private TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        display = findViewById(R.id.display_textView);

        Intent intent = getIntent();
        String displayText = intent.getExtras().getString(MainActivity.messageKey);
        display.setText(displayText);

        if (display.getText().toString().isEmpty()){
            Toast.makeText(this, "No Text Was Sent From Previous Activity", Toast.LENGTH_LONG).show();
        }

    }


}
