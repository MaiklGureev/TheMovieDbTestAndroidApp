package ru.gureev.MovieDbTestAndroidApp.POJOs.authV3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewTokenResponse {
    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("expires_at")
    @Expose
    private String expires_at;
    @SerializedName("request_token")
    @Expose
    private String request_token;

    public NewTokenResponse() {
    }

    public NewTokenResponse(boolean success, String expires_at, String request_token) {
        this.success = success;
        this.expires_at = expires_at;
        this.request_token = request_token;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(String expires_at) {
        this.expires_at = expires_at;
    }

    public String getRequest_token() {
        return request_token;
    }

    public void setRequest_token(String request_token) {
        this.request_token = request_token;
    }

    @Override
    public String toString() {
        return "NewTokenResponse{" +
                "success=" + success +
                ", expires_at='" + expires_at + '\'' +
                ", request_token='" + request_token + '\'' +
                '}';
    }
}
