package com.dad.divabeauty.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

import com.dad.divabeauty.R;
import com.dad.divabeauty.fragment.JadwalPemeriksaanFragment;

public class PemeriksaanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemeriksaan);

        FragmentManager mFragmentManager = getSupportFragmentManager();
        JadwalPemeriksaanFragment mHomeFragment = new JadwalPemeriksaanFragment();
        Fragment fragment = mFragmentManager.findFragmentByTag(JadwalPemeriksaanFragment.class.getSimpleName());

        if (!(fragment instanceof JadwalPemeriksaanFragment)) {
            Log.d("MyFlexibleFragment", "Fragment Name :" + JadwalPemeriksaanFragment.class.getSimpleName());
            mFragmentManager
                    .beginTransaction()
                    .add(R.id.frame_container_pemeriksaan, mHomeFragment, JadwalPemeriksaanFragment.class.getSimpleName())
                    .commit();
        }

    }
}