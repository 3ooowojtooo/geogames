package com.kurocho.geogames.api;

import com.kurocho.geogames.api.sign_up.ApiSignUpResponse;
import com.kurocho.geogames.api.sign_up.SignUpCredentials;
import retrofit2.Call;
import retrofit2.http.*;

public interface Api {
    String BASE_URL = "https://geogames.herokuapp.com/";

    @POST("/auth/register")
    Call<ApiSignUpResponse> signUp(@Body SignUpCredentials c);

    @POST("/auth/authenticate")
    Call<Void> signIn(@Body SignInCredentials c);

    @POST("/users/{user}")
    Call<User> getUser(@Path("user") String username);

//    @POST("/games/upload")
//    Call<GameDescription> uploadGame(@Header("token") String token, @Field('game') Game game);
//
//    @GET("/games/{id}")
//    Call<Game> getGameById(@Header("token") String token, @Path("id") String gameId);
//
//    @GET("/listing")
//    Call<List<GameDescription>> listGames(@Header("token") String token,
//                                          @Query("p") Integer page, @Query("string") String searchString, @Query("o") String orderBy);
//    @GET("/listing/{username}")
//    Call<List<GameDescription>> listPrivateGames(@Header("token") String token, @Path("username") String login,
//                                          @Query("p") Integer page, @Query("string") String searchString, @Query("order") String orderBy);


}
