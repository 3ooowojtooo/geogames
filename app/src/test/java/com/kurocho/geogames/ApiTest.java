package com.kurocho.geogames;

import com.kurocho.geogames.api.Api;
import com.kurocho.geogames.api.SignInCredentials;
import com.kurocho.geogames.api.Token;
import com.kurocho.geogames.api.SignUpCredentials;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    public void testLogin(){
        Call<Void> call = apiService.signIn(new SignInCredentials("admin","Test1234"));
        try {
            Response<Void> response = call.execute();
            Token token = new Token(response.headers().get("Authorization"));
            assertNotNull(token.getToken());
        }catch (Exception e){
            fail();
        }

    }

    @Test
    public void testRegister(){
        SignUpCredentials c = new SignUpCredentials("testowy1","okon123", "geogamestest@gmail.com");
        Call<Void> call = apiService.signUp(c);
        try {
            Response<Void> response = call.execute();
            if(!response.isSuccessful()) {
                fail();
            }
        }catch (Exception e){
            fail();
        }

    }
}
