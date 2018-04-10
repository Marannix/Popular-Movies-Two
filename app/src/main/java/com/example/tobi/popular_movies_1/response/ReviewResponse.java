package com.example.tobi.popular_movies_1.response;

import com.example.tobi.popular_movies_1.data.model.Reviews;
import java.util.List;

/**
 * Created by Tobi on 25-Mar-18.
 */

public class ReviewResponse {

    public List<Reviews> results;

    public List<Reviews> getResults() {
        return results;
    }

}
