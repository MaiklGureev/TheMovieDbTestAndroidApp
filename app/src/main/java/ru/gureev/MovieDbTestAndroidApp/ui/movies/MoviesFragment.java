package ru.gureev.MovieDbTestAndroidApp.ui.movies;

import android.app.SearchableInfo;
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
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import ru.gureev.MovieDbTestAndroidApp.POJOs.enities.Movie;
import ru.gureev.MovieDbTestAndroidApp.R;
import ru.gureev.MovieDbTestAndroidApp.TypeAdapter;

public class MoviesFragment extends Fragment implements MoviesContract.View {

    private MoviesPresenter moviesPresenter;
    private TypeAdapter currentTypeAdapter;

    private RecyclerView recyclerView;
    private MaterialTextView welcomeTextView;
    private MaterialTextView nothingFoundTextView;
    private SearchView searchViewButton;
    private Toolbar toolbar;
    private MenuItem searchMenuItem;
    private MenuItem switchTypeAdapterMenuItem;
    private SearchView searchViewInToolbar;

    private LinearLayoutManager mLayoutManager;
    private GridLayoutManager gridLayoutManager;

    private MovieAdapter movieAdapterVertical;
    private MovieAdapter movieAdapterGrid;

    private String currentQuery = "";
    private int currentPage = 1;
    private String TAG = "MoviesFragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        moviesPresenter =
                ViewModelProviders.of(this).get(MoviesPresenter.class);
        moviesPresenter.setView(this);
        View root = inflater.inflate(R.layout.fragment_movies, container, false);
        Log.d(TAG, "onCreateView: ");

        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.relative_layout_movies);
        welcomeTextView = view.findViewById(R.id.welcome_to_app_text_view);
        nothingFoundTextView = view.findViewById(R.id.nothing_found_text_view);
        searchViewButton = view.findViewById(R.id.welcome_to_app_search_view);

        searchViewButton.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchMenuItem.setVisible(true);
                switchTypeAdapterMenuItem.setVisible(true);
                searchMenuItem.expandActionView();
                searchViewInToolbar.setQuery(query, true);
                searchViewButton.setQuery("", false);
                searchViewButton.onActionViewCollapsed();
                showTextAndSearchView(false);
                showRecyclerView(true);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        currentTypeAdapter = TypeAdapter.LINER;
        mLayoutManager = new LinearLayoutManager(getContext());
        gridLayoutManager = new GridLayoutManager(getContext(), 2);

        movieAdapterVertical = new MovieAdapter(TypeAdapter.LINER);
        movieAdapterGrid = new MovieAdapter(TypeAdapter.GRID);

        moviesPresenter.getMovies().observe((LifecycleOwner) getContext(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                movieAdapterVertical.setMovies(movies);
                movieAdapterGrid.setMovies(movies);
            }
        });


        initMenu(view);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(movieAdapterVertical);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (movieAdapterGrid.isLastVisibleItem() || movieAdapterVertical.isLastVisibleItem()) {
                    currentPage++;
                    moviesPresenter.loadMovies(currentQuery, currentPage);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        if (currentTypeAdapter != moviesPresenter.getCurrentTypeAdapter()) {
            setAdapter(moviesPresenter.getCurrentTypeAdapter());
        }
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
                currentPage = 1;
                currentQuery = query;
                showTextViewNothingFound(false);
                moviesPresenter.loadMovies(query, currentPage);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                if (newText.length() % 2 == 0 && !currentQuery.isEmpty()) {
//                    currentPage = 1;
//                    currentQuery = newText;
//                    showTextViewNothingFound(false);
//                    moviesPresenter.loadMovies(newText, currentPage);
//                }
                showTextViewNothingFound(false);
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
                moviesPresenter.clearSearchMovies();
                searchMenuItem.setVisible(false);
                switchTypeAdapterMenuItem.setVisible(false);
                showRecyclerView(false);
                showTextAndSearchView(true);
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

    }

    @Override
    public void switchAdapter() {
        if (currentTypeAdapter == TypeAdapter.LINER) {
            setAdapter(TypeAdapter.GRID);
        } else {
            setAdapter(TypeAdapter.LINER);
        }
        moviesPresenter.saveCurrentTypeAdapter(currentTypeAdapter);
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
            recyclerView.setAdapter(movieAdapterVertical);
            switchTypeAdapterMenuItem.setIcon(R.drawable.ic_grid_24dp);
        }
        moviesPresenter.saveCurrentTypeAdapter(currentTypeAdapter);

    }


    @Override
    public void showTextAndSearchView(boolean value) {
        if (value) {
            welcomeTextView.setVisibility(View.VISIBLE);
            searchViewButton.setVisibility(View.VISIBLE);
        } else {
            welcomeTextView.setVisibility(View.GONE);
            searchViewButton.setVisibility(View.GONE);
        }
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
    public void onResume() {
        super.onResume();
        if (currentTypeAdapter != moviesPresenter.getCurrentTypeAdapter()) {
            switchAdapter();
        }
        if (!moviesPresenter.getCurrentQuery().equals("")) {
            searchViewInToolbar.clearFocus();
            searchViewInToolbar.setQuery(moviesPresenter.getCurrentQuery(), false);
            showRecyclerView(true);
            showTextAndSearchView(false);
            showTextViewNothingFound(false);
        } else {
            showRecyclerView(false);
            showTextAndSearchView(true);
            showTextViewNothingFound(false);
            searchMenuItem.setVisible(false);
            switchTypeAdapterMenuItem.setVisible(false);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}