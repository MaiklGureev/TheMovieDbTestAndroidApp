package ru.gureev.MovieDbTestAndroidApp;

public class AppConfig {
    public static final String API_BASE_URL = "https://api.themoviedb.org/3/";
    public static final String API_IMAGE_URL = "https://image.tmdb.org/t/p/w600_and_h900_bestv2";
    public static final String API_GRAVATAR_URL = "https://www.gravatar.com/avatar/";
    public static final String EMPTY_MOVIE_PHOTO_URL = "https://drive.google.com/file/d/1GoX05XSLIKZbRQeElwn-DtcDkpPut-0N/view?usp=sharing";
    public static final String API_KEY = "b892ea669caf9bacfbabed3ddbae377a";
    public static final String CONTENT_TYPE = "Content-Type: application/json;charset=utf-8";

    public static final String LANG_RU = "ru-RU";
    public static final String LANG_EN = "en-US";

    public static final String MOVIE_FRAGMENT = "MOVIES";
    public static final String SHOW_MOVIE_DETAILS_FRAGMENT = "SHOW_MOVIE_DETAILS";

    public enum  PopUpFragment{
        MOVIES,
        FAVORITES
    }

}
