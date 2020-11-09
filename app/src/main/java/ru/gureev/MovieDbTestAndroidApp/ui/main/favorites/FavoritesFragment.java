package ru.gureev.MovieDbTestAndroidApp.ui.main.favorites;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import ru.gureev.MovieDbTestAndroidApp.AppConfig;
import ru.gureev.MovieDbTestAndroidApp.POJOs.enities.Movie;
import ru.gureev.MovieDbTestAndroidApp.R;
import ru.gureev.MovieDbTestAndroidApp.tools.TypeAdapter;
import ru.gureev.MovieDbTestAndroidApp.ui.main.movies.MovieAdapter;

public class FavoritesFragment extends Fragment implements FavoritesContract.View {

    private FavoritesPresenter favoritesPresenter;
    private TypeAdapter currentTypeAdapter;

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private MenuItem searchMenuItem;
    private MenuItem switchTypeAdapterMenuItem;
    private SearchView searchViewInToolbar;
    private MaterialTextView nothingFoundTextView;
    private MaterialTextView favoriteTextView;

    private LinearLayoutManager mLayoutManager;
    private GridLayoutManager gridLayoutManager;

    private MovieAdapter movieAdapterList;
    private MovieAdapter movieAdapterGrid;

    private String TAG = "FavoritesFragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        favoritesPresenter =
                ViewModelProviders.of(this).get(FavoritesPresenter.class);

        View root = inflater.inflate(R.layout.fragment_favorites, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.relative_layout_movies);
        nothingFoundTextView = view.findViewById(R.id.nothing_found_text_view);
        favoriteTextView = view.findViewById(R.id.favorite_text_view);
        favoritesPresenter.setView(this);

        currentTypeAdapter = TypeAdapter.LINER;
        mLayoutManager = new LinearLayoutManager(getContext());
        gridLayoutManager = new GridLayoutManager(getContext(), 2);

        movieAdapterList = new MovieAdapter(TypeAdapter.LINER);
        movieAdapterGrid = new MovieAdapter(TypeAdapter.GRID);

        movieAdapterList.setPopUpFragment(AppConfig.PopUpFragment.FAVORITES);
        movieAdapterGrid.setPopUpFragment(AppConfig.PopUpFragment.FAVORITES);

        favoritesPresenter.getMovies().observe((LifecycleOwner) getContext(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                Log.d(TAG, "onChanged:  favoritesPresenter.getMovies()" + movies.toString());
                movieAdapterList.setMovies(movies);
                movieAdapterGrid.setMovies(movies);
                searchViewInToolbar.setQuery(favoritesPresenter.getCurrentQuery(), true);
                movieAdapterList.filter(favoritesPresenter.getCurrentQuery());
                movieAdapterGrid.filter(favoritesPresenter.getCurrentQuery());
//                if (!movies.isEmpty()) {
//                    showRecyclerView(true);
//                    showTextViewNothingFound(false);
//                } else {
//                    showRecyclerView(false);
//                    showTextViewNothingFound(true);
//                }
            }
        });

        initMenu(view);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(movieAdapterList);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (movieAdapterGrid.getLastVisibleItem() >= 4 || movieAdapterList.getLastVisibleItem() >= 4) {
                    favoriteTextView.setVisibility(View.GONE);
                } else {
                    favoriteTextView.setVisibility(View.VISIBLE);
                }
            }
        });

        if (currentTypeAdapter != favoritesPresenter.getCurrentTypeAdapter()) {
            setAdapter(favoritesPresenter.getCurrentTypeAdapter());
        }

//        favoritesPresenter.loadMovies();
        searchViewInToolbar.setQuery(favoritesPresenter.getCurrentQuery(), true);

    }

    public void initMenu(View view) {

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.movies_menu);

        searchMenuItem = toolbar.getMenu().findItem(R.id.action_search);
        switchTypeAdapterMenuItem = toolbar.getMenu().findItem(R.id.action_switch_type_adapter);
        searchViewInToolbar = (SearchView) searchMenuItem.getActionView();

        searchViewInToolbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieAdapterGrid.filter(query);
                movieAdapterList.filter(query);
                searchMenuItem.expandActionView();
                favoritesPresenter.setCurrentQuery(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                movieAdapterGrid.filter(newText);
                movieAdapterList.filter(newText);
                favoritesPresenter.setCurrentQuery(newText);

                return false;
            }
        });

        searchMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                showTextViewNothingFound(false);
                return true;
            }
        });

        switchTypeAdapterMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switchAdapter();
                return false;
            }
        });

        movieAdapterList.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                Log.d(TAG, "onChanged:  movieAdapterList.registerAdapterDataObserver " + favoritesPresenter.getMovies().getValue());
                if (recyclerView.getAdapter().getItemCount() == 0) {
                    showRecyclerView(false);
                    showTextViewNothingFound(true);
                } else {
                    showRecyclerView(true);
                    showTextViewNothingFound(false);
                }
            }
        });
    }

    @Override
    public void switchAdapter() {
        if (currentTypeAdapter == TypeAdapter.LINER) {
            setAdapter(TypeAdapter.GRID);
            favoriteTextView.setVisibility(View.VISIBLE);
        } else {
            setAdapter(TypeAdapter.LINER);
            favoriteTextView.setVisibility(View.VISIBLE);
        }
        favoritesPresenter.saveCurrentTypeAdapter(currentTypeAdapter);
    }

    @Override
    public void setAdapter(TypeAdapter typeAdapter) {
        if (typeAdapter == TypeAdapter.GRID) {
            currentTypeAdapter = TypeAdapter.GRID;
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(movieAdapterGrid);
            switchTypeAdapterMenuItem.setIcon(R.drawable.ic_list_24dp);
        } else {
            currentTypeAdapter = TypeAdapter.LINER;
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(movieAdapterList);
            switchTypeAdapterMenuItem.setIcon(R.drawable.ic_grid_24dp);
        }
        favoritesPresenter.saveCurrentTypeAdapter(currentTypeAdapter);
    }

    @Override
    public void showRecyclerView(boolean value) {
        if (value) {
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.GONE);
        }
    }


    @Override
    public void showTextViewNothingFound(boolean value) {
        if (value) {
            nothingFoundTextView.setVisibility(View.VISIBLE);
        } else {
            nothingFoundTextView.setVisibility(View.GONE);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        favoritesPresenter.reloadMovie();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (currentTypeAdapter != favoritesPresenter.getCurrentTypeAdapter()) {
            switchAdapter();
        }
        searchViewInToolbar.clearFocus();

        searchViewInToolbar.setQuery(favoritesPresenter.getCurrentQuery(), true);
        movieAdapterList.filter(favoritesPresenter.getCurrentQuery());
        movieAdapterGrid.filter(favoritesPresenter.getCurrentQuery());

    }
}