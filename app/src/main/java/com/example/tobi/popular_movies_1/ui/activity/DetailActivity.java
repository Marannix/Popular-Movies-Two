package com.example.tobi.popular_movies_1.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.tobi.popular_movies_1.presenter.DetailPresenter;
import com.example.tobi.popular_movies_1.R;
import com.example.tobi.popular_movies_1.data.model.Movie;

public class DetailActivity extends BaseActivity {

  private final AppCompatActivity activity = DetailActivity.this;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
    Movie movie = getIntent().getParcelableExtra("myDataKey");
    Intent intent = getIntent();
    DetailPresenter presenter = new DetailPresenter(getApplicationContext(), activity, intent,
        movie);
    presenter.present(getViewGroup());
    setTitle(movie.getTitle());
  }
}
