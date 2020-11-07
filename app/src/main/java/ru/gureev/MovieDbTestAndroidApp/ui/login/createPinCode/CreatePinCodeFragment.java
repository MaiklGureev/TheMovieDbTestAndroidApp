package ru.gureev.MovieDbTestAndroidApp.ui.login.createPinCode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import ru.gureev.MovieDbTestAndroidApp.R;

public class CreatePinCodeFragment extends Fragment {

    private CreatePinCodeViewModel mViewModel;

    public static CreatePinCodeFragment newInstance() {
        return new CreatePinCodeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pin_code_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CreatePinCodeViewModel.class);
        // TODO: Use the ViewModel
    }

}
