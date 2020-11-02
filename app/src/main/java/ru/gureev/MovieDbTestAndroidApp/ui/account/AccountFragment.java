package ru.gureev.MovieDbTestAndroidApp.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import ru.gureev.MovieDbTestAndroidApp.R;

public class AccountFragment extends Fragment implements AccountContract.View {

    private AccountPresenter accountPresenter;
    private ShapeableImageView userPhotoImageView;
    private MaterialTextView nameTextView;
    private MaterialTextView userNameTextView;
    private MaterialButton exitButton;

    View.OnClickListener exitButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            accountPresenter.exit();
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        accountPresenter =
                ViewModelProviders.of(this).get(AccountPresenter.class);
        View root = inflater.inflate(R.layout.fragment_account, container, false);
        accountPresenter.setView(this);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userPhotoImageView = view.findViewById(R.id.user_photo_shapeable_image_view);
        nameTextView = view.findViewById(R.id.name_text_view);
        userNameTextView = view.findViewById(R.id.user_name_text_view);
        exitButton = view.findViewById(R.id.exit_button);
        exitButton.setOnClickListener(exitButtonClickListener);
        accountPresenter.loadAccount();
    }

    @Override
    public void setAccountInfo(String name, String userName) {
        nameTextView.setText(name);
        userNameTextView.setText(userName);
    }

    @Override
    public ShapeableImageView getImageView() {
        return userPhotoImageView;
    }
}