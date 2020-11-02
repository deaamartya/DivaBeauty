package com.dad.divabeauty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private final ArrayList<Dokter> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyler_view_dokter);
        recyclerView.setHasFixedSize(true);

        list.addAll(getListDokter());
        showRecyclerList();

        CardView card_pemeriksaan = findViewById(R.id.card_pemeriksaan);
        card_pemeriksaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PemeriksaanActivity.class);
                startActivity(intent);
            }
        });

        CardView card_riwayat = findViewById(R.id.card_riwayat);
        card_riwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, RiwayatKonsultasiActivity.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(R.id.page_1);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;

                switch (item.getItemId()){
                    case R.id.page_1:
                        intent = new Intent(HomeActivity.this,HomeActivity.class);
                        break;

                    case R.id.page_2:
                        intent = new Intent(HomeActivity.this,RiwayatKonsultasiActivity.class);
                        break;

                    case R.id.page_3:
                        intent = new Intent(HomeActivity.this,InboxPasienActivity.class);
                        break;

                    case R.id.page_4:
                        intent = new Intent(HomeActivity.this,SettingsActivity.class);
                        break;

                    default:
                        intent = new Intent(HomeActivity.this,HomeActivity.class);
                }

                startActivity(intent);
                return true;
            }
        });

    }

    private void showRecyclerList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        ListDokterAdapter listDokterAdapter = new ListDokterAdapter(list);
        recyclerView.setAdapter(listDokterAdapter);
    }

    private ArrayList<Dokter> getListDokter() {
        String[] dataNama = getResources().getStringArray(R.array.data_dokter);

        ArrayList<Dokter> listDokter = new ArrayList<>();

        for (String s : dataNama) {
            Dokter dokter = new Dokter();
            dokter.setNama(s);
            dokter.setFoto("foto_dokter");

            listDokter.add(dokter);
        }

        return  listDokter;
    }
}