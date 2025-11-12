package com.example.mainactivity;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RestDbAPI{
    @POST("rest/usersmovie-ing")
    Call<Void> criarUser(
            @Header("x-apikey") String apiKey,
            @Body User user
    );
    @GET("rest/usersmovie-ing")
    Call<List<User>> loginUser(
            @Header("x-apikey") String apiKey,
            @Query("q") String query
    );
    @PUT("rest/usersmovie-ing/{id}")
    Call<User> updateUser(
            @Header("x-apikey") String apiKey,
            @Path("id") String userID,
            @Body User user
    );
}
