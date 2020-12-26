package com.dad.divabeauty.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dad.divabeauty.R;

public class MainActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        /* Cek apakah pertama kali dijalankan */
        boolean firstRun = settings.getBoolean("firstRun", true); // Is it first run? If not specified, use "true"

        if (firstRun) {
            SharedPreferences.Editor editor = settings.edit(); // Open the editor for our settings
            editor.putBoolean("firstRun", false); // It is no longer the first run
            editor.commit(); // Save all changed settings
        } else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        /* End of Cek */

        /* Fungsi pindah activity */
        Button get_started = findViewById(R.id.btn_start);
        get_started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        /* End of Fungsi pindah activity */
    }
}