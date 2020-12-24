package com.dad.divabeauty.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dad.divabeauty.R;
import com.dad.divabeauty.model.Dokter;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class ListDokterJadwalAdapter extends RecyclerView.Adapter<ListDokterJadwalAdapter.ListViewHolder> {
    private final ArrayList<Dokter> listDokter;

    public ListDokterJadwalAdapter(ArrayList<Dokter> list){
        this.listDokter = list;
    }

    @NonNull
    @Override
    public ListDokterJadwalAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_dokter_jadwal, parent, false);
        return new ListDokterJadwalAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListDokterJadwalAdapter.ListViewHolder holder, final int position) {
        Dokter dokter = listDokter.get(position);

        Glide.with(holder.itemView.getContext())
                .load(
                        holder.itemView.getResources().getIdentifier(dokter.getFoto(), "drawable", holder.itemView.getContext().getPackageName())
                )
                .into(holder.imgDokter);

        holder.namaDokter.setText(dokter.getNama());
        holder.cardDokter.setCheckable(true);
    }

    @Override
    public int getItemCount() {
        return listDokter.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgDokter;
        TextView namaDokter;
        MaterialCardView cardDokter;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDokter = itemView.findViewById(R.id.foto_dokter_jadwal);
            namaDokter = itemView.findViewById(R.id.nama_dokter_jadwal);
            cardDokter = itemView.findViewById(R.id.card_jadwal_dokter);

            imgDokter.setClipToOutline(true);
        }
    }
}