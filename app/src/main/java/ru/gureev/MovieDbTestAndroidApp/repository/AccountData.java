package ru.gureev.MovieDbTestAndroidApp.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.gureev.MovieDbTestAndroidApp.AppConfig;
import ru.gureev.MovieDbTestAndroidApp.POJOs.enities.Movie;
import ru.gureev.MovieDbTestAndroidApp.POJOs.requests.AddFavoriteMovieRequest;
import ru.gureev.MovieDbTestAndroidApp.POJOs.requests.DeleteSessionRequest;
import ru.gureev.MovieDbTestAndroidApp.POJOs.requests.NewSessionIdRequest;
import ru.gureev.MovieDbTestAndroidApp.POJOs.requests.NewSessionRequest;
import ru.gureev.MovieDbTestAndroidApp.POJOs.responses.AccountResponse;
import ru.gureev.MovieDbTestAndroidApp.POJOs.responses.AddFavoriteMovieResponse;
import ru.gureev.MovieDbTestAndroidApp.POJOs.responses.DeleteSessionResponse;
import ru.gureev.MovieDbTestAndroidApp.POJOs.responses.MoviesResponse;
import ru.gureev.MovieDbTestAndroidApp.POJOs.responses.NewSessionResponse;
import ru.gureev.MovieDbTestAndroidApp.POJOs.responses.NewTokenResponse;
import ru.gureev.MovieDbTestAndroidApp.POJOs.responses.SessionIdResponse;
import ru.gureev.MovieDbTestAndroidApp.network.NetworkService;

public class AccountData {

    private static final String TAG = "AccountData";
    private String login = "MikhailGureev";
    private String password = "0000";

    private String request_token;
    private String session_id;
    private AccountResponse account;
    private MutableLiveData<AccountResponse> accountResponseMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<List<Movie>> favoriteMoviesMutableLiveData = new MutableLiveData<>();


    Callback<NewTokenResponse> newTokenResponseCallback = new Callback<NewTokenResponse>() {
        @Override
        public void onResponse(Call<NewTokenResponse> call, Response<NewTokenResponse> response) {
            Log.d("NetworkService", "onResponse: " + response.body().toString());
            request_token = response.body().getRequest_token();
            loadSession(login, password, request_token);
        }

        @Override
        public void onFailure(Call<NewTokenResponse> call, Throwable t) {
            Log.d("NetworkService", "onFailure: " + call.toString());
        }
    };

    Callback<SessionIdResponse> sessionIdResponseCallback = new Callback<SessionIdResponse>() {
        @Override
        public void onResponse(Call<SessionIdResponse> call, Response<SessionIdResponse> response) {
            Log.d("NetworkService", "onResponse: " + response.body().toString());
            session_id = (response.body().getSession_id());
            loadAccountData(session_id);
        }

        @Override
        public void onFailure(Call<SessionIdResponse> call, Throwable t) {
            Log.d("NetworkService", "onFailure: " + call.toString());
        }
    };

    Callback<AccountResponse> accountResponseCallback = new Callback<AccountResponse>() {
        @Override
        public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
            Log.d("NetworkService", "onResponse: " + response.body().toString());
            account = response.body();
            accountResponseMutableLiveData.postValue (account);
            reloadFavoriteMovies();
        }

        @Override
        public void onFailure(Call<AccountResponse> call, Throwable t) {
            Log.d("NetworkService", "onFailure: " + call.toString());
        }
    };

    Callback<NewSessionResponse> newSessionResponseCallback = new Callback<NewSessionResponse>() {
        @Override
        public void onResponse(Call<NewSessionResponse> call, Response<NewSessionResponse> response) {
            Log.d("NetworkService", "onResponse: " + response.body().toString());
            request_token = response.body().getRequest_token();
            loadSessionId(request_token);
        }

        @Override
        public void onFailure(Call<NewSessionResponse> call, Throwable t) {
            Log.d("NetworkService", "onFailure: " + call.toString());
        }
    };

    Callback<DeleteSessionResponse> deleteSessionResponseCallback = new Callback<DeleteSessionResponse>() {
        @Override
        public void onResponse(Call<DeleteSessionResponse> call, Response<DeleteSessionResponse> response) {
            Log.d("NetworkService", "onResponse: " + response.body().toString());
        }

        @Override
        public void onFailure(Call<DeleteSessionResponse> call, Throwable t) {
            Log.d("NetworkService", "onFailure: " + call.toString());
        }
    };

    Callback<MoviesResponse> moviesResponseCallback = new Callback<MoviesResponse>() {
        @Override
        public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
            Log.d("NetworkService", "onResponse: " + response.body().toString());
            if (response.body() != null) {
                int currentPage = response.body().getPage();
                int totalPages = response.body().getTotal_pages();

                List<Movie> tempList = favoriteMoviesMutableLiveData.getValue() != null ? favoriteMoviesMutableLiveData.getValue() : new ArrayList<Movie>();
                tempList.addAll(response.body().getResults());
                favoriteMoviesMutableLiveData.postValue(tempList);

                if (currentPage + 1 <= totalPages) {
                    currentPage++;
                    loadFavoriteMovies(currentPage);
                }
            }
        }

        @Override
        public void onFailure(Call<MoviesResponse> call, Throwable t) {
            Log.d("NetworkService", "onFailure: " + call.toString());
        }
    };

    Callback<AddFavoriteMovieResponse> addFavoriteMovieResponseCallback = new Callback<AddFavoriteMovieResponse>() {
        @Override
        public void onResponse(Call<AddFavoriteMovieResponse> call, Response<AddFavoriteMovieResponse> response) {
            Log.d("NetworkService", "onResponse: " + response.body().toString());
            reloadFavoriteMovies();
        }

        @Override
        public void onFailure(Call<AddFavoriteMovieResponse> call, Throwable t) {
            Log.d("NetworkService", "onFailure: " + call.toString());
        }
    };

    public void loadToken() {
        NetworkService.getInstance()
                .getJsonPlaceHolderApi()
                .getNewToken(AppConfig.API_KEY)
                .enqueue(newTokenResponseCallback);
    }

    public void loadSessionId(String token) {
        NetworkService.getInstance()
                .getJsonPlaceHolderApi()
                .getSessionId(AppConfig.API_KEY, new NewSessionIdRequest(token))
                .enqueue(sessionIdResponseCallback);
    }

    public void loadSession(String login, String password, String token) {
        NetworkService.getInstance()
                .getJsonPlaceHolderApi()
                .getNewSession(AppConfig.API_KEY, new NewSessionRequest(login, password, token))
                .enqueue(newSessionResponseCallback);
    }

    public void deleteSession() {
        NetworkService.getInstance()
                .getJsonPlaceHolderApi()
                .deleteSession(AppConfig.API_KEY, new DeleteSessionRequest(session_id))
                .enqueue(deleteSessionResponseCallback);
    }

    public void loadAccountData(String sessionId) {
        NetworkService.getInstance()
                .getJsonPlaceHolderApi()
                .getAccount(AppConfig.API_KEY, sessionId)
                .enqueue(accountResponseCallback);
    }

    public void reloadFavoriteMovies() {
        favoriteMoviesMutableLiveData = new MutableLiveData<>();
        loadFavoriteMovies(1);
        Log.d(TAG, "reloadFavoriteMovies: ");
    }

    public void loadFavoriteMovies(int page) {
        NetworkService.getInstance()
                .getJsonPlaceHolderApi()
                .getFavorites(
                        account.getId(),
                        AppConfig.API_KEY,
                        session_id,
                        AppConfig.LANG_RU,
                        null,
                        page)
                .enqueue(moviesResponseCallback);
    }

    public void setFavoriteMovie(int currentMovieId, boolean makeIsFavorite) {
        NetworkService.getInstance()
                .getJsonPlaceHolderApi()
                .setFavoritesMovie(
                        account.getId(),
                        AppConfig.API_KEY,
                        session_id,
                        new AddFavoriteMovieRequest("movie", currentMovieId, makeIsFavorite)
                )
                .enqueue(addFavoriteMovieResponseCallback);
    }

    public boolean isFavoriteMovie(int currentMovieId) {
        for (Movie movie : favoriteMoviesMutableLiveData.getValue()) {
            if (movie.getId() == currentMovieId) {
                return true;
            }
        }
        return false;
    }

    public AccountResponse getAccount() {
        return account;
    }

    public String getSession_id() {
        return session_id;
    }

    public MutableLiveData<List<Movie>> getFavoriteMoviesMutableLiveData() {
        return favoriteMoviesMutableLiveData;
    }

    public MutableLiveData<AccountResponse> getAccountResponseMutableLiveData() {
        return accountResponseMutableLiveData;
    }

    public void setAccountResponseMutableLiveData(MutableLiveData<AccountResponse> accountResponseMutableLiveData) {
        this.accountResponseMutableLiveData = accountResponseMutableLiveData;
    }
}
