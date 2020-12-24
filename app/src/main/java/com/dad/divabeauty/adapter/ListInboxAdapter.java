package com.dad.divabeauty.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dad.divabeauty.R;
import com.dad.divabeauty.model.Inbox;

import java.util.ArrayList;

public class ListInboxAdapter extends RecyclerView.Adapter<ListInboxAdapter.ListViewHolder> {
    private final ArrayList<Inbox> listInbox;

    public ListInboxAdapter(ArrayList<Inbox> list){
        this.listInbox = list;
    }

    @NonNull
    @Override
    public ListInboxAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_inbox, parent, false);
        return new ListInboxAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListInboxAdapter.ListViewHolder holder, int position) {
        Inbox inbox = listInbox.get(position);
        String text = inbox.getNamaDokter()+" mengirim catatan untuk pemeriksaan "+inbox.getTglpemeriksaan();
        holder.pesan.setText(text);
        holder.nama_dokter.setText(inbox.getNamaDokter());
    }

    @Override
    public int getItemCount() {
        return listInbox.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView pesan;
        ImageView foto_dokter;
        TextView nama_dokter;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            pesan = itemView.findViewById(R.id.isi_pesan_inbox);
            foto_dokter = itemView.findViewById(R.id.foto_dokter_inbox);
            foto_dokter.setClipToOutline(true);
            nama_dokter = itemView.findViewById(R.id.nama_dokter_inbox);
        }
    }
}
