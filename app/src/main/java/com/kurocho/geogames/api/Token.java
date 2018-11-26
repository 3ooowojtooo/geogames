package com.kurocho.geogames.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Token {

    private String token;

    public Token(String token) {
        this.token = token;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
