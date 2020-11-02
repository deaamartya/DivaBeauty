package com.dad.divabeauty;

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

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class Jadwal_SuccessFragment extends Fragment {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH.mm");
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
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        TextView txt_timestamp = view.findViewById(R.id.timestamp_pembuatan);
        String tglwaktuwib = "Antrian ini dibuat pada "+sdf.format(timestamp) + " WIB";
        txt_timestamp.setText(tglwaktuwib);
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