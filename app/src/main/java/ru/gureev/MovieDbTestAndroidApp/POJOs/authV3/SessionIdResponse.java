package ru.gureev.MovieDbTestAndroidApp.POJOs.authV3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SessionIdResponse {
    @SerializedName("status_code")
    @Expose
    private int status_code;

    @SerializedName("status_message")
    @Expose
    private String status_message;

    @SerializedName("session_id")
    @Expose
    private String session_id;

    @SerializedName("success")
    @Expose
    private boolean success;

    public SessionIdResponse() {
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

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "SessionIdResponse{" +
                "status_code=" + status_code +
                ", status_message='" + status_message + '\'' +
                ", session_id='" + session_id + '\'' +
                ", success=" + success +
                '}';
    }
}
