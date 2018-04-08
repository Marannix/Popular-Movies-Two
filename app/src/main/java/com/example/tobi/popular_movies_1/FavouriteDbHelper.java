package com.example.tobi.popular_movies_1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.tobi.popular_movies_1.data.FavouriteContract;

import java.util.ArrayList;
import java.util.List;

public class FavouriteDbHelper extends SQLiteOpenHelper {

    public static final String TAG = "FAVOURITE";

    private static final String DATABASE_NAME = "favourite.db";
    private static final int DATABASE_VERSION = 1;

    SQLiteOpenHelper helper;
    SQLiteDatabase database;

    public FavouriteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public void open() {
        Log.i(TAG, "open: Database Opened");
        database = helper.getWritableDatabase();
    }

    public void close() {
        Log.i(TAG, "close: Database Closed");
        helper.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FAVOURITE_TABLE = "CREATE TABLE " + FavouriteContract.FavouriteEntry.TABLE_NAME + " (" +
                FavouriteContract.FavouriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FavouriteContract.FavouriteEntry.COLUMN_MOVIE_ID + " INTEGER, " +
                FavouriteContract.FavouriteEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL, " +
                FavouriteContract.FavouriteEntry.COLUMN_MOVIE_USER_RATING + " REAL NOT NULL, " +
                FavouriteContract.FavouriteEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                FavouriteContract.FavouriteEntry.COLUMN_MOVIE_OVERVIEW + " TEXT NOT NULL" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVOURITE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavouriteContract.FavouriteEntry.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }

    public void addFavourite(Movie movie) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FavouriteContract.FavouriteEntry.COLUMN_MOVIE_ID, movie.getId());
        values.put(FavouriteContract.FavouriteEntry.COLUMN_MOVIE_TITLE, movie.getTitle());
        values.put(FavouriteContract.FavouriteEntry.COLUMN_MOVIE_USER_RATING, movie.getVoteAverage());
        values.put(FavouriteContract.FavouriteEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        values.put(FavouriteContract.FavouriteEntry.COLUMN_MOVIE_OVERVIEW, movie.getOverview());

        database.insert(FavouriteContract.FavouriteEntry.TABLE_NAME, null, values);
        database.close();
    }

    public void removeFavourite(int id) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(FavouriteContract.FavouriteEntry.TABLE_NAME, FavouriteContract.FavouriteEntry.COLUMN_MOVIE_ID + "=" + id, null);

    }

    public List<Movie> getAllFavourite() {
        String[] columns = {
                FavouriteContract.FavouriteEntry._ID, FavouriteContract.FavouriteEntry.COLUMN_MOVIE_ID,
                FavouriteContract.FavouriteEntry.COLUMN_MOVIE_TITLE,
                FavouriteContract.FavouriteEntry.COLUMN_MOVIE_USER_RATING,
                FavouriteContract.FavouriteEntry.COLUMN_POSTER_PATH,
                FavouriteContract.FavouriteEntry.COLUMN_MOVIE_OVERVIEW,
        };

        String sortOrder = FavouriteContract.FavouriteEntry._ID + " ASC";
        List<Movie> favouriteList = new ArrayList<>();

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(FavouriteContract.FavouriteEntry.TABLE_NAME, columns, null, null, null, null, sortOrder);

        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();

                movie.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FavouriteContract.FavouriteEntry.COLUMN_MOVIE_ID))));
                movie.setTitle(cursor.getString(cursor.getColumnIndex(FavouriteContract.FavouriteEntry.COLUMN_MOVIE_TITLE)));
                movie.setVoteAverage(Float.parseFloat(cursor.getString(cursor.getColumnIndex(FavouriteContract.FavouriteEntry.COLUMN_MOVIE_USER_RATING))));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndex(FavouriteContract.FavouriteEntry.COLUMN_POSTER_PATH)));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(FavouriteContract.FavouriteEntry.COLUMN_MOVIE_OVERVIEW)));

                favouriteList.add(movie);

            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();

        Log.d(TAG, "getAllFavourite: " + favouriteList);
        return favouriteList;

    }

    public boolean isFavourite(String id) {
        SQLiteDatabase database = this.getReadableDatabase();


        String[] columns = {FavouriteContract.FavouriteEntry._ID, FavouriteContract.FavouriteEntry.COLUMN_MOVIE_ID,
                FavouriteContract.FavouriteEntry.COLUMN_MOVIE_TITLE, FavouriteContract.FavouriteEntry.COLUMN_MOVIE_USER_RATING,
                FavouriteContract.FavouriteEntry.COLUMN_POSTER_PATH, FavouriteContract.FavouriteEntry.COLUMN_MOVIE_OVERVIEW};
//        String Query = "SELECT * FROM " + FavouriteContract.FavouriteEntry.TABLE_NAME + " where " +
//                FavouriteContract.FavouriteEntry.COLUMN_MOVIE_ID + "=?" + fieldValue;

        String Query = "SELECT * FROM " + FavouriteContract.FavouriteEntry.TABLE_NAME + " where FavouriteContract.FavouriteEntry.COLUMN_MOVIE_ID  = ?" + id;

        String selection = FavouriteContract.FavouriteEntry.COLUMN_MOVIE_ID + " =?";
        String[] selectionArgs = new String[]{id};

        Cursor cursor = database.query(FavouriteContract.FavouriteEntry.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
//        Cursor cursor = database.rawQuery(Query, new String[]{fieldValue});
        if (cursor.getCount() <= 0) {
            cursor.close();
            database.close();
            return false;
        }

        cursor.close();
        database.close();
        return true;
    }
}


