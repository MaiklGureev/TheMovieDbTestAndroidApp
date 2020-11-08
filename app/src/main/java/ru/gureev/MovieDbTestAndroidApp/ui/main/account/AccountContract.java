package ru.gureev.MovieDbTestAndroidApp.ui.main.account;

import android.content.Context;

import com.google.android.material.imageview.ShapeableImageView;

public interface AccountContract {

    interface View {
        void setAccountInfo(String name, String userName);
        ShapeableImageView getImageView();
        Context getContext();
    }

    interface Presenter {
        void loadAccount();
        void exit();
    }

}
