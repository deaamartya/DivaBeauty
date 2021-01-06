package com.dad.divabeauty.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dad.divabeauty.adapter.ListDokterAdapter;
import com.dad.divabeauty.R;
import com.dad.divabeauty.model.Dokter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static com.dad.divabeauty.activity.MainActivity.PREFS_NAME;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private final ArrayList<Dokter> list = new ArrayList<>();
    private TextView nama_user;
    private CollectionReference doktersRef;
    private CollectionReference usersRef;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private SharedPreferences settings;
    private ArrayList<Dokter> listDokter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        settings = getSharedPreferences(PREFS_NAME, 0);

        recyclerView = findViewById(R.id.recyler_view_dokter);
        recyclerView.setHasFixedSize(true);

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
                        intent = new Intent(HomeActivity.this, InboxPasienActivity.class);
                        break;

                    case R.id.page_4:
                        intent = new Intent(HomeActivity.this, SettingsActivity.class);
                        break;

                    default:
                        intent = new Intent(HomeActivity.this,HomeActivity.class);
                }

                startActivity(intent);
                return true;
            }
        });
        usersRef = db.collection("user");
        doktersRef = db.collection("dokter");
//        list.addAll(getListDokter());
//        showRecyclerList();
        nama_user = findViewById(R.id.txt_home_nama);
        Integer id_user = settings.getInt("id_user", 0);
        if(id_user != 0){
            usersRef.whereEqualTo("id_user", id_user).get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if(!queryDocumentSnapshots.getDocuments().isEmpty()) {
                                // simpan sebagai obyek user
                                DocumentSnapshot user = queryDocumentSnapshots.getDocuments().get(0);
                                String fullname = user.get("nama").toString();
                                String name = fullname;
                                if(fullname.contains(" ")){
                                    name = fullname.substring(0, fullname.indexOf(" "));
                                }
                                nama_user.setText(name);
                            }
                            else{
                                nama_user.setText("");
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    nama_user.setText("");
                }
            });
        }

    }

    private void showRecyclerList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        ListDokterAdapter listDokterAdapter = new ListDokterAdapter(list);
        recyclerView.setAdapter(listDokterAdapter);
    }

    private ArrayList<Dokter> getListDokter() {
        listDokter = new ArrayList<>();
        doktersRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot data : queryDocumentSnapshots){
                    final Dokter dokter = new Dokter();
                    dokter.setId_dokter(Integer.valueOf(data.get("id_dokter").toString()));
                    dokter.setId_user(Integer.valueOf(data.get("id_user").toString()));
                    usersRef.whereEqualTo("id_user",dokter.getId_user()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            dokter.setNama(queryDocumentSnapshots.getDocuments().get(0).get("nama").toString());
                        }
                    });
                    dokter.setFoto("foto_dokter");
                    listDokter.add(dokter);
                }
            }
        });
        return listDokter;
    }
}