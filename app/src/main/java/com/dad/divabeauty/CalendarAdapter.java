package com.dad.divabeauty;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.MyViewHolder> {

    private final List<MyCalendar> mCalendar;
    private int recyclecount=0;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView tb_day;
        private final TextView tb_date;
        private final TextView tb_month;


        public MyViewHolder(View view) {
            super(view);
            tb_day = view.findViewById(R.id.txt_nama_hari);
            tb_date = view.findViewById(R.id.txt_tanggal);
            tb_month = view.findViewById(R.id.txt_nama_bulan);
        }

    }


    public CalendarAdapter(List<MyCalendar> mCalendar) {
        this.mCalendar = mCalendar;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.calendar_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final MyCalendar calendar = mCalendar.get(position);

        holder.tb_day.setText(calendar.getDay());

        holder.tb_date.setText(calendar.getDate());

        holder.tb_month.setText(calendar.getMonth());

    }

    @Override
    public int getItemCount() {
        return mCalendar.size();
    }

    @Override
    public void onViewRecycled (MyViewHolder holder){

        recyclecount++;

    }
}