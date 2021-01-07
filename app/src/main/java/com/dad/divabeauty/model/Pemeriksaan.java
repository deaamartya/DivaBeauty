package com.dad.divabeauty.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Pemeriksaan {
    private Integer id_pemeriksaan;
    private Integer id_pasien;
    private Integer id_dokter;
    private String tgl_periksa;
    private Integer no_antrian;
    private String keluhan_awal;
    private String foto;
    private String waktu_dibuat;
    private String status;
    private String perkiraan_jam_periksa;

    public Pemeriksaan()
    {

    }

    public Pemeriksaan(Integer id_pemeriksaan, Integer id_pasien, Integer id_dokter, String tgl_periksa, Integer no_antrian, String keluhan_awal, String foto, String waktu_dibuat, String status, String perkiraan_jam_periksa){
        this.id_pemeriksaan = id_pemeriksaan;
        this.id_pasien = id_pasien;
        this.id_dokter = id_dokter;
        this.tgl_periksa = tgl_periksa;
        this.no_antrian = no_antrian;
        this.keluhan_awal = keluhan_awal;
        this.foto = foto;
        this.waktu_dibuat = waktu_dibuat;
        this.status = status;
        this.perkiraan_jam_periksa = perkiraan_jam_periksa;
    }

    public Integer getId_pemeriksaan() {
        return id_pemeriksaan;
    }

    public void setId_pemeriksaan(Integer id_pemeriksaan) {
        this.id_pemeriksaan = id_pemeriksaan;
    }

    public Integer getId_pasien() {
        return id_pasien;
    }

    public void setId_pasien(Integer id_pasien) {
        this.id_pasien = id_pasien;
    }

    public Integer getId_dokter() {
        return id_dokter;
    }

    public void setId_dokter(Integer id_dokter) {
        this.id_dokter = id_dokter;
    }

    public String getTgl_periksa() {
        return tgl_periksa;
    }

    public void setTgl_periksa(String tgl_periksa) {
        this.tgl_periksa = tgl_periksa;
    }

    public Integer getNo_antrian() {
        return no_antrian;
    }

    public void setNo_antrian(Integer no_antrian) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPerkiraan_jam_periksa() {
        return perkiraan_jam_periksa;
    }

    public void setPerkiraan_jam_periksa(String perkiraan_jam_periksa) {
        this.perkiraan_jam_periksa = perkiraan_jam_periksa;
    }
}
