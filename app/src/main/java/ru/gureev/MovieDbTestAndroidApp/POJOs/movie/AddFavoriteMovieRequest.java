package ru.gureev.MovieDbTestAndroidApp.POJOs.movie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddFavoriteMovieRequest {

    @SerializedName("media_type")
    @Expose
    private String media_type;

    @SerializedName("media_id")
    @Expose
    private int media_id;

    @SerializedName("favorite")
    @Expose
    private boolean favorite;

    public AddFavoriteMovieRequest() {
    }

    public AddFavoriteMovieRequest(String media_type, int media_id, boolean favorite) {
        this.media_type = media_type;
        this.media_id = media_id;
        this.favorite = favorite;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public int getMedia_id() {
        return media_id;
    }

    public void setMedia_id(int media_id) {
        this.media_id = media_id;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
