package com.dad.divabeauty.adapter;

import android.content.Intent;
import android.media.Image;
import android.os.TestLooperManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dad.divabeauty.R;
import com.dad.divabeauty.activity.DetailRiwayatActivity;
import com.dad.divabeauty.model.Inbox;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class ListRiwayatKonsultasiAdapter extends RecyclerView.Adapter<ListRiwayatKonsultasiAdapter.ListViewHolder> {
    private final ArrayList<Inbox> listRiwayat;

    public ListRiwayatKonsultasiAdapter(ArrayList<Inbox> list){
        this.listRiwayat = list;
    }

    @NonNull
    @Override
    public ListRiwayatKonsultasiAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_riwayat, parent, false);
        return new ListRiwayatKonsultasiAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListRiwayatKonsultasiAdapter.ListViewHolder holder, int position) {
        holder.nama.setText("Nama Dokter");
        holder.tanggal.setText("Pada : 20/12/2020 22:49");
        Glide.with(holder.itemView.getContext())
                .load(
                        holder.itemView.getResources()
                                .getIdentifier("foto_dokter", "drawable", holder.itemView.getContext().getPackageName())
                )
                .into(holder.foto);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailRiwayatActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listRiwayat.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView nama;
        TextView tanggal;
        ImageView foto;
        MaterialCardView card;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.nama_dokter_riwayat);
            tanggal = itemView.findViewById(R.id.jam_tanggal_riwayat);
            foto = itemView.findViewById(R.id.foto_dokter_riwayat);
            foto.setClipToOutline(true);
            card = itemView.findViewById(R.id.card_riwayat);
        }
    }
}
