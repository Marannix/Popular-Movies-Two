package com.example.tobi.popular_movies_1;

import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;

public class DetailView {

  private Context context;

  @BindView(R.id.detailImageView) ImageView imageView;
  @BindView(R.id.detailOverview) TextView overview;
  @BindView(R.id.detailReleaseDate) TextView releaseDate;
  @BindView(R.id.detailTitle) TextView title;
  @BindView(R.id.favouriteButton) Button favouriteButton;

  public DetailView(ViewGroup parent, Context context) {
    ButterKnife.bind(this, parent);
    this.context = context;
  }

  public void setContent(Movie movie, Intent intent) {
    title.setText(movie.getTitle());
    overview.setText(movie.getOverview());
    releaseDate.setText(movie.getReleaseDate());
    Picasso.with(context).load(intent.getStringExtra("path")).into(imageView);
  }
}
