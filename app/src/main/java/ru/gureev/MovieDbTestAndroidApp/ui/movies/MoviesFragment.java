package ru.gureev.MovieDbTestAndroidApp.ui.movies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import ru.gureev.MovieDbTestAndroidApp.R;

public class MoviesFragment extends Fragment {

    private MoviesViewModel moviesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        moviesViewModel =
                ViewModelProviders.of(this).get(MoviesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_movies, container, false);

        return root;
    }
}