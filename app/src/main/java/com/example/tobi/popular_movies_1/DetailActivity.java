package com.example.tobi.popular_movies_1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "Stuff";
    private Context context;
    private ImageView imageView;
    private TextView overview;
    private TextView releaseDate;
    private TextView title;

    private ReviewView reviewView;
    private ReviewAdapter reviewAdapter;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private ApiModule apiModule;

    private VideoView videoView;
    private VideoAdapter videoAdapter;
    private RecyclerView videoRecyclerView;
    private LinearLayoutManager videoLayoutManager;

    private Movie object;

    private FavouriteDbHelper favouriteDbHelper;
    private final AppCompatActivity activity = DetailActivity.this;

    Button favouriteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        context = getApplicationContext();
        object = getIntent().getParcelableExtra("myDataKey");
        Intent intent = getIntent();
        apiModule = new ApiModule();
        imageView = findViewById(R.id.detailImageView);
        overview = findViewById(R.id.detailOverview);
        releaseDate = findViewById(R.id.detailReleaseDate);
        title = findViewById(R.id.detailTitle);
        recyclerView = findViewById(R.id.reviewRecyclerView);
        videoRecyclerView = findViewById(R.id.videoRecyclerView);


        // REMOVE THIS FROM HERE
        favouriteButton = findViewById(R.id.favouriteButton);

        Picasso.with(context).load(intent.getStringExtra("path")).into(imageView);
        title.setText(object.getTitle());
        overview.setText(object.getOverview());
        releaseDate.setText(object.getReleaseDate());

        // REFACTOR THIS, AWFUL WHAT WAS I THINKING
        reviewAdapter = new ReviewAdapter();
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(reviewAdapter);
        recyclerView.setLayoutManager(layoutManager);
        reviewView = new ReviewView(reviewAdapter);

        // REFACTOR THIS, AWFUL WHAT WAS I THINKING
        videoLayoutManager = new LinearLayoutManager(this);
        videoAdapter = new VideoAdapter();
        videoRecyclerView.setHasFixedSize(true);
        videoRecyclerView.setAdapter(videoAdapter);
        videoRecyclerView.setLayoutManager(videoLayoutManager);
        videoView = new VideoView(videoAdapter);

        retrieveReviews();
        retrieveVideos();
        favouriteStuff();
    }


    private void retrieveReviews() {
        Call<ReviewResponse> reviewResponseCall = apiModule.movieApi().getMovieReviews(object.getId());

        reviewResponseCall.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if (response.isSuccessful()) {
                    ReviewResponse reviewResponse = response.body();
                    reviewView.setReviews(reviewResponse.getResults(), context);

                    Log.d("RIP", "onResponse: " + reviewResponse.getResults());
                } else {
                    Toast.makeText(context, "not working", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void retrieveVideos() {
        Call<VideoResponse> videoResponseCall = apiModule.movieApi().getMovieVideos(object.getId());

        videoResponseCall.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                if (response.isSuccessful()) {
                    VideoResponse videoResponse = response.body();
                    videoView.setVideos(videoResponse.getResults(), context);

                }
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {

            }
        });
    }

    private void favouriteStuff() {
        favouriteDbHelper = new FavouriteDbHelper(activity);

        favouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (!favouriteDbHelper.isFavourite(String.valueOf(object.getId()))) {
                        SharedPreferences.Editor editor = getSharedPreferences(".DetailActivity", MODE_PRIVATE).edit();
                        editor.putBoolean("Favourite Added", true);
                        editor.apply();
                        favouriteDbHelper.addFavourite(object);
                        Toast.makeText(context, "Added to Favourite", Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences.Editor editor = getSharedPreferences(".DetailActivity", MODE_PRIVATE).edit();
                        favouriteDbHelper = new FavouriteDbHelper(DetailActivity.this);
                        favouriteDbHelper.removeFavourite(object.getId());
                        editor.putBoolean("Favourite Removed", true);
                        editor.apply();
                        Toast.makeText(context, "Removed From Favourite", Toast.LENGTH_SHORT).show();
                    }

                }
        });
    }

}
