package ru.gureev.MovieDbTestAndroidApp.POJOs.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewSessionRequest {
    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("request_token")
    @Expose
    private String request_token;

    public NewSessionRequest() {
    }

    public NewSessionRequest(String username, String password, String request_token) {
        this.username = username;
        this.password = password;
        this.request_token = request_token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRequest_token() {
        return request_token;
    }

    public void setRequest_token(String request_token) {
        this.request_token = request_token;
    }


}
