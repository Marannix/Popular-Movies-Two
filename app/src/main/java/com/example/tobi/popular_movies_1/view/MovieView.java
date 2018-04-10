package com.example.tobi.popular_movies_1.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.tobi.popular_movies_1.R;
import com.example.tobi.popular_movies_1.adapter.GridAdapter;
import com.example.tobi.popular_movies_1.data.model.Movie;
import java.util.List;

public class MovieView {

  private GridAdapter gridAdapter;
  private GridLayoutManager layoutManager;
  private ViewGroup parent;

  @BindView(R.id.recyclerView) RecyclerView recyclerView;

  public MovieView(ViewGroup parent) {
    ButterKnife.bind(this, parent);
    this.parent = parent;
    initMovieAdapter();
  }

  public void setMovieData(List<Movie> movies, Context context) {
    gridAdapter.setMovieData(movies, context);
  }

  private void initMovieAdapter() {
    gridAdapter = new GridAdapter();
    recyclerView.setHasFixedSize(true);
    layoutManager = new GridLayoutManager(parent.getContext(), 2);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(gridAdapter);
  }

  public GridLayoutManager getLayoutManager() {
    return layoutManager;
  }

}
