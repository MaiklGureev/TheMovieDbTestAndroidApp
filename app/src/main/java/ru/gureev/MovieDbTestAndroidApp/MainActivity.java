package ru.gureev.MovieDbTestAndroidApp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.redmadrobot.pinkman.Pinkman;

import java.util.ArrayList;

import ru.gureev.MovieDbTestAndroidApp.repository.Repository;
import ru.gureev.MovieDbTestAndroidApp.tools.GlobalAppContextSingleton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        GlobalAppContextSingleton.getInstance().initialize(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        String s = intent.getStringExtra(AppConfig.PIN_STORAGE_NAME);
        boolean resetData = intent.getBooleanExtra(AppConfig.RESET_DATA, false);
        Pinkman pinkman = new Pinkman(getApplicationContext(), AppConfig.PIN_STORAGE_NAME, new ArrayList<String>());

        if (s == null || !pinkman.isValidPin(s) || !pinkman.isPinSet()) {
            Log.d("MainActivity", "onStart: The crook will not pass !!!");
            System.exit(1);
        } else if (resetData) {
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(AppConfig.APP_PREFERENCES, MODE_PRIVATE);
            Repository.getInstance().getAccountData().setSession_id(sharedPreferences.getString(AppConfig.APP_PREFERENCES_NAME, ""));
            pinkman.removePin();
        } else if (!resetData) {
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(AppConfig.APP_PREFERENCES, MODE_PRIVATE);
            Repository.getInstance()
                    .getAccountData()
                    .loadAccountData(sharedPreferences.getString(AppConfig.APP_PREFERENCES_NAME, ""));
        }
    }


}
