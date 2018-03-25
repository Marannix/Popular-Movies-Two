package com.example.tobi.popular_movies_1;

public class MoviePresenter {

  // When refactoring I have this class already sorted.

  private MovieView movieView;

  private void present(GridAdapter gridAdapter) {
    movieView = new MovieView(gridAdapter);
  }

}
