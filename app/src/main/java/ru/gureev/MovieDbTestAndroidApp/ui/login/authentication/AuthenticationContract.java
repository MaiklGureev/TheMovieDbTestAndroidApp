package ru.gureev.MovieDbTestAndroidApp.ui.login.authentication;

import android.content.Context;

public interface AuthenticationContract {

    interface View {
        void showErrorMessage(int code);

        Context getContext();

        android.view.View getView();
    }

    interface Presenter {
        void authentication(String login, String password);

        void setView(View view);
    }
}
