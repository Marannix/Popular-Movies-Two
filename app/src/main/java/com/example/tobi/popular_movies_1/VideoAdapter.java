package com.example.tobi.popular_movies_1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private final static String YOUTUBE_URL = "https://www.youtube.com/watch?v=";
    private Context context;
    private List<Videos> videos;
    private String trailerUrl;

    public void setVideos(List<Videos> videos, Context context) {
        this.context = context;
        this.videos = videos;
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Videos video = videos.get(position);
        holder.title.setText(video.getName());
        trailerUrl = YOUTUBE_URL + video.getKey();

        holder.videoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
                context.startActivity(viewIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videos == null ? 0 : videos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.videoTitle)
        TextView title;
        @BindView(R.id.videoLayout)
        LinearLayout videoLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
