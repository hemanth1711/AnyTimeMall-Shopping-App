package com.bawp.testmall.model;

import android.provider.ContactsContract;

public class login_system {
    private int id;
    private String FullName;
    private String password;
    private String ConformPassword;
    private String Email;

    public login_system(int id, String fullName, String password, String conformPassword, String email) {
        this.id = id;
        FullName = fullName;
        this.password = password;
        ConformPassword = conformPassword;
        Email = email;
    }

    public login_system(String fullName, String password, String conformPassword, String email) {
        FullName = fullName;
        this.password = password;
        ConformPassword = conformPassword;
        Email = email;
    }

    public login_system() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConformPassword() {
        return ConformPassword;
    }

    public void setConformPassword(String conformPassword) {
        ConformPassword = conformPassword;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
