package com.example.mainactivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    public static final String TAG = "HomeFragment";
    private TextView homeTitle;
    private ImageView homePoster;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homeTitle = view.findViewById(R.id.textview_teste);
        homePoster = view.findViewById(R.id.home_fragment_poster);

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

                        String posterPath = firstMovie.getPosterPath();

                        // 2. Vamos imprimir o caminho no Logcat
                        Log.d(TAG, "Poster path recebido: " + posterPath);

                        if (posterPath != null && !posterPath.isEmpty()) {
                            // 3. Se o caminho NÃO for nulo, construímos o URL
                            String posterUrl = "https://image.tmdb.org/t/p/w500" + posterPath;

                            // 4. Imprimir o URL completo
                            Log.d(TAG, "A carregar imagem de: " + posterUrl);

                            // 5. Dizemos ao Glide para carregar E mostar um erro se falhar
                            Glide.with(HomeFragment.this)
                                    .load(posterUrl)
                                    .error(android.R.drawable.ic_dialog_alert) // 👈 Mostra um ícone de erro se falhar
                                    .into(homePoster);
                        } else {
                            // 6. Se o caminho FOR nulo, avisamos no Logcat
                            Log.e(TAG, "O posterPath para o filme '" + firstMovie.getTitle() + "' é nulo ou vazio!");
                            homePoster.setImageResource(android.R.drawable.ic_dialog_alert);} // Mostra o erro
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
