package com.example.tobi.popular_movies_1;

import android.content.Context;

import java.util.List;

/**
 * Created by Tobi on 25-Mar-18.
 */

public class ReviewView {

    private ReviewAdapter reviewAdapter;

    public ReviewView(ReviewAdapter reviewAdapter) {
        this.reviewAdapter = reviewAdapter;
    }

    public void setReviews(List<Reviews> results, Context context) {
        reviewAdapter.setReviews(results, context);
    }
}
