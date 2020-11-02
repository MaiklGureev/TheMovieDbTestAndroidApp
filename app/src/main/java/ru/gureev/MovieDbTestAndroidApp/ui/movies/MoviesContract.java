package ru.gureev.MovieDbTestAndroidApp.ui.movies;

import android.widget.LinearLayout;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.gureev.MovieDbTestAndroidApp.POJOs.enities.Movie;
import ru.gureev.MovieDbTestAndroidApp.TypeAdapter;

public interface MoviesContract {

    interface View{
        void switchAdapter();
        void setAdapter(TypeAdapter typeAdapter);

        void showTextAndSearchView(boolean value);
        void showRecyclerView(boolean value);
        void showTextViewNothingFound(boolean value);
    }

    interface Presenter{
        void clearSearchMovies();
        String getCurrentQuery();
        void loadMovies(String query, int page);
        MutableLiveData<List<Movie>> getMovies();
        void setView(MoviesContract.View view);

        void saveCurrentTypeAdapter(TypeAdapter currentTypeAdapter);
        TypeAdapter getCurrentTypeAdapter();

    }

}
