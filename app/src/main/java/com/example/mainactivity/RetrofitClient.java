package com.example.mainactivity;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofitTMDb = null;
    public static TMDbAPI getApi() {
        if (retrofitTMDb == null) {
            retrofitTMDb = new Retrofit.Builder()
                    .baseUrl(TMDbAPI.BASE_URL) // Usa a URL do TMDb
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitTMDb.create(TMDbAPI.class);
    }

    private static final String RESTDB_BASE_URL = "https://movieing-03c2.restdb.io/";
    private static Retrofit retrofitRestDb = null;

    public static RestDbAPI getRestDbApi() {
        if (retrofitRestDb == null) {
            retrofitRestDb = new Retrofit.Builder()
                    .baseUrl(RESTDB_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        // Retorna a nossa outra interface, a RestDbApi
        return retrofitRestDb.create(RestDbAPI.class);
    }
}