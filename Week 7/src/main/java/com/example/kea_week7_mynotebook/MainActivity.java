package com.example.kea_week7_mynotebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String messageKey ="MESSAGE_KEY";

    private Button but_save, but_display;
    private EditText editText_field;

    private String saved_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        but_save = findViewById(R.id.button_save);
        but_save.setOnClickListener(this);
        but_display = findViewById(R.id.button_display);
        but_display.setOnClickListener(this);
        editText_field = findViewById(R.id.editText);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.button_save:
                saved_string = editText_field.getText().toString();
                break;

            case R.id.button_display:
                Intent display = new Intent(this, Display.class);
                display.putExtra(messageKey, saved_string);
                startActivity(display);
                break;


        }
    }
}
