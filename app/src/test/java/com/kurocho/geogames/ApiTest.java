package com.kurocho.geogames;

import org.junit.Before;
import org.junit.Test;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;


public class ApiTest {

    private Api apiService;
    @Before
    public void initObjects() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
         apiService = retrofit.create(Api.class);
    }

    @Test
    public void testAuth(){
        Call<Void> call = apiService.signIn(new Credentials("admin","Test1234"));
        try {
            Response<Void> response = call.execute();
            Token token = new Token(response.headers().get("Authorization"));
            assertNotNull(token.getToken());
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
