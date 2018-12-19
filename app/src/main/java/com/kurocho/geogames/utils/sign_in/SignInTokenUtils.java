package com.kurocho.geogames.utils.sign_in;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.kurocho.geogames.api.Token;
import com.kurocho.geogames.di.qualifiers.SignInSharedPreferences;
import com.kurocho.geogames.utils.exception.TokenNotSetException;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
class SignInTokenUtils {

    private static final String TOKEN_KEY = "token";
    private static final String TOKEN_NOT_SET = "NOT_SET";

    private SharedPreferences signInSharedPreferences;

    @Inject
    SignInTokenUtils(@SignInSharedPreferences SharedPreferences signInSharedPreferences){
        this.signInSharedPreferences = signInSharedPreferences;
    }


    @NonNull
    Token getToken() throws TokenNotSetException {
        if(isTokenSet()){
            return new Token(getTokenAsString());
        } else{
            throw new TokenNotSetException();
        }
    }

    void setTokenDeletingExistingOne(@NonNull Token token){
        deleteToken();
        setToken(token.getToken());
    }

    boolean isTokenSet(){
        String token = signInSharedPreferences.getString(TOKEN_KEY, TOKEN_NOT_SET);
        if(token == null || token.equals(TOKEN_NOT_SET))
            return false;
        return true;
    }

    private void deleteToken(){
        signInSharedPreferences.edit().
                putString(TOKEN_KEY, TOKEN_NOT_SET).
                apply();
    }

    @Nullable
    private String getTokenAsString(){
        return signInSharedPreferences.getString(TOKEN_KEY, TOKEN_NOT_SET);
    }

    private void setToken(@NonNull String token){
        signInSharedPreferences.edit().
                putString(TOKEN_KEY, token).
                apply();
    }
}
