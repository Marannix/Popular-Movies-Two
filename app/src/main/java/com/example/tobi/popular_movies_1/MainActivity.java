package com.example.tobi.popular_movies_1;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends BaseActivity {

  private static final String BUNDLE_KEY_MENU_FAVOURITE_MOVIES = "menuFavouriteMovies";
  private static final String BUNDLE_KEY_MENU_POPULAR_MOVIES = "menuPopularMovies";
  private static final String BUNDLE_KEY_MENU_TOP_RATED_MOVIES = "menuTopRatedMovies";
  private static final String BUNDLE_KEY_LAYOUT_MANAGER_STATE = "layoutManagerState";

  private MoviePresenter presenter;
  private boolean shouldShowPopularMovies = true;
  private boolean shouldShowTopRatedMovies;
  private boolean shouldShowFavouriteMovies;
  private Parcelable layoutManagerInstanceState;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if (savedInstanceState != null) {
      layoutManagerInstanceState =
          savedInstanceState.getParcelable(BUNDLE_KEY_LAYOUT_MANAGER_STATE);
      shouldShowPopularMovies = savedInstanceState.getBoolean(BUNDLE_KEY_MENU_POPULAR_MOVIES);
      shouldShowTopRatedMovies = savedInstanceState.getBoolean(BUNDLE_KEY_MENU_TOP_RATED_MOVIES);
      shouldShowFavouriteMovies = savedInstanceState.getBoolean(BUNDLE_KEY_MENU_FAVOURITE_MOVIES);
    }

    presenter = new MoviePresenter(getApplicationContext(), shouldShowPopularMovies,
        shouldShowTopRatedMovies, shouldShowFavouriteMovies, layoutManagerInstanceState);
    presenter.present(getViewGroup());
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.popularMovies) {
      presenter.retrievePopularMovies();
      shouldShowPopularMovies = true;
      shouldShowTopRatedMovies = false;
      shouldShowFavouriteMovies = false;
      return true;
    } else if (item.getItemId() == R.id.topRated) {
      presenter.retrieveTopRatedMovies();
      shouldShowPopularMovies = false;
      shouldShowTopRatedMovies = true;
      shouldShowFavouriteMovies = false;
      return true;
    } else if (item.getItemId() == R.id.favourites) {
      presenter.retrieveFavouriteMovies();
      shouldShowPopularMovies = false;
      shouldShowTopRatedMovies = false;
      shouldShowFavouriteMovies = true;
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putParcelable(BUNDLE_KEY_LAYOUT_MANAGER_STATE,
        presenter.getLayoutManagerSaveInstanceState());
    outState.putBoolean(BUNDLE_KEY_MENU_POPULAR_MOVIES, shouldShowPopularMovies);
    outState.putBoolean(BUNDLE_KEY_MENU_TOP_RATED_MOVIES, shouldShowTopRatedMovies);
    outState.putBoolean(BUNDLE_KEY_MENU_FAVOURITE_MOVIES, shouldShowFavouriteMovies);
  }
}
