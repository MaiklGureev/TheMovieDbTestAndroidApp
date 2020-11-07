package ru.gureev.MovieDbTestAndroidApp.POJOs.authV3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewSessionIdRequest {
    @SerializedName("request_token")
    @Expose
    private String request_token;

    public NewSessionIdRequest(String request_token) {
        this.request_token = request_token;
    }

    public NewSessionIdRequest() {
    }

    public String getRequest_token() {
        return request_token;
    }

    public void setRequest_token(String request_token) {
        this.request_token = request_token;
    }
}
