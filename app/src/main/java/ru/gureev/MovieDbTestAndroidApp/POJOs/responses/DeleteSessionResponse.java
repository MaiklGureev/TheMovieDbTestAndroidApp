package ru.gureev.MovieDbTestAndroidApp.POJOs.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteSessionResponse {
    @SerializedName("status_code")
    @Expose
    private int status_code;

    @SerializedName("status_message")
    @Expose
    private String status_message;

    @SerializedName("success")
    @Expose
    private boolean success;

    public DeleteSessionResponse() {
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "DeleteSessionResponse{" +
                "status_code=" + status_code +
                ", status_message='" + status_message + '\'' +
                ", success=" + success +
                '}';
    }
}
