package ru.gureev.MovieDbTestAndroidApp.ui.account;

import android.content.Context;

import com.google.android.material.imageview.ShapeableImageView;

public interface AccountContract {

    interface View{
        void setAccountInfo(String name, String userName);
        ShapeableImageView getImageView();
    }

    interface Presenter{
        void loadAccount();
        void exit();
    }

}
