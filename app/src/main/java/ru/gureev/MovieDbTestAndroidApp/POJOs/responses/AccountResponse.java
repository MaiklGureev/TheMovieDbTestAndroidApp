package ru.gureev.MovieDbTestAndroidApp.POJOs.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ru.gureev.MovieDbTestAndroidApp.POJOs.enities.Avatar;

public class AccountResponse {

    @SerializedName("avatar")
    @Expose
    private Avatar avatar;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("iso_639_1")
    @Expose
    private String iso_639_1;

    @SerializedName("iso_3166_1")
    @Expose
    private String iso_3166_1;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("include_adult")
    @Expose
    private boolean include_adult;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("status_message")
    @Expose
    private String status_message;

    @SerializedName("status_code")
    @Expose
    private int status_code;

    public AccountResponse() {
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isInclude_adult() {
        return include_adult;
    }

    public void setInclude_adult(boolean include_adult) {
        this.include_adult = include_adult;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        return "AccountResponse{" +
                "avatar=" + avatar +
                ", id=" + id +
                ", iso_639_1='" + iso_639_1 + '\'' +
                ", iso_3166_1='" + iso_3166_1 + '\'' +
                ", name='" + name + '\'' +
                ", include_adult=" + include_adult +
                ", username='" + username + '\'' +
                ", status_message='" + status_message + '\'' +
                ", status_code=" + status_code +
                '}';
    }
}
