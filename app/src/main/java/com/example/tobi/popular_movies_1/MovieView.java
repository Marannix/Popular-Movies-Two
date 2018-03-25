package com.example.tobi.popular_movies_1;

import android.content.Context;
import java.util.List;

public class MovieView {

  private GridAdapter gridAdapter;

  public MovieView(GridAdapter gridAdapter) {
    this.gridAdapter = gridAdapter;
  }

  public void setMovieData(List<Movie>movies, Context context) {
    gridAdapter.setMovieData(movies, context);
  }
}
