package com.labs.newsapi;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {

    @GET("v2/top-headlines")
    Observable<NewsResponse> getTopHeadlines(@Query("country") String country, @Query("category") String category, @Query("apiKey") String apiKey);

}
