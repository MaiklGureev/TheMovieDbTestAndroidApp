package ru.gureev.MovieDbTestAndroidApp.POJOs.enities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

public class Movie {
    @SerializedName("poster_path")
    @Expose
    private String poster_path;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("genres")
    @Expose
    private List<Genre> genres;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("original_title")
    @Expose
    private String original_title;

    @SerializedName("title")
    @Expose
    private String title;


    @SerializedName("vote_count")
    @Expose
    private int vote_count;


    @SerializedName("vote_average")
    @Expose
    private Number vote_average;

    @SerializedName("runtime")
    @Expose
    private int runtime;

    @SerializedName("release_date")
    @Expose
    private String release_date;

    @SerializedName("genre_ids")
    @Expose
    private int[] genre_ids;

    public Movie() {
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public Number getVote_average() {
        return vote_average;
    }

    public void setVote_average(Number vote_average) {
        this.vote_average = vote_average;
    }

    public int[] getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(int[] genre_ids) {
        this.genre_ids = genre_ids;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "poster_path='" + poster_path + '\'' +
                ", overview='" + overview + '\'' +
                ", genres=" + genres +
                ", id=" + id +
                ", original_title='" + original_title + '\'' +
                ", title='" + title + '\'' +
                ", vote_count=" + vote_count +
                ", vote_average=" + vote_average +
                ", runtime=" + runtime +
                ", release_date='" + release_date + '\'' +
                ", genre_ids=" + Arrays.toString(genre_ids) +
                '}';
    }
}
