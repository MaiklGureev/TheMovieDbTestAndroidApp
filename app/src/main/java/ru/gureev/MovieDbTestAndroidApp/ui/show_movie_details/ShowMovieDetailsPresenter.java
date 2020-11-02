package ru.gureev.MovieDbTestAndroidApp.ui.show_movie_details;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.gureev.MovieDbTestAndroidApp.AppConfig;
import ru.gureev.MovieDbTestAndroidApp.POJOs.enities.Movie;
import ru.gureev.MovieDbTestAndroidApp.repository.Repository;
import ru.gureev.MovieDbTestAndroidApp.network.NetworkService;

public class ShowMovieDetailsPresenter extends ViewModel implements ShowMovieDetailsContract.Presenter {

    private MutableLiveData<Movie> currentMovieMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isFavoriteMovieMutableLiveData = new MutableLiveData<>();
    private ShowMovieDetailsContract.View view;

    Callback<Movie> movieResponseCallback = new Callback<Movie>() {
        @Override
        public void onResponse(Call<Movie> call, Response<Movie> response) {
            Log.d("NetworkService", "onResponse: " + response.body().toString());
            if (response.body() != null) {
                currentMovieMutableLiveData.setValue(response.body());
                boolean isFavoriteMovie = Repository
                        .getInstance()
                        .getAccountData()
                        .isFavoriteMovie(currentMovieMutableLiveData.getValue().getId());
                if (isFavoriteMovie) {
                    isFavoriteMovieMutableLiveData.setValue(true);
                }
            }
        }

        @Override
        public void onFailure(Call<Movie> call, Throwable t) {
            Log.d("NetworkService", "onFailure: " + call.toString());
        }
    };

    public ShowMovieDetailsPresenter() {
    }

    public void setView(ShowMovieDetailsContract.View view) {
        this.view = view;
    }


    @Override
    public MutableLiveData<Movie> getCurrentMovieMutableLiveData() {
        return currentMovieMutableLiveData;
    }

    @Override
    public MutableLiveData<Boolean> getIsFavoriteMovieMutableLiveData() {
        return isFavoriteMovieMutableLiveData;
    }

    @Override
    public void loadMovieDetails(int id) {
        NetworkService.getInstance()
                .getJsonPlaceHolderApi()
                .getOneMovie(id, AppConfig.API_KEY, AppConfig.LANG_RU)
                .enqueue(movieResponseCallback);
    }

    @Override
    public void switchMovieFavoriteStatus(boolean newValue) {
        if (currentMovieMutableLiveData.getValue() != null) {
            Repository.getInstance()
                    .getAccountData()
                    .setFavoriteMovie(currentMovieMutableLiveData.getValue().getId(), newValue);

        }
    }


}
