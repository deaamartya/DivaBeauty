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

import java.util.ArrayList;

public class ListDokterAdapter extends RecyclerView.Adapter<ListDokterAdapter.ListViewHolder> {
    private final ArrayList<Dokter> listDokter;

    public ListDokterAdapter(ArrayList<Dokter> list){
        this.listDokter = list;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_dokter, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Dokter dokter = listDokter.get(position);

        Glide.with(holder.itemView.getContext())
                .load(
                        holder.itemView.getResources()
                        .getIdentifier(dokter.getFoto(), "drawable", holder.itemView.getContext().getPackageName())
                )
                .into(holder.imgDokter);

        holder.namaDokter.setText(dokter.getNama());
    }

    @Override
    public int getItemCount() {
        return listDokter.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgDokter;
        TextView namaDokter;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDokter = itemView.findViewById(R.id.foto_dokter);
            namaDokter = itemView.findViewById(R.id.nama_dokter);

            imgDokter.setClipToOutline(true);
        }
    }
}
