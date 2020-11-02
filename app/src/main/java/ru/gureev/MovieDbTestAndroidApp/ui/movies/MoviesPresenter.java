package ru.gureev.MovieDbTestAndroidApp.ui.movies;

import android.util.Log;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.gureev.MovieDbTestAndroidApp.AppConfig;
import ru.gureev.MovieDbTestAndroidApp.POJOs.enities.Genre;
import ru.gureev.MovieDbTestAndroidApp.POJOs.enities.Movie;
import ru.gureev.MovieDbTestAndroidApp.POJOs.responses.GenresResponse;
import ru.gureev.MovieDbTestAndroidApp.POJOs.responses.MoviesResponse;
import ru.gureev.MovieDbTestAndroidApp.TypeAdapter;
import ru.gureev.MovieDbTestAndroidApp.network.NetworkService;

public class MoviesPresenter extends ViewModel implements MoviesContract.Presenter {

    private MoviesContract.View view;
    private MutableLiveData<List<Movie>> movieLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Genre>> genreLiveData = new MutableLiveData<>();
    private boolean isNextPage = false;
    private int totalPages = 1;

    private int currentPage = 1;
    private String currentQuery = "";
    private TypeAdapter currentTypeAdapter = TypeAdapter.LINER;


    Callback<MoviesResponse> moviesResponseCallback = new Callback<MoviesResponse>() {
        @Override
        public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
            boolean listIsEmpty;
            if(response.body()!=null){
                listIsEmpty = response.body().getResults().isEmpty();
                Log.d("NetworkService", "onResponse: " + response.body().toString());
            }else{
                return;
            }

            if (!isNextPage && !listIsEmpty) {
                movieLiveData.setValue(response.body().getResults());
                totalPages = response.body().getTotal_pages();
                view.showTextViewNothingFound(false);
                view.showRecyclerView(true);
                loadFullInfo();
            } else if (isNextPage) {
                List<Movie> temp = movieLiveData.getValue();
                temp.addAll(response.body().getResults());
                movieLiveData.setValue(temp);
                view.showTextViewNothingFound(false);
                view.showRecyclerView(true);
                loadFullInfo();
            } else if (listIsEmpty) {
                if (view != null) {
                    view.showTextViewNothingFound(true);
                    view.showRecyclerView(false);
                }
            }
        }

        @Override
        public void onFailure(Call<MoviesResponse> call, Throwable t) {
            Log.d("NetworkService", "onFailure: " + call.toString());
        }
    };

    @Override
    public void setView(MoviesContract.View view) {
        this.view = view;
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

    private void loadGenres(){

        Callback<GenresResponse> genresResponseCallback = new Callback<GenresResponse>() {
            @Override
            public void onResponse(Call<GenresResponse> call, Response<GenresResponse> response) {
                Log.d("NetworkService", "onResponse: " + response.body().toString());
            }

            @Override
            public void onFailure(Call<GenresResponse> call, Throwable t) {
                Log.d("NetworkService", "onFailure: " + call.toString());

            }
        };

        NetworkService.getInstance()
                .getJsonPlaceHolderApi()
                .getGenres(AppConfig.API_KEY, AppConfig.LANG_RU)
                .enqueue(genresResponseCallback);
    }


    private void loadFullInfo() {
        Callback<Movie> movieResponseCallback = new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Log.d("NetworkService", "onResponse: " + response.body().toString());
                int index = 0;
                List<Movie> tempList = movieLiveData.getValue();
                for (Movie movie : tempList) {
                    if (movie.getId() == response.body().getId()) {
                        index = movieLiveData.getValue().indexOf(movie);
                        break;
                    }
                }

                Movie movie = tempList.get(index);
                if (movie.getId() == response.body().getId()) {
                    tempList.get(index).setRuntime(response.body().getRuntime());
                    tempList.get(index).setGenres(response.body().getGenres());
                    movieLiveData.setValue(tempList);
                }

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.d("NetworkService", "onFailure: " + call.toString());
            }
        };

        for (Movie movie : movieLiveData.getValue()) {
            NetworkService.getInstance()
                    .getJsonPlaceHolderApi()
                    .getOneMovie(movie.getId(), AppConfig.API_KEY, AppConfig.LANG_RU)
                    .enqueue(movieResponseCallback);
        }
    }


}