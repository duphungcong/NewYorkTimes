package com.duphungcong.newyorktimes.api;

import com.duphungcong.newyorktimes.model.Response;
import com.google.gson.annotations.SerializedName;

/**
 * Created by udcun on 2/22/2017.
 */

public class NYTResponse {
    @SerializedName("response")
    private Response response;

    public Response getResponse() {
        return response;
    }
}
