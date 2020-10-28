package ru.gureev.MovieDbTestAndroidApp.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.gureev.MovieDbTestAndroidApp.POJOs.requests.NewSessionIdRequest;
import ru.gureev.MovieDbTestAndroidApp.POJOs.responses.AccountResponse;
import ru.gureev.MovieDbTestAndroidApp.POJOs.requests.AddFavoriteMovieRequest;
import ru.gureev.MovieDbTestAndroidApp.POJOs.responses.AddFavoriteMovieResponse;
import ru.gureev.MovieDbTestAndroidApp.POJOs.requests.DeleteSessionRequest;
import ru.gureev.MovieDbTestAndroidApp.POJOs.responses.DeleteSessionResponse;
import ru.gureev.MovieDbTestAndroidApp.POJOs.responses.GenresResponse;
import ru.gureev.MovieDbTestAndroidApp.POJOs.responses.MoviesResponse;
import ru.gureev.MovieDbTestAndroidApp.POJOs.requests.NewSessionRequest;
import ru.gureev.MovieDbTestAndroidApp.POJOs.responses.NewSessionResponse;
import ru.gureev.MovieDbTestAndroidApp.POJOs.responses.NewTokenResponse;
import ru.gureev.MovieDbTestAndroidApp.POJOs.responses.SessionIdResponse;
import ru.gureev.MovieDbTestAndroidApp.StaticVariables;

public interface JSONPlaceHolderApi {

    @GET("authentication/token/new")
    Call<NewTokenResponse> getNewToken(@Query("api_key") String api_key);

    @POST("authentication/token/validate_with_login")
    Call<NewSessionResponse> getNewSession(@Query("api_key") String api_key,
                                           @Body NewSessionRequest newSessionRequest);

    @POST("authentication/session/new")
    Call<SessionIdResponse> getSessionId(@Query("api_key") String api_key,
                                         @Body NewSessionIdRequest request_token);

    @GET("account")
    Call<AccountResponse> getAccount(@Query("api_key") String api_key,
                                     @Query("session_id") String session_id);

    @GET("search/movie")
    Call<MoviesResponse> searchMovies(@Query("api_key") String api_key,
                                      @Query("query") String query,
                                      @Query("include_adult") boolean include_adult,
                                      @Query("page") int page);

    @GET("account/{account_id}/favorite/movies")
    Call<MoviesResponse> getFavorites(
            @Path("account_id") int account_id,
            @Query("api_key") String api_key,
            @Query("session_id") String session_id,
            @Query("language") String language,
            @Query("sort_by") String sort_by,
            @Query("page") int page);

    @GET("genre/movie/list")
    Call<GenresResponse> getGenres(
            @Query("api_key") String api_key,
            @Query("language") String language);


    @Headers(StaticVariables.CONTENT_TYPE)
    @POST("account/{account_id}/favorite")
    Call<AddFavoriteMovieResponse> setFavoritesMovie(
            @Path("account_id") int account_id,
            @Query("api_key") String api_key,
            @Query("session_id") String session_id,
            @Body AddFavoriteMovieRequest addFavoriteMovieRequest);

    @HTTP(method = "DELETE", path = "authentication/session", hasBody = true)
    Call<DeleteSessionResponse> deleteSession(@Query("api_key") String api_key,
                                              @Body DeleteSessionRequest deleteSessionRequest);

}