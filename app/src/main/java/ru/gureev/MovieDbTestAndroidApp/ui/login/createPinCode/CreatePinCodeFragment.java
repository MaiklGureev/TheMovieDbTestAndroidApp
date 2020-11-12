package ru.gureev.MovieDbTestAndroidApp.ui.login.createPinCode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import ru.gureev.MovieDbTestAndroidApp.AppConfig;
import ru.gureev.MovieDbTestAndroidApp.R;

public class CreatePinCodeFragment extends Fragment implements CreatePinCodeContract.View {

    private CreatePinCodeViewModel mViewModel;

    private ImageView indicator1;
    private ImageView indicator2;
    private ImageView indicator4;
    private ImageView indicator3;

    private MaterialButton button0;
    private MaterialButton button1;
    private MaterialButton button2;
    private MaterialButton button3;
    private MaterialButton button4;
    private MaterialButton button5;
    private MaterialButton button6;
    private MaterialButton button7;
    private MaterialButton button8;
    private MaterialButton button9;
    private MaterialButton buttonDelete;

    private Toolbar toolbar;
    private MaterialTextView message;
    private MaterialTextView errorMessage;
    private View.OnClickListener keyClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_0:
                case R.id.button_1:
                case R.id.button_2:
                case R.id.button_3:
                case R.id.button_4:
                case R.id.button_5:
                case R.id.button_6:
                case R.id.button_7:
                case R.id.button_8:
                case R.id.button_9: {
                    mViewModel.addNewNumberToPinCode(((MaterialButton) v).getText().toString());
                    break;
                }
                case R.id.button_delete: {
                    mViewModel.deleteNumber();
                    break;
                }

            }

        }
    };
    private View.OnClickListener goBackClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mViewModel.getPinCodeState() == AppConfig.PinCodeState.SECOND_ATTEMPT) {
                mViewModel.goToFirstAttempt();
            } else {
                Navigation.findNavController(v).popBackStack();
            }
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_pin_code, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        message = view.findViewById(R.id.pin_code_message);
        errorMessage = view.findViewById(R.id.pin_code_error_message);
        initKeypad(view);
        initIndicators(view);
        initToolbar(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new CreatePinCodeViewModel();
        mViewModel.setView(this);
        // TODO: Use the ViewModel
    }

    void initKeypad(View view) {
        button0 = view.findViewById(R.id.button_0);
        button1 = view.findViewById(R.id.button_1);
        button2 = view.findViewById(R.id.button_2);
        button3 = view.findViewById(R.id.button_3);
        button4 = view.findViewById(R.id.button_4);
        button5 = view.findViewById(R.id.button_5);
        button6 = view.findViewById(R.id.button_6);
        button7 = view.findViewById(R.id.button_7);
        button8 = view.findViewById(R.id.button_8);
        button9 = view.findViewById(R.id.button_9);
        buttonDelete = view.findViewById(R.id.button_delete);

        button0.setOnClickListener(keyClickListener);
        button1.setOnClickListener(keyClickListener);
        button2.setOnClickListener(keyClickListener);
        button3.setOnClickListener(keyClickListener);
        button4.setOnClickListener(keyClickListener);
        button5.setOnClickListener(keyClickListener);
        button6.setOnClickListener(keyClickListener);
        button7.setOnClickListener(keyClickListener);
        button8.setOnClickListener(keyClickListener);
        button9.setOnClickListener(keyClickListener);
        buttonDelete.setOnClickListener(keyClickListener);
    }

    void initIndicators(View view) {
        indicator1 = view.findViewById(R.id.indicator1);
        indicator2 = view.findViewById(R.id.indicator2);
        indicator3 = view.findViewById(R.id.indicator3);
        indicator4 = view.findViewById(R.id.indicator4);
    }

    @Override
    public void updateMessage(int resId) {
        message.setText(resId);
    }

    @Override
    public void showErrorMessage(boolean value) {
        if (value) {
            errorMessage.setVisibility(View.VISIBLE);
        } else {
            errorMessage.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void setIndicatorsValue(int value) {
        switch (value) {
            case 0: {
                indicator1.setImageResource(R.drawable.ic_indicator_unselected_24dp);
                indicator2.setImageResource(R.drawable.ic_indicator_unselected_24dp);
                indicator3.setImageResource(R.drawable.ic_indicator_unselected_24dp);
                indicator4.setImageResource(R.drawable.ic_indicator_unselected_24dp);
                break;
            }
            case 1: {
                indicator1.setImageResource(R.drawable.ic_indicator_selected_24dp);
                indicator2.setImageResource(R.drawable.ic_indicator_unselected_24dp);
                indicator3.setImageResource(R.drawable.ic_indicator_unselected_24dp);
                indicator4.setImageResource(R.drawable.ic_indicator_unselected_24dp);
                break;
            }
            case 2: {
                indicator1.setImageResource(R.drawable.ic_indicator_selected_24dp);
                indicator2.setImageResource(R.drawable.ic_indicator_selected_24dp);
                indicator3.setImageResource(R.drawable.ic_indicator_unselected_24dp);
                indicator4.setImageResource(R.drawable.ic_indicator_unselected_24dp);
                break;
            }
            case 3: {
                indicator1.setImageResource(R.drawable.ic_indicator_selected_24dp);
                indicator2.setImageResource(R.drawable.ic_indicator_selected_24dp);
                indicator3.setImageResource(R.drawable.ic_indicator_selected_24dp);
                indicator4.setImageResource(R.drawable.ic_indicator_unselected_24dp);
                break;
            }
            case 4: {
                indicator1.setImageResource(R.drawable.ic_indicator_selected_24dp);
                indicator2.setImageResource(R.drawable.ic_indicator_selected_24dp);
                indicator3.setImageResource(R.drawable.ic_indicator_selected_24dp);
                indicator4.setImageResource(R.drawable.ic_indicator_selected_24dp);
                break;
            }
            case -1: {
                indicator1.setImageResource(R.drawable.ic_indicator_error_24dp);
                indicator2.setImageResource(R.drawable.ic_indicator_error_24dp);
                indicator3.setImageResource(R.drawable.ic_indicator_error_24dp);
                indicator4.setImageResource(R.drawable.ic_indicator_error_24dp);
                break;
            }
        }
    }

    void initToolbar(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_24dp);
        toolbar.setNavigationOnClickListener(goBackClickListener);
    }

}
