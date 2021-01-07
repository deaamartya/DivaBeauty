package com.dad.divabeauty.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dad.divabeauty.R;
import com.dad.divabeauty.activity.HomeActivity;
import com.dad.divabeauty.model.Dokter;
import com.dad.divabeauty.model.SlotJam;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class Jadwal_SuccessFragment extends Fragment {
    public static Integer id_pemeriksaan;
    public static String waktu_dibuat;
    public static Integer no_antrian;
    public static String perkiraan_jam_periksa;

    public Jadwal_SuccessFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jadwal__success, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        waktu_dibuat = getArguments().getString("EXTRA_TIMESTAMP");
        no_antrian = getArguments().getInt("EXTRA_NOANTRIAN");
        perkiraan_jam_periksa = getArguments().getString("EXTRA_PERKIRAAN");

        TextView txt_timestamp = view.findViewById(R.id.timestamp_pembuatan);
        txt_timestamp.setText(txt_timestamp.getText() + waktu_dibuat);

        TextView txt_antrian = view.findViewById(R.id.nomor_antrian_success);
        txt_antrian.setText(String.valueOf(no_antrian));

        TextView txt_perkiraan = view.findViewById(R.id.perkiraan_jam_success);
        txt_perkiraan.setText(perkiraan_jam_periksa);

        Button dashboard = view.findViewById(R.id.btn_success_back_to_home);
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}