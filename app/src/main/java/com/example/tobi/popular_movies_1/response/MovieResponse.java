package com.example.tobi.popular_movies_1.response;

import com.example.tobi.popular_movies_1.data.model.Movie;
import java.util.List;

/**
 * Created by Tobi on 25-Feb-18.
 */

public class MovieResponse {

    public List<Movie> results;

    public List<Movie> getResults() {
        return results;
    }
}
