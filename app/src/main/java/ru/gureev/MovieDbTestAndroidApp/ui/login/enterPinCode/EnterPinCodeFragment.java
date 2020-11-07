package ru.gureev.MovieDbTestAndroidApp.ui.login.enterPinCode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import ru.gureev.MovieDbTestAndroidApp.R;

public class EnterPinCodeFragment extends Fragment {

    private EnterPinCodeViewModel mViewModel;

    public static EnterPinCodeFragment newInstance() {
        return new EnterPinCodeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.enter_pin_code_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EnterPinCodeViewModel.class);
        // TODO: Use the ViewModel
    }

}
