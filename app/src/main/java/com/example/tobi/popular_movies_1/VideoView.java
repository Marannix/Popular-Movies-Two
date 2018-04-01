package com.example.tobi.popular_movies_1;

import android.content.Context;

import java.util.List;

public class VideoView {

    private VideoAdapter videoAdapter;
//    private LinearLayoutManager layoutManager;
//    private RecyclerView recyclerView;
//
    public VideoView(VideoAdapter videoAdapter) {
        this.videoAdapter = videoAdapter;
    }

    public void setVideos(List<Videos> videos, Context context) {
//        setupAdapter(context);
        videoAdapter.setVideos(videos, context);
    }

//    private void setupAdapter(Context context) {
//        videoAdapter = new VideoAdapter();
//        recyclerView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(context);
//        recyclerView.setAdapter(videoAdapter);
//        recyclerView.setLayoutManager(layoutManager);
//    }
}
