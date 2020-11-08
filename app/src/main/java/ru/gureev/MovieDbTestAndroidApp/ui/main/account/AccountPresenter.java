package ru.gureev.MovieDbTestAndroidApp.ui.main.account;

import android.content.SharedPreferences;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.redmadrobot.pinkman.Pinkman;

import java.util.ArrayList;

import ru.gureev.MovieDbTestAndroidApp.AppConfig;
import ru.gureev.MovieDbTestAndroidApp.POJOs.authV3.AccountResponse;
import ru.gureev.MovieDbTestAndroidApp.repository.Repository;
import ru.gureev.MovieDbTestAndroidApp.tools.Utils;

import static android.content.Context.MODE_PRIVATE;

public class AccountPresenter extends ViewModel implements AccountContract.Presenter {

    private AccountContract.View view;

    public AccountPresenter() {
    }

    public void setView(AccountContract.View view) {
        this.view = view;
    }

    @Override
    public void loadAccount() {
        Repository.getInstance()
                .getAccountData()
                .getAccountResponseMutableLiveData()
                .observe((LifecycleOwner) view, new Observer<AccountResponse>() {
                    @Override
                    public void onChanged(AccountResponse accountResponse) {
                        String avatarHash = accountResponse.getAvatar().getGravatar().getHash();
                        String name = accountResponse.getName();
                        String userName = accountResponse.getUsername();
                        view.setAccountInfo(name, userName);
                        Utils.loadPicture(view.getImageView(), AppConfig.API_GRAVATAR_URL + avatarHash);
                    }
                });

    }

    @Override
    public void exit() {
        Repository.getInstance().getAccountData().deleteSession();
        Pinkman pinkman = new Pinkman(view.getContext().getApplicationContext(), AppConfig.PIN_STORAGE_NAME, new ArrayList<String>());
        pinkman.removePin();

        SharedPreferences.Editor editor = view.getContext().getApplicationContext().getSharedPreferences(AppConfig.APP_PREFERENCES, MODE_PRIVATE).edit();
        editor.remove(AppConfig.APP_PREFERENCES_NAME);
        editor.commit();

        System.exit(0);
    }
}