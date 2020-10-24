package com.dad.divabeauty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Collection;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Dokter> list = new ArrayList<>();

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

            }
        });

        CardView card_riwayat = findViewById(R.id.card_riwayat);
        card_riwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, RiwayatKonsultasi.class);
                startActivity(intent);
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
            dokter.setFoto("R.drawable.foto_dokter");

            listDokter.add(dokter);
        }

        return  listDokter;
    }
}