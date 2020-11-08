package ru.gureev.MovieDbTestAndroidApp.ui.login.enterPinCode;

import android.content.Context;

import com.google.android.material.imageview.ShapeableImageView;

public interface EnterPinCodeContract {
    interface View {
        void setUserName(String name);

        ShapeableImageView getUserImageView();

        void showErrorMessage(boolean value);

        void setIndicatorsValue(int value);

        Context getContext();
    }

    interface Presenter {
        void setView(EnterPinCodeContract.View view);

        void addNewNumberToPinCode(String number);

        void deleteNumber();

        void exit();
    }
}
