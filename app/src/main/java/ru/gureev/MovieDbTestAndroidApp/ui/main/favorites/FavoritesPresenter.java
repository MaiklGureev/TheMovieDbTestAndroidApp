package ru.gureev.MovieDbTestAndroidApp.ui.main.favorites;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import ru.gureev.MovieDbTestAndroidApp.POJOs.enities.Genre;
import ru.gureev.MovieDbTestAndroidApp.POJOs.enities.Movie;
import ru.gureev.MovieDbTestAndroidApp.repository.Repository;
import ru.gureev.MovieDbTestAndroidApp.tools.TypeAdapter;

public class FavoritesPresenter extends ViewModel implements FavoritesContract.Presenter {

    private FavoritesContract.View view;
    private MutableLiveData<List<Genre>> genreLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Movie>> movieLiveData = new MediatorLiveData<>();
    private TypeAdapter currentTypeAdapter = TypeAdapter.LINER;
    private String currentQuery = "";
    private String TAG = "FavoritesPresenter";

    @Override
    public void setView(FavoritesContract.View view) {
        this.view = view;
        loadMovies();
    }

    @Override
    public void loadMovies() {
        Repository.getInstance().getAccountData().loadAccountData(Repository.getInstance().getAccountData().getSession_id());
        Repository.getInstance().getAccountData().getGenreMutableLiveData().observe((LifecycleOwner) view, new Observer<List<Genre>>() {
            @Override
            public void onChanged(List<Genre> genres) {
                genreLiveData.setValue(genres);
            }
        });
        Repository.getInstance().getAccountData().getFavoriteMoviesMutableLiveData().observe((LifecycleOwner) view, new Observer<List<Movie>>() {
            @Override
            public void onChanged(final List<Movie> movies) {
                movieLiveData.setValue(movies);
                setGenresToFilms();
                Log.d(TAG, "onChanged: " + movies.toString());
            }
        });

    }
    @Override
    public void reloadMovie(){
        Repository.getInstance().getAccountData().reloadFavoriteMovies();
    }

    @Override
    public MutableLiveData<List<Movie>> getMovies() {
        return movieLiveData;
    }

    @Override
    public String getCurrentQuery() {
        return currentQuery;
    }

    @Override
    public void setCurrentQuery(String query) {
        this.currentQuery = query;
    }

    @Override
    public void saveCurrentTypeAdapter(TypeAdapter currentTypeAdapter) {
        this.currentTypeAdapter = currentTypeAdapter;
    }

    @Override
    public TypeAdapter getCurrentTypeAdapter() {
        return currentTypeAdapter;
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