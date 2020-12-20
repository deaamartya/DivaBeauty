package com.dad.divabeauty.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Pasien implements Parcelable {
    private String id_pasien;
    private String id_user;
    private String no_ktp_pasien;
    private String no_kk_pasien;
    private String riwayat_kesehatan;

    public Pasien(){

    }

    protected Pasien(Parcel in) {
        id_pasien = in.readString();
        id_user = in.readString();
        no_ktp_pasien = in.readString();
        no_kk_pasien = in.readString();
        riwayat_kesehatan = in.readString();
    }

    public static final Creator<Pasien> CREATOR = new Creator<Pasien>() {
        @Override
        public Pasien createFromParcel(Parcel in) {
            return new Pasien(in);
        }

        @Override
        public Pasien[] newArray(int size) {
            return new Pasien[size];
        }
    };

    public String getId_pasien() {
        return id_pasien;
    }

    public void setId_pasien(String id_pasien) {
        this.id_pasien = id_pasien;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getNo_ktp_pasien() {
        return no_ktp_pasien;
    }

    public void setNo_ktp_pasien(String no_ktp_pasien) {
        this.no_ktp_pasien = no_ktp_pasien;
    }

    public String getNo_kk_pasien() {
        return no_kk_pasien;
    }

    public void setNo_kk_pasien(String no_kk_pasien) {
        this.no_kk_pasien = no_kk_pasien;
    }

    public String getRiwayat_kesehatan() {
        return riwayat_kesehatan;
    }

    public void setRiwayat_kesehatan(String riwayat_kesehatan) {
        this.riwayat_kesehatan = riwayat_kesehatan;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id_pasien);
        dest.writeString(id_user);
        dest.writeString(no_ktp_pasien);
        dest.writeString(no_kk_pasien);
        dest.writeString(riwayat_kesehatan);
    }
}
