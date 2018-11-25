package com.kurocho.geogames;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

interface Api {
    String BASE_URL = "https://radiant-oasis-73704.herokuapp.com/";

    @POST("/signup")
    Call<String> signUp(@Header("login") String login, @Header("password") String passwordHash, @Header("mail") String mail);

    @POST("/auth/authenticate")
    Call<Void> signIn(@Body Credentials c);

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
