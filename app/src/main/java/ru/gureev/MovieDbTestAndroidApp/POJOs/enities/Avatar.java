package ru.gureev.MovieDbTestAndroidApp.POJOs.enities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Avatar {
    @SerializedName("gravatar")
    @Expose
    private Gravatar gravatar;

    public Avatar() {
    }

    public Gravatar getGravatar() {
        return gravatar;
    }

    public void setGravatar(Gravatar gravatar) {
        this.gravatar = gravatar;
    }

    @Override
    public String toString() {
        return "Avatar{" +
                "gravatar=" + gravatar +
                '}';
    }
}
