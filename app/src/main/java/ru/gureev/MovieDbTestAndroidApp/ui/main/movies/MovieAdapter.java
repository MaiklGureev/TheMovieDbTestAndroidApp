package ru.gureev.MovieDbTestAndroidApp.ui.main.movies;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

import ru.gureev.MovieDbTestAndroidApp.AppConfig;
import ru.gureev.MovieDbTestAndroidApp.POJOs.enities.Movie;
import ru.gureev.MovieDbTestAndroidApp.R;
import ru.gureev.MovieDbTestAndroidApp.tools.TypeAdapter;
import ru.gureev.MovieDbTestAndroidApp.tools.Utils;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {


    private List<Movie> movies = new ArrayList<>();
    private List<Movie> moviesCopy = new ArrayList<>();
    private TypeAdapter typeAdapter;
    private String TAG = "MovieAdapter";
    private String filterQuery = "";
    private int lastVisibleItem = 0;
    private AppConfig.PopUpFragment popUpFragment = AppConfig.PopUpFragment.MOVIES;

    public MovieAdapter(TypeAdapter typeAdapter) {
        this.typeAdapter = typeAdapter;
    }

    public void setMovies(List<Movie> movies) {

        this.movies.clear();
        moviesCopy.clear();
        this.movies.addAll(movies);
        moviesCopy.addAll(movies);
        notifyDataSetChanged();

    }

    public String getFilterQuery() {
        return filterQuery;
    }

    public void setPopUpFragment(AppConfig.PopUpFragment popUpFragment) {
        this.popUpFragment = popUpFragment;
    }

    public boolean isLastVisibleItem() {
        if (lastVisibleItem == movies.size() - 1) {
            return true;
        } else {
            return false;
        }
    }

    public void filter(String text) {
        filterQuery = text;
        movies.clear();
        if (!text.isEmpty()) {
            text = text.toLowerCase();
            for (Movie item : moviesCopy) {
                if (item.getTitle().toLowerCase().contains(text) ||
                        item.getTitle().toLowerCase().contains(text) ||
                        item.getOriginal_title().toLowerCase().contains(text) ||
                        item.getOriginal_title().toLowerCase().contains(text) ||
                        item.getOverview().toLowerCase().contains(text) ||
                        item.getOverview().toLowerCase().contains(text)) {
                    movies.add(item);
                }
            }
        } else {
            movies.addAll(moviesCopy);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (typeAdapter == TypeAdapter.LINER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_horizontal, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_vertical, parent, false);
        }
        return new MovieViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull MovieViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        lastVisibleItem = holder.getLayoutPosition();

        int layoutPosition = holder.getLayoutPosition();
        Log.d(TAG, "onViewAttachedToWindow: getLayoutPosition = " + layoutPosition);

        layoutPosition = holder.getAdapterPosition();
        Log.d(TAG, "onViewAttachedToWindow: getAdapterPosition = " + layoutPosition);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        private final ShapeableImageView image;
        private final MaterialTextView title;
        private final MaterialTextView englishTitle;
        private final MaterialTextView genres;
        private final MaterialTextView rating;
        private final MaterialTextView numberRating;
        private int movieId;
        private View.OnClickListener clickListener;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.shapeable_image_view);
            title = itemView.findViewById(R.id.title);
            englishTitle = itemView.findViewById(R.id.english_title);
            genres = itemView.findViewById(R.id.genres);
            rating = itemView.findViewById(R.id.rating);
            numberRating = itemView.findViewById(R.id.number_of_rating);

        }

        private void bind(@NonNull final Movie movie) {

            movieId = movie.getId();

            clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", movieId);
                    NavOptions navOptions;
                    if (popUpFragment == AppConfig.PopUpFragment.MOVIES) {
                        navOptions = new NavOptions.Builder().setPopUpTo(R.id.navigation_movies, false).build();
                    } else {
                        navOptions = new NavOptions.Builder().setPopUpTo(R.id.navigation_favorites, false).build();
                    }
                    Navigation.findNavController(v).navigate(R.id.navigation_show_movie_details_fragment, bundle, navOptions);
                }
            };

            itemView.setOnClickListener(clickListener);
            Utils.loadPicture(image, AppConfig.API_IMAGE_URL + movie.getPoster_path());
            title.setText(movie.getTitle());
            englishTitle.setText(movie.getOriginal_title() + Utils.transformDate(movie));
            genres.setText(Utils.genresToString(movie));
            rating.setText(String.valueOf(movie.getVote_average()));
            numberRating.setText(String.valueOf(movie.getVote_count()));

        }


    }
}
