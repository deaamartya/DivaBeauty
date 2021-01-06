package com.dad.divabeauty.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User implements Parcelable {
    private Integer id_user;
    private Integer id_tipe_user;
    private String username;
    private String email;
    private String password;
    private String nama;
    private String no_telp;
    private Boolean jenis_kelamin;
    private String tgl_lahir;
    private String created_at;

    public User() { }

    public User(Integer id_user, Integer id_tipe_user, String username, String email,String password,String nama,String no_telp,Boolean jenis_kelamin, String tgl_lahir){
        this.id_user = id_user;
        this.id_tipe_user = id_tipe_user;
        this.username = username;
        this.email = email;
        this.password = password;
        this.nama = nama;
        this.no_telp = no_telp;
        this.jenis_kelamin = jenis_kelamin;
        this.tgl_lahir = tgl_lahir;
        this.created_at = getDateTime();
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    protected User(Parcel in) {
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public Integer getId_tipe_user() {
        return id_tipe_user;
    }

    public void setId_tipe_user(Integer id_tipe_user) {
        this.id_tipe_user = id_tipe_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public Boolean getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(Boolean jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getTgl_lahir() {
        return tgl_lahir;
    }

    public void setTgl_lahir(String tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
