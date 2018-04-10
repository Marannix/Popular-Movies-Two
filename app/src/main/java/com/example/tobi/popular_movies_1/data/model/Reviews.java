package com.example.tobi.popular_movies_1.data.model;

/**
 * Created by Tobi on 25-Mar-18.
 */

public class Reviews {

    private String author;
    private String content;

    public Reviews(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
