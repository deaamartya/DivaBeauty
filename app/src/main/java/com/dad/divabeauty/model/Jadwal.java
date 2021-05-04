package com.dad.divabeauty.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Jadwal implements Parcelable {
    private String id_jadwal;
    private String id_slot;
    private String id_hari;
    private String id_dokter;

    public Jadwal(){

    }

    protected Jadwal(Parcel in) {
        id_jadwal = in.readString();
        id_slot = in.readString();
        id_hari = in.readString();
        id_dokter = in.readString();
    }

    public static final Creator<Jadwal> CREATOR = new Creator<Jadwal>() {
        @Override
        public Jadwal createFromParcel(Parcel in) {
            return new Jadwal(in);
        }

        @Override
        public Jadwal[] newArray(int size) {
            return new Jadwal[size];
        }
    };

    public String getId_slot() {
        return id_slot;
    }

    public void setId_slot(String id_slot) {
        this.id_slot = id_slot;
    }

    public String getId_jadwal() {
        return id_jadwal;
    }

    public void setId_jadwal(String id_jadwal) {
        this.id_jadwal = id_jadwal;
    }

    public String getId_hari() {
        return id_hari;
    }

    public void setId_hari(String id_hari) {
        this.id_hari = id_hari;
    }

    public String getId_dokter() {
        return id_dokter;
    }

    public void setId_dokter(String id_dokter) {
        this.id_dokter = id_dokter;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id_jadwal);
        dest.writeString(id_slot);
        dest.writeString(id_hari);
        dest.writeString(id_dokter);
    }
}
