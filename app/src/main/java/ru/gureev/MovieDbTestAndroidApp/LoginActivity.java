package ru.gureev.MovieDbTestAndroidApp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FragmentContainerView fragmentContainerView = findViewById(R.id.nav_login_fragment);

        NavController navController = Navigation.findNavController(this, R.id.nav_login_fragment);
        Navigation.setViewNavController(fragmentContainerView, navController);
    }
}
