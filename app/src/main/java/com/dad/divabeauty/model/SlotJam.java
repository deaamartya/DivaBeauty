package com.dad.divabeauty.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SlotJam implements Parcelable {
    private Integer id_slot_jam;
    private String slot_jam;

    public SlotJam(){}

    public int getStartClock(){
        if (id_slot_jam == 1){
            return 8;
        }
        else if (id_slot_jam == 2){
            return 13;
        }
        else if (id_slot_jam == 3){
            return 18;
        }
        return 0;
    }

    protected SlotJam(Parcel in) {
        if (in.readByte() == 0) {
            id_slot_jam = null;
        } else {
            id_slot_jam = in.readInt();
        }
        slot_jam = in.readString();
    }

    public static final Creator<SlotJam> CREATOR = new Creator<SlotJam>() {
        @Override
        public SlotJam createFromParcel(Parcel in) {
            return new SlotJam(in);
        }

        @Override
        public SlotJam[] newArray(int size) {
            return new SlotJam[size];
        }
    };

    public String getSlot_jam() {
        return slot_jam;
    }

    public void setSlot_jam(String slot_jam) {
        this.slot_jam = slot_jam;
    }

    public Integer getId_slot_jam() {
        return id_slot_jam;
    }

    public void setId_slot_jam(Integer id_slot_jam) {
        this.id_slot_jam = id_slot_jam;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id_slot_jam == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id_slot_jam);
        }
        parcel.writeString(slot_jam);
    }
}
