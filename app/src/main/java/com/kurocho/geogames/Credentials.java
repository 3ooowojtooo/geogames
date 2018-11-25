package com.kurocho.geogames;

import com.google.gson.annotations.SerializedName;

public class Credentials {

    private String username;
    private String password;

    Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
