package com.example.tobi.popular_movies_1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tobi on 25-Feb-18.
 */

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {
    private Context context;
    private List<Movie> movies;
    private String movieUrl = "https://image.tmdb.org/t/p/";
    private String phoneSize = "w500";

    public void setMovieData(List<Movie> movies, Context context) {
        this.movies = movies;
        this.context = context;
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Movie movie = movies.get(position);
        final String path = movieUrl + phoneSize + movie.getPosterPath();

        Picasso.with(context).load(path).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Movie dataToSend = new Movie(movie.getId(), movie.getTitle(), movie.getOverview(),
                        movie.getPosterPath(), movie.getVoteCount(), movie.getVoteAverage(),
                        movie.getReleaseDate());
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("myDataKey", dataToSend);
                intent.putExtra("path", path);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies != null ? movies.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView)
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
