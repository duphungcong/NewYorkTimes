package com.duphungcong.newyorktimes.api;

import com.duphungcong.newyorktimes.common.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by udcun on 2/22/2017.
 */

public class NYTClient {
    private static Retrofit retrofit = null;

    public static Retrofit getService() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.NYT_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
