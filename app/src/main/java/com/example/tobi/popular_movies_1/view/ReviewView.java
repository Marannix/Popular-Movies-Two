package com.example.tobi.popular_movies_1.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.tobi.popular_movies_1.R;
import com.example.tobi.popular_movies_1.adapter.ReviewAdapter;
import com.example.tobi.popular_movies_1.data.model.Reviews;
import java.util.List;

/**
 * Created by Tobi on 25-Mar-18.
 */

public class ReviewView {

  private ViewGroup parent;
  private ReviewAdapter reviewAdapter;

  @BindView(R.id.reviewRecyclerView) RecyclerView recyclerView;

  public ReviewView(ViewGroup parent) {
    ButterKnife.bind(this, parent);
    this.parent = parent;
    initReviewAdapter();
  }

  public void setReviews(List<Reviews> results) {
    reviewAdapter.setReviews(results);
  }

  private void initReviewAdapter() {
    reviewAdapter = new ReviewAdapter();
    recyclerView.setHasFixedSize(true);
    LinearLayoutManager layoutManager = new LinearLayoutManager(parent.getContext());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(reviewAdapter);
  }
}
