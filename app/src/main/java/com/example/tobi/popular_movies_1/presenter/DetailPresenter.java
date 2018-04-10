package com.example.tobi.popular_movies_1.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.tobi.popular_movies_1.database.FavouriteDbHelper;
import com.example.tobi.popular_movies_1.response.ReviewResponse;
import com.example.tobi.popular_movies_1.response.VideoResponse;
import com.example.tobi.popular_movies_1.api.ApiModule;
import com.example.tobi.popular_movies_1.data.model.Movie;
import com.example.tobi.popular_movies_1.view.DetailView;
import com.example.tobi.popular_movies_1.view.ReviewView;
import com.example.tobi.popular_movies_1.view.VideoView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPresenter {

  private Intent intent;
  private Movie movie;
  private Context context;
  private ReviewView reviewView;
  private DetailView detailView;
  private ApiModule apiModule;
  private VideoView videoView;
  private FavouriteDbHelper favouriteDbHelper;
  private final AppCompatActivity activity;

  public DetailPresenter(Context context, AppCompatActivity activity, Intent intent, Movie movie) {
    this.context = context;
    this.activity = activity;
    this.movie = movie;
    this.intent = intent;
  }

  public void present(ViewGroup rootView) {
    apiModule = new ApiModule();
    reviewView = new ReviewView(rootView);
    videoView = new VideoView(rootView);
    detailView = new DetailView(rootView, context);
    detailView.setContent(movie, intent);
    retrieveReviews();
    retrieveVideos();
    setFavouriteMovie();
  }

  private void retrieveReviews() {
    Call<ReviewResponse> reviewResponseCall = apiModule.movieApi().getMovieReviews(movie.getId());

    reviewResponseCall.enqueue(new Callback<ReviewResponse>() {
      @Override
      public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
        if (response.isSuccessful()) {
          ReviewResponse reviewResponse = response.body();
          reviewView.setReviews(reviewResponse.getResults());
        }
      }

      @Override public void onFailure(Call<ReviewResponse> call, Throwable t) {
        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void retrieveVideos() {
    Call<VideoResponse> videoResponseCall = apiModule.movieApi().getMovieVideos(movie.getId());
    videoResponseCall.enqueue(new Callback<VideoResponse>() {
      @Override public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
        if (response.isSuccessful()) {
          VideoResponse videoResponse = response.body();
          videoView.setVideos(videoResponse.getResults(), context);
        }
      }

      @Override public void onFailure(Call<VideoResponse> call, Throwable t) {

      }
    });
  }

  private void setFavouriteMovie() {
    favouriteDbHelper = new FavouriteDbHelper(activity);
    detailView.favouriteButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        if (!favouriteDbHelper.isFavourite(String.valueOf(movie.getId()))) {
          SharedPreferences.Editor editor =
              context.getSharedPreferences(".DetailActivity", Context.MODE_PRIVATE).edit();
          editor.putBoolean("Favourite Added", true);
          editor.apply();
          favouriteDbHelper.addFavourite(movie);
          Toast.makeText(context, "Added to Favourite", Toast.LENGTH_SHORT).show();
        } else {
          SharedPreferences.Editor editor =
              context.getSharedPreferences(".DetailActivity", Context.MODE_PRIVATE).edit();
          favouriteDbHelper = new FavouriteDbHelper(context);
          favouriteDbHelper.removeFavourite(movie.getId());
          editor.putBoolean("Favourite Removed", true);
          editor.apply();
          Toast.makeText(context, "Removed From Favourite", Toast.LENGTH_SHORT).show();
        }
      }
    });
  }
}
