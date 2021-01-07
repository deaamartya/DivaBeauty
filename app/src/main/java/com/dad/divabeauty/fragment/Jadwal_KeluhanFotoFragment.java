package com.dad.divabeauty.fragment;

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
import com.dad.divabeauty.model.SlotJam;

public class Jadwal_KeluhanFotoFragment extends Fragment {
    public static String EXTRA_TANGGAL = "extra_name";
    public static Dokter EXTRA_DOKTER;
    public static SlotJam EXTRA_SLOT;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_keluhan_foto, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert getArguments() != null;
        EXTRA_TANGGAL = getArguments().getString("EXTRA_TANGGAL");
        EXTRA_SLOT = getArguments().getParcelable("EXTRA_SLOT");
        EXTRA_DOKTER = getArguments().getParcelable("EXTRA_DOKTER");
        TextView tglJam = view.findViewById(R.id.jam_dan_tanggal_keluhan);
        TextView nama_dokter = view.findViewById(R.id.nama_dokter_keluhan);
        ImageView foto_dokter = view.findViewById(R.id.foto_dokter_keluhan);

        Log.d("isi extra tglJam",EXTRA_SLOT.getSlot_jam() +" "+ EXTRA_TANGGAL);

        tglJam.setText(new StringBuilder(EXTRA_TANGGAL+" "+EXTRA_SLOT.getId_slot_jam()));
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
                Jadwal_SuccessFragment successFragment = new Jadwal_SuccessFragment();

                FragmentManager mFragmentManager = getFragmentManager();
                if (mFragmentManager != null) {
                    mFragmentManager
                            .beginTransaction()
                            .replace(R.id.frame_container_pemeriksaan, successFragment, Jadwal_SuccessFragment.class.getSimpleName())
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
    }
}