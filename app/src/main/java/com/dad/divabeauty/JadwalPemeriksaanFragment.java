package com.dad.divabeauty;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;

import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
public class JadwalPemeriksaanFragment extends Fragment {
    private final List<MyCalendar> calendarList= new ArrayList<>();
    private CalendarAdapter mAdapter;
    private final ArrayList<Dokter> list = new ArrayList<>();
    private RecyclerView rv_dokter;
    private RecyclerView rv_calendar;
    private RecyclerView rv_slotjam;
    private final ArrayList<SlotJam> listSlot = new ArrayList<>();
    private String namaDokter,slotjam,tgl,fotoDokter;

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

        rv_calendar = view.getRootView().findViewById(R.id.rv_calendar);

        rv_calendar.setHasFixedSize(true);

        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);

        rv_calendar.setLayoutManager(mLayoutManager);

        rv_calendar.setItemAnimator(new DefaultItemAnimator());

        rv_calendar.setAdapter(mAdapter);

        rv_calendar.addOnItemTouchListener(new RecyclerTouchListener(getContext(), rv_calendar, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
//                for (int i = 0; i < calendarList.size()-1; i++){
//                    MaterialCardView childView = (MaterialCardView) rv_calendar.getChildAt(i);
//                    childView.setChecked(false);
//                    childView.setCardBackgroundColor(Color.TRANSPARENT);
//                }
                MaterialCardView cardView;
                if(position < calendarList.size())
                    cardView = (MaterialCardView) rv_calendar.getChildAt(position);
                else {
                    cardView = (MaterialCardView) rv_calendar.getChildAt(position-1);
                }
//                int color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
//                cardView.setCardBackgroundColor(color);
                cardView.setChecked(true);
                MyCalendar calendar = calendarList.get(position);
                tgl = calendar.getDate() + " " + calendar.getMonth() + " " + calendar.getYear();
            }

            @Override
            public void onLongClick(View view, int position) {
                MyCalendar calendar = calendarList.get(position);
                tgl = calendar.getDate() + " " + calendar.getMonth() + " " + calendar.getYear();
            }
        }));

        prepareCalendarData();

        rv_dokter = view.findViewById(R.id.rv_dokter_jadwal);
        rv_dokter.setHasFixedSize(true);

        list.addAll(getListDokter());
        showRecyclerList();

        rv_dokter.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), rv_dokter, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Dokter dokter = list.get(position);
                for (int i = 0; i < list.size(); i++){
                    MaterialCardView childView = (MaterialCardView) rv_dokter.getChildAt(i);
                    childView.setChecked(false);
                }
                namaDokter = dokter.getNama();
                fotoDokter = dokter.getFoto();

                MaterialCardView childView = (MaterialCardView) rv_dokter.getChildAt(position);
                childView.setChecked(true);
            }

            @Override
            public void onLongClick(View view, int position) {
                for (int i = 0; i < list.size(); i++){
                    MaterialCardView childView = (MaterialCardView) rv_dokter.getChildAt(i);
                    childView.setChecked(false);
                }
                Dokter dokter = list.get(position);
                namaDokter = dokter.getNama();
                fotoDokter = dokter.getFoto();

                MaterialCardView childView = (MaterialCardView) rv_dokter.getChildAt(position);
                childView.setChecked(true);
            }
        }));

        rv_slotjam = view.findViewById(R.id.rv_slotjam);
        rv_slotjam.setHasFixedSize(true);

        listSlot.addAll(getListSlot());
        rv_slotjam.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        final ListSlotJamAdapter listSlotJamAdapter = new ListSlotJamAdapter(listSlot);
        rv_slotjam.setAdapter(listSlotJamAdapter);

        rv_slotjam.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), rv_slotjam, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                SlotJam slotJam = listSlot.get(position);
                slotjam = slotJam.getSlot_jam();
            }

            @Override
            public void onLongClick(View view, int position) {
                SlotJam slotJam = listSlot.get(position);
                slotjam = slotJam.getSlot_jam();
            }
        }));

        Button pilih = view.findViewById(R.id.btn_pilih_jadwal);
        pilih.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Jadwal_KeluhanFotoFragment keluhanFotoFragment = new Jadwal_KeluhanFotoFragment();

                Bundle bundle = new Bundle();
                bundle.putString(Jadwal_KeluhanFotoFragment.EXTRA_NAME, namaDokter);
                bundle.putString(Jadwal_KeluhanFotoFragment.EXTRA_FOTO, fotoDokter);
                bundle.putString(Jadwal_KeluhanFotoFragment.EXTRA_JAM, slotjam);
                bundle.putString(Jadwal_KeluhanFotoFragment.EXTRA_TANGGAL, tgl);

                keluhanFotoFragment.setArguments(bundle);

                FragmentManager mFragmentManager = getFragmentManager();
                if (mFragmentManager != null) {
                    mFragmentManager
                            .beginTransaction()
                            .replace(R.id.frame_container_pemeriksaan, keluhanFotoFragment, Jadwal_KeluhanFotoFragment.class.getSimpleName())
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

    }

    private ArrayList<SlotJam> getListSlot() {
        String[] dataSlot = getResources().getStringArray(R.array.data_slot_jam);

        ArrayList<SlotJam> listSlot = new ArrayList<>();

        for (String s : dataSlot) {
            SlotJam slotJam = new SlotJam();
            slotJam.setSlot_jam(s);

            listSlot.add(slotJam);
        }

        return  listSlot;
    }

    private void showRecyclerList() {
        rv_dokter.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        ListDokterJadwalAdapter listDokterAdapter = new ListDokterJadwalAdapter(list);
        rv_dokter.setAdapter(listDokterAdapter);
    }

    private void prepareCalendarData() {
        myCalendarData m_calendar = new myCalendarData(0);

        for ( int i=0; i <7; i++) {

            MyCalendar calendar = new MyCalendar(m_calendar.getWeekDay(), String.valueOf(m_calendar.getDay()), String.valueOf(m_calendar.getMonth()), String.valueOf(m_calendar.getYear()),i);
            m_calendar.getNextWeekDay(1);

            calendarList.add(calendar);

        }

        mAdapter.notifyDataSetChanged();
    }

    private ArrayList<Dokter> getListDokter() {
        String[] dataNama = getResources().getStringArray(R.array.data_dokter_tersedia);

        ArrayList<Dokter> listDokter = new ArrayList<>();

        for (String s : dataNama) {
            Dokter dokter = new Dokter();
            dokter.setNama(s);
            dokter.setFoto("foto_dokter_2");

            listDokter.add(dokter);
        }

        return  listDokter;
    }
}