package com.dad.divabeauty;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.inbox.setText(text);
    }

    @Override
    public int getItemCount() {
        return listInbox.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView inbox;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            inbox = itemView.findViewById(R.id.txt_inbox);
        }
    }
}
