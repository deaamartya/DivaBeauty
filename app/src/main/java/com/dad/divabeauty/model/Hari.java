package com.dad.divabeauty.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Hari implements Parcelable {
    private String id_hari;
    private String hari;

    protected Hari(Parcel in) {
        id_hari = in.readString();
        hari = in.readString();
    }

    public static final Creator<Hari> CREATOR = new Creator<Hari>() {
        @Override
        public Hari createFromParcel(Parcel in) {
            return new Hari(in);
        }

        @Override
        public Hari[] newArray(int size) {
            return new Hari[size];
        }
    };

    public String getId_hari() {
        return id_hari;
    }

    public void setId_hari(String id_hari) {
        this.id_hari = id_hari;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public Hari(){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id_hari);
        dest.writeString(hari);
    }
}
