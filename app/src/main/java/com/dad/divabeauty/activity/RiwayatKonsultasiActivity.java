package com.dad.divabeauty.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.dad.divabeauty.R;
import com.dad.divabeauty.RecyclerTouchListener;
import com.dad.divabeauty.adapter.ListInboxAdapter;
import com.dad.divabeauty.adapter.ListRiwayatKonsultasiAdapter;
import com.dad.divabeauty.model.Inbox;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class RiwayatKonsultasiActivity extends AppCompatActivity {
    private RecyclerView rv_riwayat;
    private final ArrayList<Inbox> listInbox = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_konsultasi);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(R.id.page_2);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;

                switch (item.getItemId()){
                    case R.id.page_1:
                        intent = new Intent(RiwayatKonsultasiActivity.this,HomeActivity.class);
                        break;

                    case R.id.page_2:
                        intent = new Intent(RiwayatKonsultasiActivity.this,RiwayatKonsultasiActivity.class);
                        break;

                    case R.id.page_3:
                        intent = new Intent(RiwayatKonsultasiActivity.this, InboxPasienActivity.class);
                        break;

                    case R.id.page_4:
                        intent = new Intent(RiwayatKonsultasiActivity.this, SettingsActivity.class);
                        break;

                    default:
                        intent = new Intent(RiwayatKonsultasiActivity.this,HomeActivity.class);
                }

                startActivity(intent);
                return true;
            }
        });

        rv_riwayat = findViewById(R.id.recyler_view_riwayat);
        rv_riwayat.setHasFixedSize(true);

        listInbox.addAll(getListInbox());
        showRecyclerList();
    }

    private void showRecyclerList() {
        rv_riwayat.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        ListRiwayatKonsultasiAdapter listRiwayatKonsultasiAdapter = new ListRiwayatKonsultasiAdapter(listInbox);
        rv_riwayat.setAdapter(listRiwayatKonsultasiAdapter);
    }

    private ArrayList<Inbox> getListInbox() {
        String[] dataNamaDokter = getResources().getStringArray(R.array.data_dokter);

        ArrayList<Inbox> listInbox = new ArrayList<>();

        for (String s : dataNamaDokter) {
            Inbox inbox = new Inbox();
            inbox.setNamaDokter(s);
            inbox.setTglpemeriksaan("26/10/2020 21.59 WIB");
            inbox.setPesan("Ini adalah contoh catatan yang dikirimkan oleh dokter pada pasien");

            listInbox.add(inbox);
        }

        return  listInbox;
    }
}