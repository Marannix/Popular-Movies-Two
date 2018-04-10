package com.example.tobi.popular_movies_1;

import android.content.Context;
import android.os.Parcelable;
import android.view.ViewGroup;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviePresenter {

  public boolean shouldShowPopularMovies = true;
  public boolean shouldShowTopRatedMovies;
  public boolean shouldShowFavouriteMovies;
  private FavouriteDbHelper favouriteDbHelper;
  private Parcelable layoutManagerInstanceState;
  private final Context context;
  private MovieView movieView;
  private ApiModule apiModule;

  public MoviePresenter(Context context, Parcelable layoutManagerInstanceState) {
    this.context = context;
    favouriteDbHelper = new FavouriteDbHelper(context);
    this.layoutManagerInstanceState = layoutManagerInstanceState;
  }

  public void present(ViewGroup viewGroup) {
    apiModule = new ApiModule();
    movieView = new MovieView(viewGroup);
    showMovies();
  }

  public Parcelable getLayoutManagerSaveInstanceState() {
    return movieView.getLayoutManager().onSaveInstanceState();
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

  public void refresh() {
    if (shouldShowFavouriteMovies) {
      retrieveFavouriteMovies();
    }
  }

  public void retrievePopularMovies() {
    showMovies(true, false, false);
    Call<MovieResponse> popularMovies = apiModule.movieApi().getPopularMovies();
    popularMovies.enqueue(new Callback<MovieResponse>() {
      @Override public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
        if (response.isSuccessful()) {
          MovieResponse movieResults = response.body();
          movieView.setMovieData(movieResults.getResults(), context);
        } else {
          Toast.makeText(context, "Unable to show Popular Movies", Toast.LENGTH_SHORT).show();
          Toast.makeText(context, "Please provide an Api Key, Thank you", Toast.LENGTH_SHORT)
              .show();
        }
        getOnRestoreInstanceState();
      }

      @Override public void onFailure(Call<MovieResponse> call, Throwable t) {
        Toast.makeText(context, "Unable to retrieve Popular Movies", Toast.LENGTH_SHORT).show();
        Toast.makeText(context, "Please check your connection", Toast.LENGTH_SHORT).show();
      }
    });
  }

  public void retrieveTopRatedMovies() {
    showMovies(false, true, false);
    Call<MovieResponse> topRated = apiModule.movieApi().getTopRatedMovies();
    topRated.enqueue(new Callback<MovieResponse>() {
      @Override public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

        if (response.isSuccessful()) {
          MovieResponse movieResults = response.body();
          movieView.setMovieData(movieResults.getResults(), context);
        } else {
          Toast.makeText(context, "Unable to show Top Rated Movies", Toast.LENGTH_SHORT).show();
          Toast.makeText(context, "Please provide an Api Key, Thank you", Toast.LENGTH_SHORT)
              .show();
        }
        getOnRestoreInstanceState();
      }

      @Override public void onFailure(Call<MovieResponse> call, Throwable t) {
        Toast.makeText(context, "Unable to retrieve Top Rated Movies", Toast.LENGTH_SHORT).show();
        Toast.makeText(context, "Please check your connection", Toast.LENGTH_SHORT).show();
      }
    });
  }

  public void retrieveFavouriteMovies() {
    showMovies(false, false, true);
    movieView.setMovieData(favouriteDbHelper.getAllFavourite(), context);
  }

  private void showMovies(boolean showPopularMovies, boolean showTopRatedMovies,
      boolean showFavouriteMovies) {
    shouldShowPopularMovies = showPopularMovies;
    shouldShowTopRatedMovies = showTopRatedMovies;
    shouldShowFavouriteMovies = showFavouriteMovies;
  }

  private void getOnRestoreInstanceState() {
    if (layoutManagerInstanceState != null) {
      movieView.getLayoutManager().onRestoreInstanceState(layoutManagerInstanceState);
      layoutManagerInstanceState = null;
    }
  }
}
