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
  private Parcelable layoutManagerInstanceState;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setTitle(R.string.popular_movies_label);
    presenter = new MoviePresenter(getApplicationContext(), layoutManagerInstanceState);

    if (savedInstanceState != null) {
      layoutManagerInstanceState =
          savedInstanceState.getParcelable(BUNDLE_KEY_LAYOUT_MANAGER_STATE);
      presenter.shouldShowPopularMovies =
          savedInstanceState.getBoolean(BUNDLE_KEY_MENU_POPULAR_MOVIES);
      presenter.shouldShowTopRatedMovies =
          savedInstanceState.getBoolean(BUNDLE_KEY_MENU_TOP_RATED_MOVIES);
      presenter.shouldShowFavouriteMovies =
          savedInstanceState.getBoolean(BUNDLE_KEY_MENU_FAVOURITE_MOVIES);
    }

    presenter.present(getViewGroup());
  }

  @Override protected void onResume() {
    super.onResume();
    presenter.refresh();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.popularMovies) {
      presenter.retrievePopularMovies();
      setTitle(R.string.popular_movies_label);
      return true;
    } else if (item.getItemId() == R.id.topRated) {
      presenter.retrieveTopRatedMovies();
      setTitle(R.string.top_rated_label);
      return true;
    } else if (item.getItemId() == R.id.favourites) {
      presenter.retrieveFavouriteMovies();
      setTitle(R.string.favourites);
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putParcelable(BUNDLE_KEY_LAYOUT_MANAGER_STATE,
        presenter.getLayoutManagerSaveInstanceState());
    outState.putBoolean(BUNDLE_KEY_MENU_POPULAR_MOVIES, presenter.shouldShowPopularMovies);
    outState.putBoolean(BUNDLE_KEY_MENU_TOP_RATED_MOVIES, presenter.shouldShowTopRatedMovies);
    outState.putBoolean(BUNDLE_KEY_MENU_FAVOURITE_MOVIES, presenter.shouldShowFavouriteMovies);
  }
}
