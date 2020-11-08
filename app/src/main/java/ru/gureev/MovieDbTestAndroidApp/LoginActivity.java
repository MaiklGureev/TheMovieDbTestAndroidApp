package ru.gureev.MovieDbTestAndroidApp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;

import com.redmadrobot.pinkman.Pinkman;

import java.util.ArrayList;

import ru.gureev.MovieDbTestAndroidApp.tools.GlobalAppContextSingleton;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FragmentContainerView fragmentContainerView = findViewById(R.id.nav_login_fragment);

        GlobalAppContextSingleton.getInstance().initialize(this);

        Pinkman pinkman = new Pinkman(getApplicationContext(), AppConfig.PIN_STORAGE_NAME, new ArrayList<String>());

        NavController navController = Navigation.findNavController(this, R.id.nav_login_fragment);
        Navigation.setViewNavController(fragmentContainerView, navController);

        if (pinkman.isPinSet()) {
            NavGraph navGraph = navController.getGraph();
            navGraph.setStartDestination(R.id.enterPinCodeFragment);
            navController.setGraph(navGraph);
        }


    }
}
