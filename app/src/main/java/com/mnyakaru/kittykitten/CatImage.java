package com.mnyakaru.kittykitten;

import androidx.annotation.NonNull;

public class CatImage {
    @NonNull
    @Override
    public String toString() {
        return "CatImage{" +
                "id=" + id +
                ", url='" + url + '\'' +
                '}';
    }

    private final String id;
    private final String url;

    public CatImage(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
