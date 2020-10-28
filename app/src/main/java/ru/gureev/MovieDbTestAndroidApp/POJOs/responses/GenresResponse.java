package ru.gureev.MovieDbTestAndroidApp.POJOs.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.gureev.MovieDbTestAndroidApp.POJOs.enities.Genre;

public class GenresResponse {

    @SerializedName("genres")
    @Expose
    private List<Genre> genres;

    @SerializedName("status_message")
    @Expose
    private String status_message;

    @SerializedName("status_code")
    @Expose
    private int status_code;


    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    @Override
    public String toString() {
        return "GenresResponse{" +
                "genres=" + genres +
                ", status_message='" + status_message + '\'' +
                ", status_code=" + status_code +
                '}';
    }
}
