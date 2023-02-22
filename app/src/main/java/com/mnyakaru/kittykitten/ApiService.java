package com.mnyakaru.kittykitten;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface ApiService {

    @GET("search")
    Single<List<CatImage>> loadCatImage();
    @GET("fact")
    Single<CatFact> loadCatFact();
}
