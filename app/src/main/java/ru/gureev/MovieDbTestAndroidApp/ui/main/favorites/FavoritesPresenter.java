package ru.gureev.MovieDbTestAndroidApp.ui.main.favorites;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.gureev.MovieDbTestAndroidApp.POJOs.enities.Movie;
import ru.gureev.MovieDbTestAndroidApp.repository.Repository;
import ru.gureev.MovieDbTestAndroidApp.tools.TypeAdapter;

public class FavoritesPresenter extends ViewModel implements FavoritesContract.Presenter {

    private FavoritesContract.View view;
    private MutableLiveData<List<Movie>> movieLiveData = new MediatorLiveData<>();
    private TypeAdapter currentTypeAdapter = TypeAdapter.LINER;
    private String currentQuery = "";
    private String TAG = "FavoritesPresenter";

    @Override
    public void setView(FavoritesContract.View view) {
        this.view = view;
    }

    @Override
    public void loadMovies() {

        Repository.getInstance().getAccountData().getFavoriteMoviesMutableLiveData().observe((LifecycleOwner) view, new Observer<List<Movie>>() {
            @Override
            public void onChanged(final List<Movie> movies) {

                movieLiveData.postValue(movies);
                Log.d(TAG, "onChanged: ");

            }
        });


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