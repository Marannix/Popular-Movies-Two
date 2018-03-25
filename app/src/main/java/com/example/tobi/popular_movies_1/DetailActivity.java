package com.example.tobi.popular_movies_1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private Context context;
    private ImageView imageView;
    private TextView overview;
    private TextView releaseDate;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        context = getApplicationContext();
        Movie object = getIntent().getParcelableExtra("myDataKey");
        Intent intent = getIntent();

        imageView = findViewById(R.id.detailImageView);
        overview = findViewById(R.id.detailOverview);
        releaseDate = findViewById(R.id.detailReleaseDate);
        title = findViewById(R.id.detailTitle);

        Picasso.with(context).load(intent.getStringExtra("path")).into(imageView);
        title.setText(object.getTitle());
        overview.setText(object.getOverview());
        releaseDate.setText(object.getReleaseDate());
    }
}
