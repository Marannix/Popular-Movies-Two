package com.example.tobi.popular_movies_1;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiModule {

  String BASE_URL = "https://api.themoviedb.org/3/";

  Retrofit provideRetrofit() {
    Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    return retrofit;
  }

  MovieApi provideMovieApi(Retrofit retrofit) {
    return retrofit.create(MovieApi.class);
  }

  public MovieApi movieApi() {
    return provideMovieApi(provideRetrofit());
  }
}
