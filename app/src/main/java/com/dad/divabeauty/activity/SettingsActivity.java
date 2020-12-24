package com.dad.divabeauty.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.dad.divabeauty.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ImageView foto_user = (ImageView) findViewById(R.id.foto_user);
        foto_user.setClipToOutline(true);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(R.id.page_4);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;

                switch (item.getItemId()){
                    case R.id.page_1:
                        intent = new Intent(SettingsActivity.this,HomeActivity.class);
                        break;

                    case R.id.page_2:
                        intent = new Intent(SettingsActivity.this,RiwayatKonsultasiActivity.class);
                        break;

                    case R.id.page_3:
                        intent = new Intent(SettingsActivity.this, InboxPasienActivity.class);
                        break;

                    case R.id.page_4:
                        intent = new Intent(SettingsActivity.this, SettingsActivity.class);
                        break;

                    default:
                        intent = new Intent(SettingsActivity.this,HomeActivity.class);
                }

                startActivity(intent);
                return true;
            }
        });
    }
}