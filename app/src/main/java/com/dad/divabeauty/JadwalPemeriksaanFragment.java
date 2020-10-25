package com.dad.divabeauty;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;
public class JadwalPemeriksaanFragment extends Fragment {
    private List<MyCalendar> calendarList= new ArrayList<>();
    private CalendarAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jadwal_pemeriksaan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new CalendarAdapter(calendarList);

        RecyclerView rv_calendar = view.getRootView().findViewById(R.id.rv_calendar);

        rv_calendar.setHasFixedSize(true);

        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);

        rv_calendar.setLayoutManager(mLayoutManager);

        rv_calendar.setItemAnimator(new DefaultItemAnimator());

        rv_calendar.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = mLayoutManager.getChildCount();
                for (int i = 0; i < totalItemCount; i++){
                    View childView = recyclerView.getChildAt(i);
                    TextView childTextView = childView.findViewById(R.id.txt_nama_hari);
                    String childTextViewText = (String) childTextView.getText();

                    if (childTextViewText.equals("Sun"))
                        childTextView.setTextColor(Color.RED);
                    else
                        childTextView.setTextColor(Color.WHITE);

                }
            }
        });

        rv_calendar.setAdapter(mAdapter);

        rv_calendar.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), rv_calendar, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                MyCalendar calendar = calendarList.get(position);
                TextView childTextView = (TextView) (view.findViewById(R.id.txt_nama_hari));

                Animation startRotateAnimation = AnimationUtils.makeInChildBottomAnimation(getActivity().getApplicationContext());
                childTextView.startAnimation(startRotateAnimation);
                MaterialCardView cardView = (MaterialCardView) view.findViewById(R.id.calendar_item_card);
                int color = ContextCompat.getColor(getActivity().getApplicationContext(), R.color.colorPrimary);
                cardView.setCardBackgroundColor(color);
            }

            @Override
            public void onLongClick(View view, int position) {
                MyCalendar calendar = calendarList.get(position);

                TextView childTextView = (TextView) (view.findViewById(R.id.txt_nama_hari));
                childTextView.setTextColor(Color.GREEN);

                Toast.makeText(getActivity().getApplicationContext(), calendar.getDate()+"/" + calendar.getDay()+"/" +calendar.getMonth()+"   selected!", Toast.LENGTH_SHORT).show();

            }
        }));

        prepareCalendarData();
    }

    private void prepareCalendarData() {
        myCalendarData m_calendar = new myCalendarData(-2);

        for ( int i=0; i <30; i++) {

            MyCalendar calendar = new MyCalendar(m_calendar.getWeekDay(), String.valueOf(m_calendar.getDay()), String.valueOf(m_calendar.getMonth()), String.valueOf(m_calendar.getYear()),i);
            m_calendar.getNextWeekDay(1);

            calendarList.add(calendar);

        }

        mAdapter.notifyDataSetChanged();
    }
}