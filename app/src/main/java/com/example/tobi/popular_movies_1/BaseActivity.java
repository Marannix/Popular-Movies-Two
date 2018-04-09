package com.example.tobi.popular_movies_1;

import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

public class BaseActivity extends AppCompatActivity {

  public ViewGroup getViewGroup() {
    return (ViewGroup) findViewById(android.R.id.content);
  }
}
