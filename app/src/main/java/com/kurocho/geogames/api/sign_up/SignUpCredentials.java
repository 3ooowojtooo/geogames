package com.kurocho.geogames.api.sign_up;

import android.support.annotation.NonNull;

public class SignUpCredentials {
    @NonNull private String username;
    @NonNull private String password;
    @NonNull private String email;

    public SignUpCredentials(@NonNull String username, @NonNull String password, @NonNull String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    @NonNull
    public String getEmail() {
        return email;
    }
}