package ru.gureev.MovieDbTestAndroidApp.POJOs.enities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Gravatar {
    @SerializedName("hash")
    @Expose
    private String hash;

    public Gravatar() {
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return "Gravatar{" +
                "hash='" + hash + '\'' +
                '}';
    }
}
