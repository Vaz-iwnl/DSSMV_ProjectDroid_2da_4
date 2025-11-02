package com.example.mainactivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private TextView homeTitle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homeTitle = view.findViewById(R.id.textview_teste);

        fetchPopularMovies();
    }

    private void fetchPopularMovies() {
        String apiKey = BuildConfig.TMDB_API_KEY;

        Call<MovieResponse> call = RetrofitClient.getApi().getPopularMovies(apiKey);

        call.enqueue(new Callback<MovieResponse>() {

            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                if (response.isSuccessful() && response.body() != null) {
                    List<Movie> movies = response.body().getResults();

                    if (!movies.isEmpty()) {
                        Movie firstMovie = movies.get(0);
                        homeTitle.setText(firstMovie.getTitle());
                    } else {
                        homeTitle.setText("Nenhum filme popular encontrado.");
                    }
                } else {
                    String erro = "Falha a obter filme. Erro:" + response.code();
                    homeTitle.setText(erro);
                    Log.e("HomeFragment", "Erro de resposta da API: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                homeTitle.setText("Erro de rede: " + t.getMessage());
                Log.e("HomeFragment", "Erro de rede ao chamar API", t);
            }
        });
    }
}
