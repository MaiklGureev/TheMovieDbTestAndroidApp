package ru.gureev.MovieDbTestAndroidApp.ui.main.show_movie_details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import ru.gureev.MovieDbTestAndroidApp.AppConfig;
import ru.gureev.MovieDbTestAndroidApp.POJOs.enities.Movie;
import ru.gureev.MovieDbTestAndroidApp.R;
import ru.gureev.MovieDbTestAndroidApp.tools.Utils;

public class ShowMovieDetailsFragment extends Fragment implements ShowMovieDetailsContract.View {

    View.OnClickListener goBackClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Navigation.findNavController(v).popBackStack();
        }
    };
    private int movieId;
    private boolean isFavoriteMovie = false;
    private ShapeableImageView image;
    private ImageView timeIcon;
    private MaterialTextView title;
    private MaterialTextView englishTitle;
    private MaterialTextView genres;
    private MaterialTextView rating;
    private MaterialTextView numberRating;
    private MaterialTextView time;
    private MaterialTextView description;
    private Toolbar toolbar;
    private MenuItem switchFavoriteMovieMenuItem;
    private ShowMovieDetailsPresenter showMovieDetailsPresenter;
    MenuItem.OnMenuItemClickListener switchMovieFavoriteStatusOnMenuItemClickListener = new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            showMovieDetailsPresenter.switchMovieFavoriteStatus(!isFavoriteMovie);
            switchFavoriteMovie(!isFavoriteMovie);
            return false;
        }
    };

    public static ShowMovieDetailsFragment newInstance() {
        return new ShowMovieDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        showMovieDetailsPresenter =
                ViewModelProviders.of(this).get(ShowMovieDetailsPresenter.class);
        showMovieDetailsPresenter.setView(this);
        movieId = getArguments().getInt("id");
        return inflater.inflate(R.layout.show_movie_details_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        image = view.findViewById(R.id.shapeable_image_view);
        title = view.findViewById(R.id.title);
        englishTitle = view.findViewById(R.id.english_title);
        genres = view.findViewById(R.id.genres);
        rating = view.findViewById(R.id.rating);
        numberRating = view.findViewById(R.id.number_of_rating);
        time = view.findViewById(R.id.time);
        description = view.findViewById(R.id.description);
        timeIcon = view.findViewById(R.id.icon_time);

        initToolbar(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        showMovieDetailsPresenter.loadMovieDetails(movieId);

        showMovieDetailsPresenter.getCurrentMovieMutableLiveData().observe((LifecycleOwner) getContext(), new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                Utils.loadPicture(image, AppConfig.API_IMAGE_URL + movie.getPoster_path());
                title.setText(movie.getTitle());
                englishTitle.setText(movie.getOriginal_title() + Utils.transformDate(movie));
                genres.setText(Utils.genresToString(movie));
                rating.setText(String.valueOf(movie.getVote_average()));
                numberRating.setText(String.valueOf(movie.getVote_count()));
                time.setText(movie.getRuntime() + " мин");
                description.setText(movie.getOverview());
                timeIcon.setVisibility(View.VISIBLE);
            }
        });

        showMovieDetailsPresenter.getIsFavoriteMovieMutableLiveData().observe((LifecycleOwner) getContext(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                switchFavoriteMovie(aBoolean);
            }
        });

    }


    @Override
    public void switchFavoriteMovie(boolean isFavorite) {
        if (isFavorite) {
            switchFavoriteMovieMenuItem.setIcon(R.drawable.ic_favorite_24dp);
            isFavoriteMovie = true;
        } else {
            switchFavoriteMovieMenuItem.setIcon(R.drawable.ic_not_favorite_24dp);
            isFavoriteMovie = false;
        }
    }


    void initToolbar(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.show_movie_details_menu);
        toolbar.setNavigationIcon(R.drawable.ic_back_24dp);
        toolbar.setNavigationOnClickListener(goBackClickListener);
        switchFavoriteMovieMenuItem = toolbar.getMenu().findItem(R.id.action_switch_type_adapter);
        switchFavoriteMovieMenuItem.setOnMenuItemClickListener(switchMovieFavoriteStatusOnMenuItemClickListener);
    }
}
