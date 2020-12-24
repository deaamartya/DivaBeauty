package com.dad.divabeauty.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Dokter implements Parcelable {
    private String nama;
    private String foto;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Dokter(){

    }

    protected Dokter(Parcel in) {
        nama = in.readString();
        foto = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nama);
        dest.writeString(foto);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Dokter> CREATOR = new Creator<Dokter>() {
        @Override
        public Dokter createFromParcel(Parcel in) {
            return new Dokter(in);
        }

        @Override
        public Dokter[] newArray(int size) {
            return new Dokter[size];
        }
    };
}
