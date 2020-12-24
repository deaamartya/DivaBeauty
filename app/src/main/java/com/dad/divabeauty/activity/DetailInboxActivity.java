package com.dad.divabeauty.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dad.divabeauty.R;

public class DetailInboxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_inbox);

        String namaDokter = getIntent().getExtras().getString("namaDokter");
        String fotoDokter = getIntent().getExtras().getString("fotoDokter");
        String catatan = getIntent().getExtras().getString("catatan");
        String tglpemeriksaan = getIntent().getExtras().getString("tglpemeriksaan");

        TextView nama = findViewById(R.id.nama_dokter_detail_inbox);
        nama.setText(namaDokter);

        TextView catatanDokter = findViewById(R.id.catatan_detail_inbox);
        catatanDokter.setText(catatan);

        ImageView foto = findViewById(R.id.foto_dokter_detail_inbox);
        Glide.with(this)
                .load(
                        getResources().getIdentifier(fotoDokter, "drawable", getPackageName())
                )
                .into(foto);
        foto.setClipToOutline(true);
        catatanDokter.setEnabled(false);
    }
}