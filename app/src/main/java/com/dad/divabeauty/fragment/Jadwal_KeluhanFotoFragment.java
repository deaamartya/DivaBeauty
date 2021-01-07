package com.dad.divabeauty.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dad.divabeauty.R;
import com.dad.divabeauty.model.Dokter;
import com.dad.divabeauty.model.Pemeriksaan;
import com.dad.divabeauty.model.SlotJam;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static com.dad.divabeauty.activity.MainActivity.PREFS_NAME;

public class Jadwal_KeluhanFotoFragment extends Fragment {
    public static String EXTRA_TANGGAL = "extra_name";
    public static Dokter EXTRA_DOKTER;
    public static SlotJam EXTRA_SLOT;
    private Integer id_pemeriksaan;
    private Integer id_pasien;
    private Integer id_dokter;
    private String tgl_periksa;
    private Integer no_antrian;
    private String keluhan_awal;
    private String foto;
    private String waktu_dibuat;
    private Boolean status;
    private String perkiraan_jam_periksa;
    private Integer lastid;
    private CollectionReference slotRef;
    private CollectionReference doktersRef;
    private CollectionReference usersRef;
    private CollectionReference pemeriksaanRef;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_keluhan_foto, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EXTRA_TANGGAL = getArguments().getString(EXTRA_TANGGAL);
        EXTRA_SLOT = getArguments().getParcelable("EXTRA_SLOT");
        EXTRA_DOKTER = getArguments().getParcelable("EXTRA_DOKTER");
        TextView tglJam = view.findViewById(R.id.jam_dan_tanggal_keluhan);
        TextView nama_dokter = view.findViewById(R.id.nama_dokter_keluhan);
        ImageView foto_dokter = view.findViewById(R.id.foto_dokter_keluhan);

        pemeriksaanRef = db.collection("pemeriksaan");
        doktersRef = db.collection("dokter");

//        Log.d("isi extra tglJam",EXTRA_SLOT.getSlot_jam() +" "+ EXTRA_TANGGAL);

        tglJam.setText(new StringBuilder(EXTRA_TANGGAL + " " + EXTRA_SLOT.getSlot_jam()));
        nama_dokter.setText(EXTRA_DOKTER.getNama());

        Glide.with(getView())
                .load(
                        getActivity().getResources().getIdentifier(EXTRA_DOKTER.getFoto(), "drawable", getActivity().getPackageName())
                )
                .into(foto_dokter);
        foto_dokter.setClipToOutline(true);

        Button daftar = view.findViewById(R.id.btn_keluhan_daftar);
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout keluhan = (TextInputLayout) v.getRootView().findViewById(R.id.input_keluhan);
                keluhan_awal = keluhan.getEditText().getText().toString();
                submitData();
            }
        });
    }

    private void submitSuccess(String waktu_dibuat, Integer no_antrian, String perkiraan_jam_periksa) {
        Jadwal_SuccessFragment successFragment = new Jadwal_SuccessFragment();

        Bundle bundle = new Bundle();
        bundle.putString("EXTRA_TIMESTAMP", waktu_dibuat);
        bundle.putInt("EXTRA_NOANTRIAN",no_antrian);
        bundle.putString("EXTRA_PERKIRAAN", perkiraan_jam_periksa);

        successFragment.setArguments(bundle);

        FragmentManager mFragmentManager = getFragmentManager();
        if (mFragmentManager != null) {
            mFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_container_pemeriksaan, successFragment, Jadwal_SuccessFragment.class.getSimpleName())
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void submitData() {
        SharedPreferences settings = getContext().getSharedPreferences(PREFS_NAME, 0);
        final Integer id_user = settings.getInt("id_user", 0);
        pemeriksaanRef.whereEqualTo("tgl_periksa", tgl_periksa).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull final Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot result = task.getResult();
                    if (result.isEmpty()) {
                        no_antrian = 1;
                    } else {
                        no_antrian = result.size() + 1;
                    }
                    pemeriksaanRef.orderBy("id_pemeriksaan", Query.Direction.DESCENDING).limit(1).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            lastid = Integer.valueOf(queryDocumentSnapshots.getDocuments().get(0).get("id_pemeriksaan").toString());
                            Pemeriksaan pemeriksaan = new Pemeriksaan();
                            pemeriksaan.setId_pemeriksaan(lastid + 1);
                            pemeriksaan.setId_pasien(id_user);
                            pemeriksaan.setId_dokter(EXTRA_DOKTER.getId_dokter());
                            pemeriksaan.setFoto("null");
                            pemeriksaan.setKeluhan_awal(keluhan_awal);
                            pemeriksaan.setNo_antrian(no_antrian);

                            Log.w("no antrian", String.valueOf(no_antrian));
                            perkiraan_jam_periksa = EXTRA_TANGGAL + " " +addClock(EXTRA_SLOT.getStartClock(),20*(no_antrian-1));
                            pemeriksaan.setPerkiraan_jam_periksa(perkiraan_jam_periksa);
                            Log.w("perkiraan",perkiraan_jam_periksa);
                            pemeriksaan.setStatus("BELUM PERIKSA");
                            pemeriksaan.setTgl_periksa(tgl_periksa);
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                            waktu_dibuat = sdf.format(timestamp)+" WIB";
                            pemeriksaan.setWaktu_dibuat(waktu_dibuat);
                            pemeriksaanRef.add(pemeriksaan);
                            submitSuccess(waktu_dibuat, no_antrian,perkiraan_jam_periksa);
                        }
                    });
                }
            }
        });
    }

    private String addClock(Integer hours,Integer add_minutes){
        String jam;
        int total_minutes = hours*60 + add_minutes;
        int hour = total_minutes/60;
        int minute = total_minutes%60;
        if (hour < 10){
            jam = "0"+hour;
        }
        else{
            jam = String.valueOf(hour);
        }
        if (minute < 10){
            jam = jam + ":0"+minute;
        }
        else{
            jam = jam + ":0"+String.valueOf(minute);
        }

        return jam;
    }
}