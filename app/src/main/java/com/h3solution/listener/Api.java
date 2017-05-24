package com.h3solution.listener;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Api
 * Created by HHHai on 16-05-2017.
 */
public interface Api {
    @GET("questions")
    Call<ResponseBody> getQuestions();
}