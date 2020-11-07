package ru.gureev.MovieDbTestAndroidApp.ui.login.authentication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import ru.gureev.MovieDbTestAndroidApp.AppConfig;
import ru.gureev.MovieDbTestAndroidApp.R;

public class AuthenticationFragment extends Fragment implements AuthenticationContract.View {

    private AuthenticationViewModel mViewModel;

    private MaterialButton exitMaterialButton;
    private TextInputEditText loginInputEditText;
    private TextInputEditText passwordInputEditText;
    private MaterialTextView errorMessageTextView;

    public static AuthenticationFragment newInstance() {
        return new AuthenticationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.authentication_fragment, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AuthenticationViewModel.class);
        mViewModel.setView(this);

        exitMaterialButton = getView().findViewById(R.id.exit_button);
        loginInputEditText = getView().findViewById(R.id.login_input_edit_text);
        passwordInputEditText = getView().findViewById(R.id.password_input_edit_text);
        errorMessageTextView = getView().findViewById(R.id.error_message_material_text_view);

        exitMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.authentication(
                        loginInputEditText.getText().toString(),
                        passwordInputEditText.getText().toString());

            }
        });

    }

    @Override
    public void loadNextPage() {
        Bundle bundle = new Bundle();
        NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.authenticationFragment, false).build();
        Navigation.findNavController(getView()).navigate(R.id.createPinCodeFragment, bundle, navOptions);
    }

    @Override
    public void showErrorMessage(int code) {
        if (code == AppConfig.CODE_SUCCESS) {
            errorMessageTextView.setVisibility(View.INVISIBLE);
        } else if (code == AppConfig.CODE_INVALID_DATA) {
            errorMessageTextView.setText(R.string.incorrect_login_and_password);
            errorMessageTextView.setVisibility(View.VISIBLE);
        } else if (code == AppConfig.CODE_ERROR) {
            errorMessageTextView.setText(R.string.something_went_wrong);
            errorMessageTextView.setVisibility(View.VISIBLE);
        }
    }
}
