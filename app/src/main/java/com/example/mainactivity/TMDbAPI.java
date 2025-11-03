package com.example.mainactivity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TMDbAPI {

    String BASE_URL = "https://api.themoviedb.org/3/";

    @GET("movie/popular")

    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);

    // Depois tenho de adicionar outros endpoints(getMovieDetails, searchMovies, etc, por agora é so para testar)
}

