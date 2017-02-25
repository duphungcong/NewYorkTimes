package com.duphungcong.newyorktimes.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by udcun on 2/22/2017.
 */

public interface NYTService {
    @GET("articlesearch.json")
    Call<NYTResponse> getArticleSearch(@Query("api-key") String apiKey,
                                       @Query("q") String q);
}
