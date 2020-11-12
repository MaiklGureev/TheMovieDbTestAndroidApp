package ru.gureev.MovieDbTestAndroidApp.ui.main.movies;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.gureev.MovieDbTestAndroidApp.AppConfig;
import ru.gureev.MovieDbTestAndroidApp.POJOs.enities.Genre;
import ru.gureev.MovieDbTestAndroidApp.POJOs.enities.Movie;
import ru.gureev.MovieDbTestAndroidApp.POJOs.genre.GenresResponse;
import ru.gureev.MovieDbTestAndroidApp.POJOs.movie.MoviesResponse;
import ru.gureev.MovieDbTestAndroidApp.network.NetworkService;
import ru.gureev.MovieDbTestAndroidApp.tools.TypeAdapter;

public class MoviesPresenter implements MoviesContract.Presenter {

    private MoviesContract.View view;
    private MutableLiveData<List<Movie>> movieLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Genre>> genreLiveData = new MutableLiveData<>();
    private boolean isNextPage = false;
    private int totalPages = 1;

    private int currentPage = 1;
    private String currentQuery = "";
    private TypeAdapter currentTypeAdapter = TypeAdapter.LINER;
    private String TAG = "MoviesPresenter";


    Callback<MoviesResponse> moviesResponseCallback = new Callback<MoviesResponse>() {
        @Override
        public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
            boolean listIsEmpty;

            if (response.body() != null) {
                listIsEmpty = response.body().getResults().isEmpty();
                Log.d(TAG, "onResponse: " + response.body().toString());
            } else {
                return;
            }

            if (!isNextPage && !listIsEmpty) {
                movieLiveData.setValue(response.body().getResults());
                totalPages = response.body().getTotal_pages();
                view.showTextViewNothingFound(false);
                view.showRecyclerView(true);
                setGenresToFilms();
            } else if (isNextPage) {
                List<Movie> temp = movieLiveData.getValue();
                temp.addAll(response.body().getResults());
                movieLiveData.setValue(temp);
                view.showTextViewNothingFound(false);
                view.showRecyclerView(true);
                setGenresToFilms();
            } else if (listIsEmpty) {
                if (view != null) {
                    view.showTextViewNothingFound(true);
                    view.showRecyclerView(false);
                }
            }
        }

        @Override
        public void onFailure(Call<MoviesResponse> call, Throwable t) {
            Log.d(TAG, "onFailure: " + call.toString());
        }
    };

    @Override
    public void setView(MoviesContract.View view) {
        this.view = view;
        loadGenres();
    }

    @Override
    public void clearSearchMovies() {
        movieLiveData.setValue(new ArrayList<Movie>());
    }

    @Override
    public String getCurrentQuery() {
        return currentQuery;
    }

    @Override
    public void loadMovies(String query, int page) {

        currentPage = page;
        currentQuery = query;
        isNextPage = page == 1 ? false : true;

        if (page < totalPages || page == 1) {
            NetworkService.getInstance()
                    .getJsonPlaceHolderApi()
                    .searchMovies(AppConfig.API_KEY, currentQuery, AppConfig.LANG_RU, true, currentPage)
                    .enqueue(moviesResponseCallback);
        }

        movieLiveData.observe((LifecycleOwner) view, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                //setGenresToFilms();
            }
        });
    }

    @Override
    public MutableLiveData<List<Movie>> getMovies() {
        return movieLiveData;
    }

    @Override
    public void saveCurrentTypeAdapter(TypeAdapter currentTypeAdapter) {
        this.currentTypeAdapter = currentTypeAdapter;
    }

    @Override
    public TypeAdapter getCurrentTypeAdapter() {
        return currentTypeAdapter;
    }

    private void loadGenres() {

        Callback<GenresResponse> genresResponseCallback = new Callback<GenresResponse>() {
            @Override
            public void onResponse(Call<GenresResponse> call, Response<GenresResponse> response) {
                Log.d(TAG, "onResponse: " + response.body().toString());
                genreLiveData.postValue(response.body().getGenres());
            }

            @Override
            public void onFailure(Call<GenresResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + call.toString());

            }
        };

        NetworkService.getInstance()
                .getJsonPlaceHolderApi()
                .getGenres(AppConfig.API_KEY, AppConfig.LANG_RU)
                .enqueue(genresResponseCallback);
    }

    void setGenresToFilms() {

        List<Genre> genreList = genreLiveData.getValue();
        List<Movie> movieList = movieLiveData.getValue();

        if (genreList != null && movieList != null) {

            for (Movie m : movieList) {
                m.setGenres(new ArrayList<Genre>());
                for (int id : m.getGenre_ids()) {
                    for (Genre gFull : genreList) {
                        if (id == gFull.getId()) {
                            m.getGenres().add(new Genre(id, gFull.getName()));
                        }
                    }
                }
            }
            movieLiveData.setValue(movieList);
        }
    }


}