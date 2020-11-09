package ru.gureev.MovieDbTestAndroidApp.ui.login.authentication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

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
        return inflater.inflate(R.layout.fragment_authentication, container, false);
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
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (loginInputEditText.length() != 0 && passwordInputEditText.length() != 0) {
                    Log.d("afterTextChanged", "afterTextChanged: true");
                    exitMaterialButton.setRippleColorResource(R.color.selectedText);
                    exitMaterialButton.setBackgroundResource(R.color.selectedButton);
                } else {
                    exitMaterialButton.setRippleColorResource(R.color.unselectedText);
                    exitMaterialButton.setBackgroundResource(R.color.unselectedButton);
                    Log.d("afterTextChanged", "afterTextChanged: false");
                }

            }
        };
        loginInputEditText.addTextChangedListener(textWatcher);
        passwordInputEditText.addTextChangedListener(textWatcher);
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
