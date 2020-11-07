package ru.gureev.MovieDbTestAndroidApp.ui.main.show_movie_details;

import androidx.lifecycle.MutableLiveData;

import ru.gureev.MovieDbTestAndroidApp.POJOs.enities.Movie;

public interface ShowMovieDetailsContract {

    interface View {
        void switchFavoriteMovie(boolean isFavorite);
    }

    interface Presenter {
        MutableLiveData<Movie> getCurrentMovieMutableLiveData();

        MutableLiveData<Boolean> getIsFavoriteMovieMutableLiveData();

        void loadMovieDetails(int id);

        void switchMovieFavoriteStatus(boolean newValue);
    }
}
