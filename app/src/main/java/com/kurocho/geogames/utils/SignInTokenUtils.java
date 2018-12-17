package com.kurocho.geogames.utils;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import com.kurocho.geogames.api.Token;
import com.kurocho.geogames.di.qualifiers.SignInSharedPreferences;
import com.kurocho.geogames.utils.exception.TokenNotSetException;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SignInTokenUtils {

    private static final String TOKEN_KEY = "token";
    private static final String TOKEN_NOT_SET = "NOT_SET";

    private SharedPreferences signInSharedPreferences;

    @Inject
    public SignInTokenUtils(@SignInSharedPreferences SharedPreferences signInSharedPreferences){
        this.signInSharedPreferences = signInSharedPreferences;
    }

    @NonNull
    public Token getToken() throws TokenNotSetException {
        return new Token(getTokenAsString());
    }

    public void setTokenDeletingExistingOne(@NonNull Token token){
        deleteToken();
        setToken(token.getToken());
    }

    public void deleteToken(){
        signInSharedPreferences.edit().
                putString(TOKEN_KEY, TOKEN_NOT_SET).
                apply();
    }

    @NonNull
    private String getTokenAsString() throws TokenNotSetException {
        String token = signInSharedPreferences.getString(TOKEN_KEY, TOKEN_NOT_SET);
        if(token == null || token.equals(TOKEN_NOT_SET)){
            throw new TokenNotSetException("Token is not set");
        }
        return token;
    }

    private void setToken(@NonNull String token){
        signInSharedPreferences.edit().
                putString(TOKEN_KEY, token).
                apply();
    }
}
