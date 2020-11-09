package ru.gureev.MovieDbTestAndroidApp.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.redmadrobot.pinkman.Pinkman;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.gureev.MovieDbTestAndroidApp.AppConfig;
import ru.gureev.MovieDbTestAndroidApp.POJOs.authV3.AccountResponse;
import ru.gureev.MovieDbTestAndroidApp.POJOs.authV3.DeleteSessionRequest;
import ru.gureev.MovieDbTestAndroidApp.POJOs.authV3.DeleteSessionResponse;
import ru.gureev.MovieDbTestAndroidApp.POJOs.authV3.NewSessionIdRequest;
import ru.gureev.MovieDbTestAndroidApp.POJOs.authV3.NewSessionRequest;
import ru.gureev.MovieDbTestAndroidApp.POJOs.authV3.NewSessionResponse;
import ru.gureev.MovieDbTestAndroidApp.POJOs.authV3.NewTokenResponse;
import ru.gureev.MovieDbTestAndroidApp.POJOs.authV3.SessionIdResponse;
import ru.gureev.MovieDbTestAndroidApp.POJOs.enities.Genre;
import ru.gureev.MovieDbTestAndroidApp.POJOs.enities.Movie;
import ru.gureev.MovieDbTestAndroidApp.POJOs.genre.GenresResponse;
import ru.gureev.MovieDbTestAndroidApp.POJOs.movie.AddFavoriteMovieRequest;
import ru.gureev.MovieDbTestAndroidApp.POJOs.movie.AddFavoriteMovieResponse;
import ru.gureev.MovieDbTestAndroidApp.POJOs.movie.MoviesResponse;
import ru.gureev.MovieDbTestAndroidApp.network.NetworkService;
import ru.gureev.MovieDbTestAndroidApp.tools.GlobalAppContextSingleton;

import static android.content.Context.MODE_PRIVATE;

public class AccountData {

    private static final String TAG = "AccountData";

    private String login;
    private String password;

    private String request_token;
    private String session_id;
    private AccountResponse account;

    private MutableLiveData<Integer> statusCode = new MutableLiveData<>();
    Callback<NewTokenResponse> newTokenResponseCallback = new Callback<NewTokenResponse>() {
        @Override
        public void onResponse(Call<NewTokenResponse> call, Response<NewTokenResponse> response) {
            if (response.body() != null) {
                Log.d(TAG, "onResponse: " + response.body().toString());
                request_token = response.body().getRequest_token();
                loadSession(login, password, request_token);
            }
        }

        @Override
        public void onFailure(Call<NewTokenResponse> call, Throwable t) {
            Log.d(TAG, "onFailure: " + call.toString());
            statusCode.setValue(AppConfig.CODE_ERROR);
        }
    };
    Callback<DeleteSessionResponse> deleteSessionResponseCallback = new Callback<DeleteSessionResponse>() {
        @Override
        public void onResponse(Call<DeleteSessionResponse> call, Response<DeleteSessionResponse> response) {
            if (response.body() != null) {
                Log.d(TAG, "onResponse: " + response.body().toString());
            }
        }

        @Override
        public void onFailure(Call<DeleteSessionResponse> call, Throwable t) {
            Log.d(TAG, "onFailure: " + call.toString());
            statusCode.setValue(AppConfig.CODE_ERROR);
        }
    };
    Callback<SessionIdResponse> sessionIdResponseCallback = new Callback<SessionIdResponse>() {
        @Override
        public void onResponse(Call<SessionIdResponse> call, Response<SessionIdResponse> response) {
            if (response.body() != null) {
                Log.d(TAG, "onResponse: " + response.code());
                session_id = (response.body().getSession_id());
                //loadAccountData(session_id);
            }
        }

        @Override
        public void onFailure(Call<SessionIdResponse> call, Throwable t) {
            Log.d(TAG, "onFailure: " + call.toString());
            statusCode.setValue(AppConfig.CODE_ERROR);
        }
    };
    Callback<NewSessionResponse> newSessionResponseCallback = new Callback<NewSessionResponse>() {
        @Override
        public void onResponse(Call<NewSessionResponse> call, Response<NewSessionResponse> response) {
            if (response.body() != null) {
                Log.d(TAG, "onResponse: !null" + response.body().toString());
                request_token = response.body().getRequest_token();
                loadSessionId(request_token);
            }
            statusCode.setValue(response.code());
        }

        @Override
        public void onFailure(Call<NewSessionResponse> call, Throwable t) {
            Log.d(TAG, "onFailure: " + call.toString());
            statusCode.setValue(AppConfig.CODE_ERROR);
        }
    };
    private MutableLiveData<List<Genre>> genreMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<AccountResponse> accountResponseMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Movie>> favoriteMoviesMutableLiveData = new MutableLiveData<>();
    Callback<MoviesResponse> moviesResponseCallback = new Callback<MoviesResponse>() {
        @Override
        public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
            if (response.body() != null) {
                Log.d(TAG, "onResponse: " + response.body().toString());
                int currentPage = response.body().getPage();
                int totalPages = response.body().getTotal_pages();

                List<Movie> tempList = favoriteMoviesMutableLiveData.getValue() != null ? favoriteMoviesMutableLiveData.getValue() : new ArrayList<Movie>();
                tempList.addAll(response.body().getResults());
                favoriteMoviesMutableLiveData.setValue(tempList);
                setGenresToFilms();

                if (currentPage + 1 <= totalPages) {
                    currentPage++;
                    loadFavoriteMovies(currentPage);
                }
            }
        }

        @Override
        public void onFailure(Call<MoviesResponse> call, Throwable t) {
            Log.d(TAG, "onFailure: " + call.toString());
            statusCode.setValue(AppConfig.CODE_ERROR);
        }
    };
    Callback<AccountResponse> accountResponseCallback = new Callback<AccountResponse>() {
        @Override
        public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
            if (response.body() != null) {
                Log.d(TAG, "onResponse: " + response.body().toString());
                account = response.body();
                accountResponseMutableLiveData.setValue(account);
                loadGenres();

            }
        }

        @Override
        public void onFailure(Call<AccountResponse> call, Throwable t) {
            Log.d(TAG, "onFailure: " + call.toString());
            statusCode.setValue(AppConfig.CODE_ERROR);
            logOut();
        }
    };
    Callback<AddFavoriteMovieResponse> addFavoriteMovieResponseCallback = new Callback<AddFavoriteMovieResponse>() {
        @Override
        public void onResponse(Call<AddFavoriteMovieResponse> call, Response<AddFavoriteMovieResponse> response) {
            if (response.body() != null) {
                Log.d(TAG, "onResponse: " + response.body().toString());
                reloadFavoriteMovies();
            }
        }

        @Override
        public void onFailure(Call<AddFavoriteMovieResponse> call, Throwable t) {
            Log.d(TAG, "onFailure: " + call.toString());
            statusCode.setValue(AppConfig.CODE_ERROR);
        }
    };

    public void loadToken(String l, String p) {
        login = l;
        password = p;
        if (!login.isEmpty() && !password.isEmpty()) {
            NetworkService.getInstance()
                    .getJsonPlaceHolderApi()
                    .getNewToken(AppConfig.API_KEY)
                    .enqueue(newTokenResponseCallback);
        }
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

    public void setSession_id(String session_id) {
        this.session_id = session_id;
        loadAccountData(session_id);
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

    public MutableLiveData<Integer> getStatusCode() {
        return statusCode;
    }

    public MutableLiveData<List<Genre>> getGenreMutableLiveData() {
        return genreMutableLiveData;
    }

    void logOut() {
        Context context = GlobalAppContextSingleton.getInstance().getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConfig.APP_PREFERENCES, MODE_PRIVATE);
        String id = sharedPreferences.getString(AppConfig.APP_PREFERENCES_NAME, "");
        Repository.getInstance().getAccountData().setSession_id(id);

        Pinkman pinkman = new Pinkman(context, AppConfig.PIN_STORAGE_NAME, new ArrayList<String>());
        pinkman.removePin();

        SharedPreferences.Editor editor = context.getSharedPreferences(AppConfig.APP_PREFERENCES, MODE_PRIVATE).edit();
        editor.remove(AppConfig.APP_PREFERENCES_NAME);
        editor.commit();

    }

    private void loadGenres() {

        Callback<GenresResponse> genresResponseCallback = new Callback<GenresResponse>() {
            @Override
            public void onResponse(Call<GenresResponse> call, Response<GenresResponse> response) {
                Log.d(TAG, "onResponse: " + response.body().toString());
                genreMutableLiveData.setValue(response.body().getGenres());
                reloadFavoriteMovies();
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

        List<Genre> genreList = genreMutableLiveData.getValue();
        List<Movie> movieList = favoriteMoviesMutableLiveData.getValue();

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
            favoriteMoviesMutableLiveData.setValue(movieList);
        }
    }
}
