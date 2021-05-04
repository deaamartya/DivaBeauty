package com.dad.divabeauty.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TipeUser implements Parcelable {
    private String id_tipe_user;
    private String nama_tipe_user;

    public TipeUser()
    {

    }

    protected TipeUser(Parcel in) {
        id_tipe_user = in.readString();
        nama_tipe_user = in.readString();
    }

    public static final Creator<TipeUser> CREATOR = new Creator<TipeUser>() {
        @Override
        public TipeUser createFromParcel(Parcel in) {
            return new TipeUser(in);
        }

        @Override
        public TipeUser[] newArray(int size) {
            return new TipeUser[size];
        }
    };

    public String getId_tipe_user() {
        return id_tipe_user;
    }

    public void setId_tipe_user(String id_tipe_user) {
        this.id_tipe_user = id_tipe_user;
    }

    public String getNama_tipe_user() {
        return nama_tipe_user;
    }

    public void setNama_tipe_user(String nama_tipe_user) {
        this.nama_tipe_user = nama_tipe_user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id_tipe_user);
        dest.writeString(nama_tipe_user);
    }
}
