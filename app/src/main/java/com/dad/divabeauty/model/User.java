package com.dad.divabeauty.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String id_user;
    private String id_tipe_user;
    private String username;
    private String email;
    private String password;
    private String nama;
    private String no_telp;
    private Boolean jenis_kelamin;

    public User()
    {

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

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_tipe_user() {
        return id_tipe_user;
    }

    public void setId_tipe_user(String id_tipe_user) {
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
}
