package com.h3solution.listener;

import android.support.annotation.NonNull;
import android.util.Log;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Use Retrofit
 * Created by HHHai on 16-05-2017.
 */
public class ApiIml {
    private final String TAG = getClass().getSimpleName();
    private Api api;

    public ApiIml() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://time-learner.herokuapp.com/")
                .build();

        api = retrofit.create(Api.class);
    }

    public void getQuestions(final BaseListener listener) {
        Call<ResponseBody> call = api.getQuestions();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();
                        Log.d(TAG, result);
                        listener.onSuccess(result);
                    } catch (Exception e) {
                        Log.e(TAG + " onResponse", e.getMessage());
                        listener.onFailure(e);
                    }
                } else {
                    Log.e(TAG + " isSuccessful", "Fail");
                    listener.onFailure(new Exception());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e(TAG + " onFailure", t.getMessage());
                listener.onFailure(new Exception(t));
            }
        });
    }
}