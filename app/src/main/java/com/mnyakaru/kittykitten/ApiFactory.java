package com.mnyakaru.kittykitten;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {

    private static final String URL_CATS = "https://api.thecatapi.com/v1/images/";
    private static final String URL_CAT_FACTS = "https://catfact.ninja/";
    private static ApiService apiServiceCat;
    private static ApiService apiServiceFact;

    public static ApiService getApiServiceCat(){
        if (apiServiceCat == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL_CATS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
            apiServiceCat = retrofit.create(ApiService.class);
        }
        return apiServiceCat;
    }
    public static ApiService getApiServiceFact(){
        if (apiServiceFact == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL_CAT_FACTS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
            apiServiceFact = retrofit.create(ApiService.class);
        }
        return apiServiceFact;
    }
}
