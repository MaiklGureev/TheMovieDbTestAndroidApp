package ru.gureev.MovieDbTestAndroidApp.ui.login.createPinCode;

import android.content.Intent;
import android.content.SharedPreferences;

import androidx.lifecycle.ViewModel;

import com.redmadrobot.pinkman.Pinkman;

import java.util.ArrayList;

import ru.gureev.MovieDbTestAndroidApp.AppConfig;
import ru.gureev.MovieDbTestAndroidApp.MainActivity;
import ru.gureev.MovieDbTestAndroidApp.R;
import ru.gureev.MovieDbTestAndroidApp.repository.Repository;

import static android.content.Context.MODE_PRIVATE;

public class CreatePinCodeViewModel extends ViewModel implements CreatePinCodeContract.Presenter {

    private CreatePinCodeContract.View view;
    private StringBuilder tempPinCode = new StringBuilder();
    private StringBuilder tempPinCodeRepeat = new StringBuilder();
    private AppConfig.PinCodeState pinCodeState = AppConfig.PinCodeState.FIRST_ATTEMPT;

    @Override
    public AppConfig.PinCodeState getPinCodeState() {
        return pinCodeState;
    }

    @Override
    public void setView(CreatePinCodeContract.View view) {
        this.view = view;
    }

    @Override
    public void addNewNumberToPinCode(String number) {
        switch (pinCodeState) {
            case FIRST_ATTEMPT: {
                if (tempPinCode.length() < 4) {
                    tempPinCode.append(number);
                    view.setIndicatorsValue(tempPinCode.length());
                }
                if (tempPinCode.length() == 4) {
                    goToSecondAttempt();
                }
                break;
            }
            case SECOND_ATTEMPT: {
                if (tempPinCodeRepeat.length() < 4) {
                    tempPinCodeRepeat.append(number);
                    view.setIndicatorsValue(tempPinCodeRepeat.length());
                }
                if (tempPinCode.length() == tempPinCodeRepeat.length()) {
                    if (tempPinCodeRepeat.toString().equals(tempPinCode.toString())) {
                        loadNextActivity();
                    } else {
                        view.showErrorMessage(true);
                        view.setIndicatorsValue(-1);
                    }
                }
                break;
            }
        }

    }

    @Override
    public void deleteNumber() {

        switch (pinCodeState) {
            case FIRST_ATTEMPT: {
                if (tempPinCode.length() != 0) {
                    tempPinCode.delete(tempPinCode.length() - 1, tempPinCode.length());
                    view.setIndicatorsValue(tempPinCode.length());
                }
                break;
            }
            case SECOND_ATTEMPT: {
                if (tempPinCodeRepeat.length() != 0) {
                    view.showErrorMessage(false);
                    tempPinCodeRepeat.delete(tempPinCodeRepeat.length() - 1, tempPinCodeRepeat.length());
                    view.setIndicatorsValue(tempPinCodeRepeat.length());

                } else if (tempPinCodeRepeat.length() == 0) {
                    goToFirstAttempt();
                }
                break;

            }
        }


    }

    @Override
    public void goToFirstAttempt() {
        pinCodeState = AppConfig.PinCodeState.FIRST_ATTEMPT;
        view.setIndicatorsValue(0);
        view.showErrorMessage(false);
        tempPinCode.delete(0, tempPinCode.length());
        tempPinCodeRepeat.delete(0, tempPinCodeRepeat.length());
        view.updateMessage(R.string.come_up_with_a_pin_code);
    }

    private void goToSecondAttempt() {
        pinCodeState = AppConfig.PinCodeState.SECOND_ATTEMPT;
        view.setIndicatorsValue(0);
        view.updateMessage(R.string.repeat_pin_code);
    }

    @Override
    public void savePinCodeAndSession() {
        Pinkman pinkman = new Pinkman(view.getContext().getApplicationContext(), AppConfig.PIN_STORAGE_NAME, new ArrayList<String>());
        pinkman.createPin(tempPinCode.toString(), true);

        SharedPreferences.Editor editor = view.getContext().getApplicationContext().getSharedPreferences(AppConfig.APP_PREFERENCES, MODE_PRIVATE).edit();
        editor.putString(AppConfig.APP_PREFERENCES_NAME, Repository.getInstance().getAccountData().getSession_id());
        editor.commit();
    }

    private void loadNextActivity() {
        savePinCodeAndSession();
        Intent intent = new Intent(view.getContext().getApplicationContext(), MainActivity.class);
        intent.putExtra(AppConfig.RESET_DATA, false);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        view.getContext().getApplicationContext().startActivity(intent);

    }

}
