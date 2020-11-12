package ru.gureev.MovieDbTestAndroidApp.ui.login.enterPinCode;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.redmadrobot.pinkman.Pinkman;

import java.util.ArrayList;

import ru.gureev.MovieDbTestAndroidApp.AppConfig;
import ru.gureev.MovieDbTestAndroidApp.MainActivity;
import ru.gureev.MovieDbTestAndroidApp.POJOs.authV3.AccountResponse;
import ru.gureev.MovieDbTestAndroidApp.repository.Repository;
import ru.gureev.MovieDbTestAndroidApp.tools.Utils;

import static android.content.Context.MODE_PRIVATE;

public class EnterPinCodeViewModel implements EnterPinCodeContract.Presenter {

    private EnterPinCodeContract.View view;
    private StringBuilder tempPinCode = new StringBuilder();
    private Pinkman pinkman;

    public void init() {

        pinkman = new Pinkman(
                view.getContext().getApplicationContext(),
                AppConfig.PIN_STORAGE_NAME,
                new ArrayList<String>());

        SharedPreferences sharedPreferences = view
                .getContext()
                .getApplicationContext()
                .getSharedPreferences(AppConfig.APP_PREFERENCES, MODE_PRIVATE);

        Repository.getInstance()
                .getAccountData()
                .loadAccountData(sharedPreferences.getString(AppConfig.APP_PREFERENCES_NAME, ""));

        Repository.getInstance()
                .getAccountData()
                .getAccountResponseMutableLiveData().observe((LifecycleOwner) view, new Observer<AccountResponse>() {
            @Override
            public void onChanged(AccountResponse accountResponse) {
                view.setUserName(accountResponse.getName());
                Utils.loadPicture(view.getUserImageView(), AppConfig.API_GRAVATAR_URL + accountResponse.getAvatar().getGravatar().getHash());
            }
        });
    }

    @Override
    public void setView(EnterPinCodeContract.View view) {
        this.view = view;
        init();
    }

    @Override
    public void addNewNumberToPinCode(String number) {
        if (tempPinCode.length() < 4) {
            tempPinCode.append(number);
            view.setIndicatorsValue(tempPinCode.length());
        }
        if (tempPinCode.length() == 4) {
            if (pinkman.isValidPin(tempPinCode.toString())) {
                loadNextActivity();
            } else {
                view.showErrorMessage(true);
                view.setIndicatorsValue(-1);
            }
        }

    }

    @Override
    public void deleteNumber() {
        if (tempPinCode.length() == 4) {
            view.showErrorMessage(false);
        }
        if (tempPinCode.length() != 0) {
            tempPinCode.delete(tempPinCode.length() - 1, tempPinCode.length());
            view.setIndicatorsValue(tempPinCode.length());
        }
    }

    @Override
    public void exit() {
        System.exit(0);
    }

    private void loadNextActivity() {
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Intent intent = new Intent(view.getContext().getApplicationContext(), MainActivity.class);
                intent.putExtra(AppConfig.RESET_DATA, true);
                intent.putExtra(AppConfig.PIN_STORAGE_NAME, tempPinCode.toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                view.getContext().getApplicationContext().startActivity(intent);
                return null;
            }
        };
        asyncTask.execute();
    }
}
