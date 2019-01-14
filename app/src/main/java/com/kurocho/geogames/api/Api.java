package com.kurocho.geogames.api;

import com.kurocho.geogames.api.create_game.GameCreationRequest;
import com.kurocho.geogames.api.download_game.GameRetrievalRequest;
import com.kurocho.geogames.api.sign_up.SignUpApiResponse;
import com.kurocho.geogames.api.sign_up.SignUpCredentials;
import com.kurocho.geogames.data.search.SearchGameDetails;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface Api {

    String BASE_URL = "https://geogamesapi.herokuapp.com/";
    String AUTH_API_URL = "https://geogames.herokuapp.com/";

    @POST(AUTH_API_URL+"auth/register")
    Call<SignUpApiResponse> signUp(@Body SignUpCredentials c);

    @POST(AUTH_API_URL+"auth/authenticate")
    Call<Void> signIn(@Body SignInCredentials c);

    @POST(BASE_URL+"private/create")
    Call<Void> createGame(@Body GameCreationRequest gameCreationRequest, @Header("Authorization") String token);

    @GET(BASE_URL+"public/games")
    Call<List<SearchGameDetails>> getPublicGames();

    @GET(BASE_URL+"public/{gameid}")
    Call<GameRetrievalRequest> getGame(@Path("gameid") Integer gameId);



//    @POST("users/{user}")
//    Call<User> getUser(@Path("user") String username);

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
//    Call<List<GameDescription>> listPrivateGames(@Header("token") String token, @Path("username") String sign_in,
//                                          @Query("p") Integer page, @Query("string") String searchString, @Query("order") String orderBy);


}
