package com.kurocho.geogames;

import org.junit.Before;
import org.junit.Test;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ApiTest {
    Api apiService;
    @Before
    public void initObjects() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .build();
         apiService = retrofit.create(Api.class);
    }

    @Test
    public void testAuth(){
        Call<Token> token = apiService.signIn(new Credentials("admin","Test1234"));
        token.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                Token token = response.body();
            }
            @Override
            public void onFailure(Call<Token> call, Throwable t) {

            }
        });
    }
}
