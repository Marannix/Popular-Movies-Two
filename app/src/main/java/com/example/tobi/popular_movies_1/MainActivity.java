package com.example.tobi.popular_movies_1;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String BUNDLE_KEY_MENU_FAVOURITE_MOVIES = "menuFavouriteMovies";
    private static final String BUNDLE_KEY_MENU_POPULAR_MOVIES = "menuPopularMovies";
    private static final String BUNDLE_KEY_MENU_TOP_RATED_MOVIES = "menuTopRatedMovies";
    private static final String BUNDLE_KEY_LAYOUT_MANAGER_STATE = "layoutManagerState";

    private RecyclerView recyclerView;
    private Context context;
    private GridAdapter adapter;
    private GridLayoutManager layoutManager;
    private ApiModule apiModule;
    private MovieView movieView;
    private FavouriteDbHelper favouriteDbHelper;

    private boolean shouldShowPopularMovies = true;
    private boolean shouldShowTopRatedMovies;
    private boolean shouldShowFavouriteMovies;

    private Parcelable layoutManagerInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        favouriteDbHelper = new FavouriteDbHelper(this);
        init();
        adapter = new GridAdapter();
        context = getApplicationContext();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(MainActivity.this, 2);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
        movieView = new MovieView(adapter);

        if (savedInstanceState != null) {
            layoutManagerInstanceState = savedInstanceState.getParcelable(BUNDLE_KEY_LAYOUT_MANAGER_STATE);
            shouldShowPopularMovies = savedInstanceState.getBoolean(BUNDLE_KEY_MENU_POPULAR_MOVIES);
            shouldShowTopRatedMovies = savedInstanceState.getBoolean(BUNDLE_KEY_MENU_TOP_RATED_MOVIES);
            shouldShowFavouriteMovies = savedInstanceState.getBoolean(BUNDLE_KEY_MENU_FAVOURITE_MOVIES);
        }
        showMovies();
    }

    private void showMovies() {
        if (shouldShowPopularMovies) {
            retrievePopularMovies();
        } else if (shouldShowTopRatedMovies) {
            retrieveTopRatedMovies();
        } else if (shouldShowFavouriteMovies) {
            retrieveFavouriteMovies();
        }
    }

    private void retrieveFavouriteMovies() {
        adapter.clearList();
        movieView.setMovieData(favouriteDbHelper.getAllFavourite(), context);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.popularMovies) {
//            if (IsItemChecked(item)) return true;
            retrievePopularMovies();
            shouldShowPopularMovies = true;
            shouldShowTopRatedMovies = false;
            shouldShowFavouriteMovies = false;
            return true;
        } else if (item.getItemId() == R.id.topRated) {
//            if (IsItemChecked(item)) return true;
            retrieveTopRatedMovies();
            shouldShowPopularMovies = false;
            shouldShowTopRatedMovies = true;
            shouldShowFavouriteMovies = false;
            return true;
        } else if (item.getItemId() == R.id.favourites) {
//            if (IsItemChecked(item)) return true;
            retrieveFavouriteMovies();
            shouldShowPopularMovies = false;
            shouldShowTopRatedMovies = false;
            shouldShowFavouriteMovies = true;
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean IsItemChecked(MenuItem item) {
        if (item.isChecked()) {
            return true;
        }
        item.setChecked(!item.isChecked());
        return false;
    }

    private void retrievePopularMovies() {
        Call<MovieResponse> popularMovies = apiModule.movieApi().getPopularMovies();
        popularMovies.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call,
                                   Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    MovieResponse movieResults = response.body();
                    movieView.setMovieData(movieResults.getResults(), context);
                } else {
                    Toast.makeText(context, "Unable to show Popular Movies", Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "Please provide an Api Key, Thank you", Toast.LENGTH_SHORT).show();

                }

                if (layoutManagerInstanceState != null) {
                    layoutManager.onRestoreInstanceState(layoutManagerInstanceState);
                    layoutManagerInstanceState = null;
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(context, "Unable to retrieve Popular Movies", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void retrieveTopRatedMovies() {
        Call<MovieResponse> topRated = apiModule.movieApi().getTopRatedMovies();
        topRated.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                if (response.isSuccessful()) {
                    MovieResponse movieResults = response.body();
                    movieView.setMovieData(movieResults.getResults(), context);
                } else {
                    Toast.makeText(context, "Unable to show Top Rated Movies", Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "Please provide an Api Key, Thank you", Toast.LENGTH_SHORT).show();
                }

                if (layoutManagerInstanceState != null) {
                    layoutManager.onRestoreInstanceState(layoutManagerInstanceState);
                    layoutManagerInstanceState = null;
                }
            }


            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(context, "Unable to retrieve Top Rated Movies", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        apiModule = new ApiModule();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_KEY_LAYOUT_MANAGER_STATE, layoutManager.onSaveInstanceState());
        outState.putBoolean(BUNDLE_KEY_MENU_POPULAR_MOVIES, shouldShowPopularMovies);
        outState.putBoolean(BUNDLE_KEY_MENU_TOP_RATED_MOVIES, shouldShowTopRatedMovies);
    }
}
