package com.example.tobi.popular_movies_1.view;

import android.content.Context;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.tobi.popular_movies_1.R;
import com.example.tobi.popular_movies_1.adapter.VideoAdapter;
import com.example.tobi.popular_movies_1.data.model.Videos;
import java.util.List;

public class VideoView {

  private ViewGroup parent;
  private VideoAdapter videoAdapter;

  @BindView(R.id.videoRecyclerView) RecyclerView recyclerView;

  public VideoView(ViewGroup parent) {
    ButterKnife.bind(this, parent);
    this.parent = parent;
    initVideoAdapter();
  }

  public void setVideos(List<Videos> videos, Context context) {
    videoAdapter.setVideos(videos, context);
  }

  private void initVideoAdapter() {
    videoAdapter = new VideoAdapter();
    recyclerView.setHasFixedSize(true);
    LinearLayoutManager layoutManager = new LinearLayoutManager(parent.getContext(), LinearLayoutManager.HORIZONTAL, false);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(videoAdapter);
  }
}
