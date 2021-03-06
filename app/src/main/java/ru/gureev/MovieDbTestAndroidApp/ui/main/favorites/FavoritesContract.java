package ru.gureev.MovieDbTestAndroidApp.ui.main.favorites;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ru.gureev.MovieDbTestAndroidApp.POJOs.enities.Movie;
import ru.gureev.MovieDbTestAndroidApp.tools.TypeAdapter;

public interface FavoritesContract {

    interface View {
        void switchAdapter();

        void showTextViewNothingFound(boolean value);

        void showRecyclerView(boolean value);

        void setAdapter(TypeAdapter typeAdapter);
    }

    interface Presenter {
        void setView(View view);

        void loadMovies();

        MutableLiveData<List<Movie>> getMovies();

        String getCurrentQuery();

        void setCurrentQuery(String query);

        void saveCurrentTypeAdapter(TypeAdapter currentTypeAdapter);

        TypeAdapter getCurrentTypeAdapter();

        void reloadMovie();
    }

}
