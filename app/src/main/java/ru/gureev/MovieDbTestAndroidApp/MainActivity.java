package ru.gureev.MovieDbTestAndroidApp;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.gureev.MovieDbTestAndroidApp.POJOs.requests.NewSessionIdRequest;
import ru.gureev.MovieDbTestAndroidApp.POJOs.responses.AccountResponse;
import ru.gureev.MovieDbTestAndroidApp.POJOs.responses.AddFavoriteMovieResponse;
import ru.gureev.MovieDbTestAndroidApp.POJOs.requests.DeleteSessionRequest;
import ru.gureev.MovieDbTestAndroidApp.POJOs.responses.DeleteSessionResponse;
import ru.gureev.MovieDbTestAndroidApp.POJOs.responses.GenresResponse;
import ru.gureev.MovieDbTestAndroidApp.POJOs.responses.MoviesResponse;
import ru.gureev.MovieDbTestAndroidApp.POJOs.requests.NewSessionRequest;
import ru.gureev.MovieDbTestAndroidApp.POJOs.responses.NewSessionResponse;
import ru.gureev.MovieDbTestAndroidApp.POJOs.responses.NewTokenResponse;
import ru.gureev.MovieDbTestAndroidApp.POJOs.responses.SessionIdResponse;
import ru.gureev.MovieDbTestAndroidApp.network.NetworkService;

public class MainActivity extends AppCompatActivity {

    String request_token;
    String session_id;
    int account_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_movies, R.id.navigation_favorites, R.id.navigation_account)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        final Callback<GenresResponse> genresResponseCallback =  new Callback<GenresResponse>() {
            @Override
            public void onResponse(Call<GenresResponse> call, Response<GenresResponse> response) {
                Log.d("NetworkService", "onResponse: " + response.body().toString());
            }

            @Override
            public void onFailure(Call<GenresResponse> call, Throwable t) {
                Log.d("NetworkService", "onFailure: " + call.toString());

            }
        };

        final Callback<MoviesResponse> moviesResponseCallback = new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                Log.d("NetworkService", "onResponse: " + response.body().toString());
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.d("NetworkService", "onFailure: " + call.toString());
            }
        };

        final Callback<AddFavoriteMovieResponse> addFavoriteMovieResponseCallback = new Callback<AddFavoriteMovieResponse>() {
            @Override
            public void onResponse(Call<AddFavoriteMovieResponse> call, Response<AddFavoriteMovieResponse> response) {
                Log.d("NetworkService", "onResponse: " + response.body().toString());
            }

            @Override
            public void onFailure(Call<AddFavoriteMovieResponse> call, Throwable t) {
                Log.d("NetworkService", "onFailure: " + call.toString());
            }
        };

        final Callback<AccountResponse> accountResponseCallback = new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                Log.d("NetworkService", "onResponse: " + response.body().toString());

                account_id = response.body().getId();

//                NetworkService.getInstance()
//                        .getJsonPlaceHolderApi()
//                        .setFavoritesMovie(account_id,StaticVariables.API_KEY, session_id,  new AddFavoriteMovieRequest("movie",200,true))
//                        .enqueue(addFavoriteMovieResponseCallback);
                NetworkService.getInstance()
                        .getJsonPlaceHolderApi()
                        .getFavorites(account_id, StaticVariables.API_KEY, session_id, StaticVariables.LANG_RU, null, 1)
                        .enqueue(moviesResponseCallback);

            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                Log.d("NetworkService", "onFailure: " + call.toString());
            }
        };

        final Callback<SessionIdResponse> sessionIdResponseCallback = new Callback<SessionIdResponse>() {
            @Override
            public void onResponse(Call<SessionIdResponse> call, Response<SessionIdResponse> response) {
                Log.d("NetworkService", "onResponse: " + response.body().toString());
                session_id = response.body().getSession_id();

                NetworkService.getInstance()
                        .getJsonPlaceHolderApi()
                        .getAccount(StaticVariables.API_KEY, session_id)
                        .enqueue(accountResponseCallback);

                NetworkService.getInstance()
                        .getJsonPlaceHolderApi()
                        .deleteSession(StaticVariables.API_KEY, new DeleteSessionRequest(session_id))
                        .enqueue(new Callback<DeleteSessionResponse>() {
                            @Override
                            public void onResponse(Call<DeleteSessionResponse> call, Response<DeleteSessionResponse> response) {
                                Log.d("NetworkService", "onResponse: " + response.body().toString());
                            }

                            @Override
                            public void onFailure(Call<DeleteSessionResponse> call, Throwable t) {
                                Log.d("NetworkService", "onFailure: " + call.toString());
                            }
                        });
            }

            @Override
            public void onFailure(Call<SessionIdResponse> call, Throwable t) {
                Log.d("NetworkService", "onFailure: " + call.toString());
            }
        };



        final Callback<NewSessionResponse> newSessionResponseCallback = new Callback<NewSessionResponse>() {
            @Override
            public void onResponse(Call<NewSessionResponse> call, Response<NewSessionResponse> response) {
                Log.d("NetworkService", "onResponse: " + response.body().toString());
                request_token = response.body().getRequest_token();

                NetworkService.getInstance()
                        .getJsonPlaceHolderApi()
                        .getSessionId(StaticVariables.API_KEY, new NewSessionIdRequest(request_token))
                        .enqueue(sessionIdResponseCallback);

                NetworkService.getInstance()
                        .getJsonPlaceHolderApi()
                        .getGenres(StaticVariables.API_KEY, StaticVariables.LANG_RU)
                        .enqueue(genresResponseCallback);


                NetworkService.getInstance()
                        .getJsonPlaceHolderApi()
                        .searchMovies(StaticVariables.API_KEY,"spider man",true,3)
                        .enqueue(moviesResponseCallback);

            }

            @Override
            public void onFailure(Call<NewSessionResponse> call, Throwable t) {
                Log.d("NetworkService", "onFailure: " + call.toString());
            }
        };


        Callback<NewTokenResponse> newTokenResponseCallback = new Callback<NewTokenResponse>() {
            @Override
            public void onResponse(Call<NewTokenResponse> call, Response<NewTokenResponse> response) {

                request_token = response.body().getRequest_token();
                Log.d("NetworkService", "onResponse: " + response.body().toString());

                NetworkService.getInstance()
                        .getJsonPlaceHolderApi()
                        .getNewSession(StaticVariables.API_KEY, new NewSessionRequest("MikhailGureev", "0000", request_token))
                        .enqueue(newSessionResponseCallback);


            }

            @Override
            public void onFailure(Call<NewTokenResponse> call, Throwable t) {
                Log.d("NetworkService", "onFailure: " + call.toString());
            }
        };


//        NetworkService.getInstance()
//                .getJsonPlaceHolderApi()
//                .getNewToken(StaticVariables.API_KEY)
//                .enqueue(newTokenResponseCallback);


    }

}
