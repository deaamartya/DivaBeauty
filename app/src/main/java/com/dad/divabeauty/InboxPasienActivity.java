package com.dad.divabeauty;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class InboxPasienActivity extends AppCompatActivity {
    private RecyclerView rv_inbox;
    private final ArrayList<Inbox> listInbox = new ArrayList<>();
    private String namaDokter,fotoDokter,tglpemeriksaan,catatan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox_pasien);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(R.id.page_3);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;

                switch (item.getItemId()){
                    case R.id.page_1:
                        intent = new Intent(InboxPasienActivity.this,HomeActivity.class);
                        break;

                    case R.id.page_2:
                        intent = new Intent(InboxPasienActivity.this,RiwayatKonsultasiActivity.class);
                        break;

                    case R.id.page_3:
                        intent = new Intent(InboxPasienActivity.this,InboxPasienActivity.class);
                        break;

                    case R.id.page_4:
                        intent = new Intent(InboxPasienActivity.this,SettingsActivity.class);
                        break;

                    default:
                        intent = new Intent(InboxPasienActivity.this,HomeActivity.class);
                }

                startActivity(intent);
                return true;
            }
        });

        rv_inbox = findViewById(R.id.rv_inbox);
        rv_inbox.setHasFixedSize(true);

        listInbox.addAll(getListInbox());
        showRecyclerList();

        rv_inbox.addOnItemTouchListener(new RecyclerTouchListener(this, rv_inbox, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Inbox inbox = listInbox.get(position);
                namaDokter = inbox.getNamaDokter();
                fotoDokter = "foto_dokter";
                catatan = inbox.getPesan();
                tglpemeriksaan = inbox.getTglpemeriksaan();

                Intent intent = new Intent(InboxPasienActivity.this,DetailInboxActivity.class);
                intent.putExtra("namaDokter",namaDokter);
                intent.putExtra("fotoDokter",fotoDokter);
                intent.putExtra("catatan",catatan);
                intent.putExtra("tglpemeriksaan",tglpemeriksaan);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

                Inbox inbox = listInbox.get(position);
                namaDokter = inbox.getNamaDokter();
                fotoDokter = "foto_dokter";
                catatan = inbox.getPesan();
                tglpemeriksaan = inbox.getTglpemeriksaan();

                Intent intent = new Intent(InboxPasienActivity.this,DetailInboxActivity.class);
                intent.putExtra("namaDokter",namaDokter);
                intent.putExtra("fotoDokter",fotoDokter);
                intent.putExtra("catatan",catatan);
                intent.putExtra("tglpemeriksaan",tglpemeriksaan);
                startActivity(intent);

            }
        }));
    }

    private void showRecyclerList() {
        rv_inbox.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        ListInboxAdapter listInboxAdapter = new ListInboxAdapter(listInbox);
        rv_inbox.setAdapter(listInboxAdapter);
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