package ru.gureev.MovieDbTestAndroidApp.ui.main.favorites;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.List;

import ru.gureev.MovieDbTestAndroidApp.POJOs.enities.Movie;
import ru.gureev.MovieDbTestAndroidApp.repository.Repository;
import ru.gureev.MovieDbTestAndroidApp.tools.TypeAdapter;

public class FavoritesPresenter implements FavoritesContract.Presenter {

    private FavoritesContract.View view;
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
        Repository.getInstance().getAccountData().getFavoriteMoviesMutableLiveData().observe((LifecycleOwner) view, new Observer<List<Movie>>() {
            @Override
            public void onChanged(final List<Movie> movies) {
                movieLiveData.setValue(movies);
                Log.d(TAG, "onChanged: " + movies.toString());
            }
        });

    }

    @Override
    public void reloadMovie() {
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


}