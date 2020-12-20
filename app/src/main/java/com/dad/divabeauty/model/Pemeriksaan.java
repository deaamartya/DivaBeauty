package com.dad.divabeauty.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Pemeriksaan implements Parcelable {
    private String id_pemeriksaan;
    private String id_pasien;
    private String id_dokter;
    private String tgl_periksa;
    private String no_antrian;
    private String keluhan_awal;
    private String foto;
    private String waktu_dibuat;
    private Boolean status;
    private String perkiraan_jam_periksa;

    public Pemeriksaan()
    {

    }

    protected Pemeriksaan(Parcel in) {
        id_pemeriksaan = in.readString();
        id_pasien = in.readString();
        id_dokter = in.readString();
        tgl_periksa = in.readString();
        no_antrian = in.readString();
        keluhan_awal = in.readString();
        foto = in.readString();
        waktu_dibuat = in.readString();
        byte tmpStatus = in.readByte();
        status = tmpStatus == 0 ? null : tmpStatus == 1;
        perkiraan_jam_periksa = in.readString();
    }

    public static final Creator<Pemeriksaan> CREATOR = new Creator<Pemeriksaan>() {
        @Override
        public Pemeriksaan createFromParcel(Parcel in) {
            return new Pemeriksaan(in);
        }

        @Override
        public Pemeriksaan[] newArray(int size) {
            return new Pemeriksaan[size];
        }
    };

    public String getId_pemeriksaan() {
        return id_pemeriksaan;
    }

    public void setId_pemeriksaan(String id_pemeriksaan) {
        this.id_pemeriksaan = id_pemeriksaan;
    }

    public String getId_pasien() {
        return id_pasien;
    }

    public void setId_pasien(String id_pasien) {
        this.id_pasien = id_pasien;
    }

    public String getId_dokter() {
        return id_dokter;
    }

    public void setId_dokter(String id_dokter) {
        this.id_dokter = id_dokter;
    }

    public String getTgl_periksa() {
        return tgl_periksa;
    }

    public void setTgl_periksa(String tgl_periksa) {
        this.tgl_periksa = tgl_periksa;
    }

    public String getNo_antrian() {
        return no_antrian;
    }

    public void setNo_antrian(String no_antrian) {
        this.no_antrian = no_antrian;
    }

    public String getKeluhan_awal() {
        return keluhan_awal;
    }

    public void setKeluhan_awal(String keluhan_awal) {
        this.keluhan_awal = keluhan_awal;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getWaktu_dibuat() {
        return waktu_dibuat;
    }

    public void setWaktu_dibuat(String waktu_dibuat) {
        this.waktu_dibuat = waktu_dibuat;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getPerkiraan_jam_periksa() {
        return perkiraan_jam_periksa;
    }

    public void setPerkiraan_jam_periksa(String perkiraan_jam_periksa) {
        this.perkiraan_jam_periksa = perkiraan_jam_periksa;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id_pemeriksaan);
        dest.writeString(id_pasien);
        dest.writeString(id_dokter);
        dest.writeString(tgl_periksa);
        dest.writeString(no_antrian);
        dest.writeString(keluhan_awal);
        dest.writeString(foto);
        dest.writeString(waktu_dibuat);
        dest.writeByte((byte) (status == null ? 0 : status ? 1 : 2));
        dest.writeString(perkiraan_jam_periksa);
    }
}
