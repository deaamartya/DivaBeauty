package com.dad.divabeauty.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.dad.divabeauty.adapter.ListDokterJadwalAdapter;
import com.dad.divabeauty.adapter.ListSlotJamAdapter;
import com.dad.divabeauty.model.MyCalendar;
import com.dad.divabeauty.R;
import com.dad.divabeauty.RecyclerTouchListener;
import com.dad.divabeauty.model.SlotJam;
import com.dad.divabeauty.adapter.CalendarAdapter;
import com.dad.divabeauty.model.Dokter;
import com.dad.divabeauty.adapter.myCalendarData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class JadwalPemeriksaanFragment extends Fragment {
    private final List<MyCalendar> calendarList= new ArrayList<>();
    private CalendarAdapter mAdapter;
    private ArrayList<Dokter> list = new ArrayList<>();
    private RecyclerView rv_dokter;
    private RecyclerView rv_calendar;
    private RecyclerView rv_slotjam;
    private ArrayList<SlotJam> listSlot = new ArrayList<>();
    private String tgl;
    private SlotJam slotjam;
    private Dokter dokter;
    private CollectionReference slotRef;
    private CollectionReference doktersRef;
    private CollectionReference usersRef;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jadwal_pemeriksaan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        slotRef = db.collection("slot");
        usersRef = db.collection("user");
        doktersRef = db.collection("dokter");

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

//        getListDokter();
//        rv_dokter.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
//        ListDokterJadwalAdapter listDokterAdapter = new ListDokterJadwalAdapter(list);
//        rv_dokter.setAdapter(listDokterAdapter);

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

        getListSlot();
        Log.d("isi list slot",listSlot.toString());

        rv_slotjam.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), rv_slotjam, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                slotjam= listSlot.get(position);
                view.setSelected(true);

            }

            @Override
            public void onLongClick(View view, int position) {
                slotjam = listSlot.get(position);
            }
        }));

        Button pilih = view.findViewById(R.id.btn_pilih_jadwal);
        pilih.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Jadwal_KeluhanFotoFragment keluhanFotoFragment = new Jadwal_KeluhanFotoFragment();

                Bundle bundle = new Bundle();
                bundle.putParcelable(Jadwal_KeluhanFotoFragment.EXTRA_DOKTER, dokter);
                bundle.putParcelable(Jadwal_KeluhanFotoFragment.EXTRA_JAM, slotjam);
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

    private void getListSlot() {
        slotRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot data : queryDocumentSnapshots){
                    final SlotJam slotJam = new SlotJam();
                    slotJam.setSlot_jam(data.get("waktu").toString());
                    listSlot.add(slotJam);
                    Log.d("slotJam","Memasukkan slotjam ke listslot"+slotJam.getSlot_jam());
                }
                rv_slotjam.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                ListSlotJamAdapter listSlotJamAdapter = new ListSlotJamAdapter(listSlot);
                rv_slotjam.setAdapter(listSlotJamAdapter);
            }
        });
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

//    private ArrayList<Dokter> getListDokter() {
//        doktersRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                for(QueryDocumentSnapshot data : queryDocumentSnapshots){
//                    final Dokter dokter = new Dokter();
//                    dokter.setId_dokter(Integer.valueOf(data.get("id_dokter").toString()));
//                    dokter.setId_user(Integer.valueOf(data.get("id_user").toString()));
//                    usersRef.whereEqualTo("id_user",dokter.getId_user()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                        @Override
//                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                            dokter.setNama(queryDocumentSnapshots.getDocuments().get(0).get("nama").toString());
//                        }
//                    });
//                    dokter.setFoto("foto_dokter");
//                    list.add(dokter);
//                }
//            }
//        });
//        return list;
//    }

    private ArrayList<Dokter> updateListDokter() {
        doktersRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot data : queryDocumentSnapshots){
                    final Dokter dokter = new Dokter();
                    dokter.setId_dokter(Integer.valueOf(data.get("id_dokter").toString()));
                    dokter.setId_user(Integer.valueOf(data.get("id_user").toString()));
                    usersRef.whereEqualTo("id_user",dokter.getId_user()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            dokter.setNama(queryDocumentSnapshots.getDocuments().get(0).get("nama").toString());
                        }
                    });
                    dokter.setFoto("foto_dokter");
                    list.add(dokter);
                }
            }
        });
        return list;
    }
}