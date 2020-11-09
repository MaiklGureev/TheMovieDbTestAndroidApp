package ru.gureev.MovieDbTestAndroidApp.ui.login.createPinCode;

import android.content.Context;

import ru.gureev.MovieDbTestAndroidApp.AppConfig;

public interface CreatePinCodeContract {
    interface View {
        void updateMessage(int resId);

        void showErrorMessage(boolean value);

        void setIndicatorsValue(int value);

        Context getContext();
    }

    interface Presenter {
        void savePinCodeAndSession();

        AppConfig.PinCodeState getPinCodeState();

        void goToFirstAttempt();

        void setView(View view);

        void addNewNumberToPinCode(String number);

        void deleteNumber();
    }
}
