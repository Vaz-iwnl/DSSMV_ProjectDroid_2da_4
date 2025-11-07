package com.example.mainactivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    public static final String TAG = "HomeFragment";

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView= view.findViewById(R.id.home_recycler_view);

        setupRecyclerView();

        fetchPopularMovies();
    }

    private void setupRecyclerView() {
        movieAdapter = new MovieAdapter(getContext(), movieList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(movieAdapter);
    }

    private void fetchPopularMovies() {
       String apiKey = BuildConfig.TMDB_API_KEY;

        TMDbAPI apiService = RetrofitClient.getApi();
        Call<MovieResponse> call = apiService.getPopularMovies(apiKey);

        call.enqueue(new Callback<MovieResponse>() {

            @Override
           public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    movieList.clear();

                    movieList.addAll(response.body().getResults());

                    movieAdapter.notifyDataSetChanged();

                } else {
                    Log.e(TAG, "Erro de resposta da API: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("HomeFragment", "Erro de rede ao chamar API", t);
            }
        });
    }
}
