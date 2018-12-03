package com.kurocho.geogames.api.sign_up;

import android.support.annotation.NonNull;

public class ApiSignUpResponse {

    @NonNull private String status;
    @NonNull private String message;

    public ApiSignUpResponse(@NonNull String status, @NonNull String message) {
        this.status = status;
        this.message = message;
    }

    @NonNull
    public String getStatus() {
        return status;
    }

    @NonNull
    public String getMessage() {
        return message;
    }
}
