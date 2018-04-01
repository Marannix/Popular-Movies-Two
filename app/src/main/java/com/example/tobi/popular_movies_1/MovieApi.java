package com.example.tobi.popular_movies_1;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Tobi on 25-Feb-18.
 */

public interface MovieApi {

    //Insert your Api Key or crash
    String apiKey = "";

    String authentication = "api_key=" + apiKey;

    @GET("movie/popular?" + authentication)
    Call<MovieResponse> getPopularMovies();

    @GET("movie/top_rated?" + authentication)
    Call<MovieResponse> getTopRatedMovies();

    @GET("movie/{id}/reviews?" + authentication)
    Call<ReviewResponse> getMovieReviews(@Path("id") int id);

    @GET("movie/{id}/videos?" + authentication)
    Call<VideoResponse> getMovieVideos(@Path("id") int id);

}
