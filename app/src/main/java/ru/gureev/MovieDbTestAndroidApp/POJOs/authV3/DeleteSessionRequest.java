package ru.gureev.MovieDbTestAndroidApp.POJOs.authV3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteSessionRequest {
    @SerializedName("session_id")
    @Expose
    private String session_id;

    public DeleteSessionRequest() {
    }

    public DeleteSessionRequest(String session_id) {
        this.session_id = session_id;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }
}
