package com.dad.divabeauty.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.dad.divabeauty.R;
import com.dad.divabeauty.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.dad.divabeauty.activity.MainActivity.PREFS_NAME;

public class SettingsActivity extends AppCompatActivity {
    private CollectionReference usersRef;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private SharedPreferences settings;
    private ImageView foto_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        foto_user = (ImageView) findViewById(R.id.foto_user);
        foto_user.setClipToOutline(true);
        settings = getSharedPreferences(PREFS_NAME, 0);

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
        final TextView nama_user = findViewById(R.id.nama_user);
        final TextView tgl_lahir = findViewById(R.id.tanggal_lahir_user);
        final TextView no_telp = findViewById(R.id.no_telp_user);
        final TextView email = findViewById(R.id.email_user);
        final TextView nik_user = findViewById(R.id.nik_user);
        final TextView nokk_user = findViewById(R.id.nokk_user);
        final ImageView icon_jenis_kelamin = findViewById(R.id.jenis_kelamin_user);
        final TextView created_at = findViewById(R.id.created_at_user);
        final TextView umur = findViewById(R.id.umur_user);

        usersRef = db.collection("user");
        Integer id_user = settings.getInt("id_user", 0);
        usersRef.whereEqualTo("id_user", id_user).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.getDocuments().isEmpty()) {
                            DocumentSnapshot user = queryDocumentSnapshots.getDocuments().get(0);
                            email.setText(user.get("email").toString());
                            nama_user.setText(user.get("nama").toString());
                            tgl_lahir.setText(user.get("tgl_lahir").toString());
                            boolean jenis_kelamin = Boolean.getBoolean(user.get("jenis_kelamin").toString());
                            if(jenis_kelamin)
                            {
                                icon_jenis_kelamin.setImageResource(R.drawable.genderman);
                                foto_user.setImageResource(R.drawable.man);
                            }
                            else
                            {
                                icon_jenis_kelamin.setImageResource(R.drawable.genderwoman);
                                foto_user.setImageResource(R.drawable.woman);
                            }
                            no_telp.setText(user.get("no_telp").toString());
                            String text = (String) created_at.getText();
                            String created_at_text = text.concat(user.get("created_at").toString());
                            created_at.setText(created_at_text);
                            Date date1 = null;
                            try {
                                date1 = new SimpleDateFormat("MMM dd,yyyy", Locale.ENGLISH).parse(user.get("tgl_lahir").toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Calendar dob = Calendar.getInstance();
                            Calendar today = Calendar.getInstance();

                            dob.setTime(date1);

                            int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

                            if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
                                age--;
                            }

                            Log.d("tgl_lahir", String.valueOf(dob.get(Calendar.DATE)));

                            String ageS = String.valueOf(age);
                            umur.setText(ageS);
                        }
                    }
                });
        CollectionReference pasienRef = db.collection("pasien");
        pasienRef.whereEqualTo("id_user",id_user).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.getDocuments().isEmpty()) {
                            DocumentSnapshot pasien = queryDocumentSnapshots.getDocuments().get(0);
                            nik_user.setText(pasien.get("no_ktp_pasien").toString());
                            nokk_user.setText(pasien.get("no_kk_pasien").toString());
                        }
                    }
                });
    }
}