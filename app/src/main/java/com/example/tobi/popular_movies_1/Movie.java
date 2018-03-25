package com.example.tobi.popular_movies_1;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tobi on 25-Feb-18.
 */

public class Movie implements Parcelable {

    private int id;
    private String title;
    private String overview;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("vote_count")
    private int voteCount;
    @SerializedName("vote_average")
    private float voteAverage;
    @SerializedName("release_date")
    private String releaseDate;

    public Movie(int id, String title, String overview, String posterPath, int voteCount,
                 float voteAverage, String releaseDate) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.posterPath = posterPath;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.posterPath);
        dest.writeInt(this.voteCount);
        dest.writeFloat(this.voteAverage);
        dest.writeString(this.releaseDate);
    }

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.overview = in.readString();
        this.posterPath = in.readString();
        this.voteCount = in.readInt();
        this.voteAverage = in.readFloat();
        this.releaseDate = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
