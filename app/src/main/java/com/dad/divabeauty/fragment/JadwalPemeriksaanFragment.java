package com.dad.divabeauty.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dad.divabeauty.adapter.ListDokterJadwalAdapter;
import com.dad.divabeauty.model.MyCalendar;
import com.dad.divabeauty.R;
import com.dad.divabeauty.RecyclerTouchListener;
import com.dad.divabeauty.model.SlotJam;
import com.dad.divabeauty.adapter.CalendarAdapter;
import com.dad.divabeauty.model.Dokter;
import com.dad.divabeauty.adapter.myCalendarData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class JadwalPemeriksaanFragment extends Fragment {
    private final List<MyCalendar> calendarList= new ArrayList<>();
    private CalendarAdapter mAdapter;
    private ArrayList<Dokter> list = new ArrayList<>();
    private RecyclerView rv_dokter;
    private RecyclerView rv_calendar;
    private String tgl;
    private SlotJam slotjam = new SlotJam();
    private Dokter dokter = new Dokter();
    private Integer id_hari;
    private CollectionReference slotRef;
    private CollectionReference doktersRef;
    private CollectionReference usersRef;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Integer id_dokter;
    private Integer id_user;
    private int[] ids;
    private int i;
    private TextView pilihan_dokter;
    private Button pilih;

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

        pilih = view.findViewById(R.id.btn_pilih_jadwal);

        mAdapter = new CalendarAdapter(calendarList);
        rv_calendar = view.getRootView().findViewById(R.id.rv_calendar);
        rv_calendar.setHasFixedSize(true);

        slotjam.setId_slot_jam(0);
        id_hari = 0;
        dokter.setId_dokter(0);

        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        rv_calendar.setLayoutManager(mLayoutManager);
        rv_calendar.setItemAnimator(new DefaultItemAnimator());
        rv_calendar.setAdapter(mAdapter);
        rv_calendar.addOnItemTouchListener(new RecyclerTouchListener(getContext(), rv_calendar, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                for (int i = 0; i < calendarList.size()-1; i++){
                    MaterialCardView childView = (MaterialCardView) rv_calendar.getChildAt(i);
                    childView.setChecked(false);
                }
//                Log.d("selected tgl", String.valueOf(position));
                MaterialCardView cardView;
                if(position < 4)
                    cardView = (MaterialCardView) rv_calendar.getChildAt(position);
                else {
                    cardView = (MaterialCardView) rv_calendar.getChildAt(position-1);
//                    Log.d("kurang dr 4","true");
                }
                cardView.setChecked(true);
                MyCalendar calendar = calendarList.get(position);
                tgl = calendar.getDate() + " " + calendar.getMonth() + " " + calendar.getYear();
                id_hari = calendar.getHariInt(calendar.getDay());
                Log.d("hari ke", String.valueOf(id_hari));
                updateListDokter();
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        prepareCalendarData();

        rv_dokter = view.findViewById(R.id.rv_dokter_jadwal);
        rv_dokter.setHasFixedSize(true);

        pilihan_dokter = view.findViewById(R.id.pilihan_dokter_jadwal);
        pilihan_dokter.setVisibility(View.VISIBLE);

        rv_dokter.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), rv_dokter, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                dokter = list.get(position);
                for (int i = 0; i < list.size(); i++){
                    MaterialCardView childView = (MaterialCardView) rv_dokter.getChildAt(i);
                    childView.setChecked(false);
                }

                MaterialCardView childView = (MaterialCardView) rv_dokter.getChildAt(position);
                childView.setChecked(true);
                pilih.setEnabled(true);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        ChipGroup chipGroup = view.getRootView().findViewById(R.id.chipGroup);
        chipGroup.setSelectionRequired(true);

        final Chip slot1 = view.getRootView().findViewById(R.id.slotjam_1);
        final Chip slot2 = view.getRootView().findViewById(R.id.slotjam_2);
        final Chip slot3 = view.getRootView().findViewById(R.id.slotjam_3);

        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                if(slot1.isChecked()){
                    slotjam.setSlot_jam("08.00 - 12.00");
                    slotjam.setId_slot_jam(1);
                }
                else if(slot2.isChecked()){
                    slotjam.setSlot_jam("13.00 - 17.00");
                    slotjam.setId_slot_jam(2);
                }
                else if(slot3.isChecked()){
                    slotjam.setSlot_jam("18.00 - 20.00");
                    slotjam.setId_slot_jam(3);
                }
                Log.d("slot ke", String.valueOf(slotjam.getId_slot_jam()));
                updateListDokter();
            }
        });


        pilih.setEnabled(false);
        pilih.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id_hari != 0 && slotjam.getId_slot_jam() != 0 && dokter.getId_dokter() != 0){
                    Jadwal_KeluhanFotoFragment keluhanFotoFragment = new Jadwal_KeluhanFotoFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("EXTRA_DOKTER", dokter);
                    bundle.putParcelable("EXTRA_SLOT", slotjam);
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
            }
        });

    }

//    private void getListSlot() {
//        slotRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                for(QueryDocumentSnapshot data : queryDocumentSnapshots){
//                    final SlotJam slotJam = new SlotJam();
//                    slotJam.setSlot_jam(data.get("waktu").toString());
//                    listSlot.add(slotJam);
//                    Log.d("slotJam","Memasukkan slotjam ke listslot"+slotJam.getSlot_jam());
//                }
////                rv_slotjam.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
////                ListSlotJamAdapter listSlotJamAdapter = new ListSlotJamAdapter(listSlot);
////                rv_slotjam.setAdapter(listSlotJamAdapter);
//            }
//        });
//    }

    private void prepareCalendarData() {
        myCalendarData m_calendar = new myCalendarData(0);

        for ( int i=0; i <7; i++) {
            MyCalendar calendar = new MyCalendar(m_calendar.getWeekDay(), String.valueOf(m_calendar.getDay()), String.valueOf(m_calendar.getMonth()), String.valueOf(m_calendar.getYear()),i);
            m_calendar.getNextWeekDay(1);
            calendarList.add(calendar);
        }

        mAdapter.notifyDataSetChanged();
    }

//    private void getListDokter() {
//        doktersRef.orderBy("id_user").limit(2).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                for(QueryDocumentSnapshot data : queryDocumentSnapshots){
//                    id_dokter = Integer.valueOf(data.get("id_dokter").toString());
//                    id_user = Integer.valueOf(data.get("id_user").toString());
//                    usersRef.whereEqualTo("id_user",id_user).limit(1).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                        @Override
//                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                            String nama = queryDocumentSnapshots.getDocuments().get(0).get("nama").toString();
//                            Dokter dokter = new Dokter();
//                            dokter.setId_dokter(id_dokter);
//                            dokter.setId_user(id_user);
//                            dokter.setNama(nama);
//                            Log.d("nama dokter",nama);
//                            dokter.setFoto("foto_dokter");
//                            list.add(dokter);
//                            showDokterList();
//                        }
//                    });
//                }
//            }
//        });
//    }

    private void showDokterList(){
        rv_dokter.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        ListDokterJadwalAdapter listDokterAdapter = new ListDokterJadwalAdapter(list);
        rv_dokter.setAdapter(listDokterAdapter);
        pilihan_dokter.setVisibility(View.GONE);
    }

    private void updateListDokter() {
        CollectionReference jadwalRef = db.collection("jadwal");
        if (id_hari != 0 && slotjam.getId_slot_jam() != 0){
            jadwalRef.whereEqualTo("id_hari",id_hari)
                    .whereEqualTo("id_slot",slotjam.getId_slot_jam())
                    .limit(1)
                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (!queryDocumentSnapshots.getDocuments().isEmpty()) {
                                String str_id_dokter = queryDocumentSnapshots.getDocuments().get(0).get("id_dokter").toString();
                                str_id_dokter = str_id_dokter.replaceAll("\\s+", "");
                                str_id_dokter = str_id_dokter.replace("[", "");
                                str_id_dokter = str_id_dokter.replace("]", "");
                                final String[] id_dokters = str_id_dokter.split(",");
                                Log.d("length", String.valueOf(id_dokters.length));
                                for (i=0;i<id_dokters.length;i++){
                                    id_dokter = Integer.parseInt(id_dokters[i]);
                                    Log.d("id_dokter", String.valueOf(id_dokter));
                                    doktersRef.whereEqualTo("id_dokter",id_dokter).limit(1).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            id_user = Integer.valueOf(queryDocumentSnapshots.getDocuments().get(0).get("id_user").toString());
                                            Log.d("id_user", String.valueOf(id_user));

                                            usersRef.whereEqualTo("id_user",id_user).limit(1).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                @Override
                                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                    String nama = queryDocumentSnapshots.getDocuments().get(0).get("nama").toString();
                                                    Dokter dokter = new Dokter();
                                                    dokter.setId_dokter(id_dokter);
                                                    dokter.setId_user(id_user);
                                                    dokter.setNama(nama);
                                                    Log.d("nama dokter",nama);
                                                    dokter.setFoto("foto_dokter");
                                                    list.add(dokter);
                                                    showDokterList();
                                                }
                                            });
                                        }
                                    });
                                }
                            }
                        }
                    });
        }
//
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
    }
}