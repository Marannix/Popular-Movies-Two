package com.example.tobi.popular_movies_1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

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

    private Movie object;

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

        Picasso.with(context).load(intent.getStringExtra("path")).into(imageView);
        title.setText(object.getTitle());
        overview.setText(object.getOverview());
        releaseDate.setText(object.getReleaseDate());

        reviewAdapter = new ReviewAdapter();
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(reviewAdapter);
        recyclerView.setLayoutManager(layoutManager);
        reviewView = new ReviewView(reviewAdapter);

        retrieveReviews();
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
}
