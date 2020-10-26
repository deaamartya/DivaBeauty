package com.dad.divabeauty;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ListSlotJamAdapter extends RecyclerView.Adapter<ListSlotJamAdapter.ListViewHolder> {
    private ArrayList<SlotJam> listSlotJam;

    public ListSlotJamAdapter(ArrayList<SlotJam> list){
        this.listSlotJam = list;
    }

    @NonNull
    @Override
    public ListSlotJamAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slotjam_chip, parent, false);
        return new ListSlotJamAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListSlotJamAdapter.ListViewHolder holder, int position) {
        SlotJam slotJam = listSlotJam.get(position);
        holder.jam.setText(slotJam.getSlot_jam());
    }

    @Override
    public int getItemCount() {
        return listSlotJam.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView jam;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            jam = itemView.findViewById(R.id.txt_slotjam);
        }
    }
}


