package com.h3solution.listener;

/**
 * BaseListener for API
 * Created by HHHai on 16-05-2017.
 */
public interface BaseListener {
    void onSuccess(String result);
    void onFailure(Exception e);
}