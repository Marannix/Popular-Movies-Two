package com.example.tobi.popular_movies_1.data.model;

public class Videos {

    private String key;
    private String name;

    public Videos(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}
