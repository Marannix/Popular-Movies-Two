package com.example.tobi.popular_movies_1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tobi on 25-Mar-18.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    // remove context if not needed...
    private Context context;
    private List<Reviews> reviews;

    public void setReviews(List<Reviews> reviews, Context context) {
        this.reviews = reviews;
        this.context = context;
        this.notifyDataSetChanged();
    }

    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false));

    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ViewHolder holder, int position) {
        final Reviews review = reviews.get(position);
        holder.author.setText(review.getAuthor());
        Log.d("RIPPPPPP", "onBindViewHolder: " + review.getAuthor());
        holder.content.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return reviews != null ? reviews.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.reviewAuthor)
        TextView author;
        @BindView(R.id.reviewContent)
        TextView content;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
