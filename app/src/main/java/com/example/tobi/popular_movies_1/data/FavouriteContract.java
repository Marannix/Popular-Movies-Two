package com.example.tobi.popular_movies_1.data;

import android.provider.BaseColumns;

public class FavouriteContract {

    public static final class FavouriteEntry implements BaseColumns {
        public static final String TABLE_NAME = "favourite";
        public static final String COLUMN_MOVIE_ID = "movieid";
        public static final String COLUMN_MOVIE_TITLE = "title";
        public static final String COLUMN_MOVIE_USER_RATING = "userrating";
        public static final String COLUMN_POSTER_PATH = "posterpath";
        public static final String COLUMN_MOVIE_OVERVIEW = "overview";

    }
}
