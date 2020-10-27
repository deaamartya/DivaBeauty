package com.dad.divabeauty;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailInboxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_inbox);

        String namaDokter = getIntent().getExtras().getString("namaDokter");
        String fotoDokter = getIntent().getExtras().getString("fotoDokter");
        String catatan = getIntent().getExtras().getString("catatan");
        String tglpemeriksaan = getIntent().getExtras().getString("tglpemeriksaan");

        TextView nama = (TextView) findViewById(R.id.nama_dokter_detail_inbox);
        nama.setText(namaDokter);

        TextView catatanDokter = (TextView) findViewById(R.id.catatan_detail_inbox);
        catatanDokter.setText(catatan);

        TextView tgl = (TextView) findViewById(R.id.waktu_pesan);
        tgl.setText(tglpemeriksaan);

        ImageView foto = (ImageView) findViewById(R.id.foto_dokter_detail_inbox);
        Glide.with(this)
                .load(
                        getResources().getIdentifier(fotoDokter, "drawable", getPackageName())
                )
                .into(foto);
        foto.setClipToOutline(true);
        catatanDokter.setEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Detail Kotak Masuk");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}