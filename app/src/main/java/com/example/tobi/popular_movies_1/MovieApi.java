package com.example.tobi.popular_movies_1;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Tobi on 25-Feb-18.
 */

public interface MovieApi {

  //Insert your Api Key or crash
  String apiKey = "";

  String authentication = "api_key=" + apiKey;

  @GET("movie/popular?" + authentication) Call<MovieResponse> getPopularMovies();
  @GET("movie/top_rated?" + authentication) Call<MovieResponse> getTopRatedMovies();

}
