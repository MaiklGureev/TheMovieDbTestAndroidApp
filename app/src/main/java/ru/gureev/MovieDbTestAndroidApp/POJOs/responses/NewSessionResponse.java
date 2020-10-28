package ru.gureev.MovieDbTestAndroidApp.POJOs.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewSessionResponse {
    @SerializedName("status_code")
    @Expose
    private int status_code;

    @SerializedName("status_message")
    @Expose
    private String status_message;

    @SerializedName("request_token")
    @Expose
    private String request_token;

    @SerializedName("expires_at")
    @Expose
    private String expires_at;

    @SerializedName("success")
    @Expose
    private boolean success;

    public NewSessionResponse() {
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

    public String getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(String expires_at) {
        this.expires_at = expires_at;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getRequest_token() {
        return request_token;
    }

    public void setRequest_token(String request_token) {
        this.request_token = request_token;
    }

    @Override
    public String toString() {
        return "NewSessionResponse{" +
                "status_code=" + status_code +
                ", status_message='" + status_message + '\'' +
                ", request_token='" + request_token + '\'' +
                ", expires_at='" + expires_at + '\'' +
                ", success=" + success +
                '}';
    }
}
