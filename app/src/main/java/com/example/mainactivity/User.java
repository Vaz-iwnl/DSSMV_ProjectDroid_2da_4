package com.example.mainactivity;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("_id")
    String _id;
    String email;
    String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String get_id() { return _id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}