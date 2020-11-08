package ru.gureev.MovieDbTestAndroidApp.ui.login.authentication;

import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import ru.gureev.MovieDbTestAndroidApp.AppConfig;
import ru.gureev.MovieDbTestAndroidApp.R;
import ru.gureev.MovieDbTestAndroidApp.repository.Repository;

public class AuthenticationViewModel extends ViewModel implements AuthenticationContract.Presenter {

    private static final String TAG = "AuthenticationViewModel";
    private AuthenticationContract.View view;
    private MutableLiveData<Integer> statusCode = new MutableLiveData<>();

    @Override
    public void authentication(final String login, final String password) {

        Repository.getInstance().getAccountData().loadToken(login, password);
        Repository.getInstance().getAccountData().getStatusCode().observe((LifecycleOwner) view, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Log.d(TAG, "onChanged: code=" + integer);
                if (integer == AppConfig.CODE_SUCCESS) {
                    loadNextPage();
                    Repository.getInstance().getAccountData().getStatusCode().setValue(0);
                }
                view.showErrorMessage(integer);
            }
        });
    }

    @Override
    public void setView(AuthenticationContract.View view) {
        this.view = view;
    }

    public void loadNextPage() {
        Bundle bundle = new Bundle();
        NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.authenticationFragment, false).build();
        Navigation.findNavController(view.getView()).navigate(R.id.createPinCodeFragment, bundle, navOptions);
    }

}
