package ru.gureev.MovieDbTestAndroidApp;

public class AppConfig {
    public static final String API_BASE_URL = "https://api.themoviedb.org/3/";
    public static final String API_IMAGE_URL = "https://image.tmdb.org/t/p/w600_and_h900_bestv2";
    public static final String API_GRAVATAR_URL = "https://www.gravatar.com/avatar/";

    public static final String API_KEY = "b892ea669caf9bacfbabed3ddbae377a";

    public static final String CONTENT_TYPE = "Content-Type: application/json;charset=utf-8";

    public static final String LANG_RU = "ru-RU";
    public static final String LANG_EN = "en-US";

    public static final int CODE_SUCCESS = 200;
    public static final int CODE_INVALID_DATA = 401;
    public static final int CODE_ERROR = -2;

    public static final String PIN_STORAGE_NAME = "PinCode";

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
