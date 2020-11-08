package ru.gureev.MovieDbTestAndroidApp;

public class AppConfig {
    public static final String API_BASE_URL = "https://api.themoviedb.org/3/";
    public static final String API_BASE_URL_V4 = "https://api.themoviedb.org/4/";
    public static final String API_IMAGE_URL = "https://image.tmdb.org/t/p/w600_and_h900_bestv2";
    public static final String API_APPROVAL_URL = "https://www.themoviedb.org/auth/access?request_token=";
    public static final String REDIRECT_URL = "http://www.themoviedb.org/";
    public static final String API_GRAVATAR_URL = "https://www.gravatar.com/avatar/";
    public static final String EMPTY_MOVIE_PHOTO_URL = "https://drive.google.com/file/d/1GoX05XSLIKZbRQeElwn-DtcDkpPut-0N/view?usp=sharing";

    public static final String API_KEY = "b892ea669caf9bacfbabed3ddbae377a";
    public static final String API_KEY_V4 = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiODkyZWE2NjljYWY5YmFjZmJhYmVkM2RkYmFlMzc3YSIsInN1YiI6IjVmOTgwMjNhYzJmZjNkMDAzNzI5ZWE4MiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.bt5Q96v2szvkuVTElucTMFwdwuuE9XUBbEqs6dRQjwc";

    public static final String CONTENT_TYPE = "Content-Type: application/json;charset=utf-8";
    public static final String AUTHORIZATION = "Authorization: Bearer " + AppConfig.API_KEY_V4;

    public static final String LANG_RU = "ru-RU";
    public static final String LANG_EN = "en-US";

    public static final int CODE_SUCCESS = 200;
    public static final int CODE_INVALID_DATA = 401;
    public static final int CODE_ERROR = -2;

    public static final String PIN_STORAGE_NAME = "PinCode";
    public static final String BEARER_TOKEN_STORAGE_NAME = "bearerToken";

    public static final String APP_PREFERENCES = "SESSION";
    public static final String APP_PREFERENCES_NAME = "sessionId";
    public static final String RESET_DATA = "resetData";

    public enum PopUpFragment {
        MOVIES,
        FAVORITES
    }

    public enum PinCodeState {
        FIRST_ATTEMPT,
        SECOND_ATTEMPT
    }


}
