package com.h3solution.timelearner;

import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.google.gson.Gson;
import com.h3solution.listener.ApiIml;
import com.h3solution.listener.BaseListener;
import com.h3solution.model.Question;
import com.h3solution.model.Response;

import java.util.ArrayList;

public class SplashScreenActivity extends BaseActivity {
    final int SPLASH_TIME_OUT = 3000;

    boolean finishOnDone;
    ArrayList<Question> questions;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash_screen;
    }

    @Override
    protected void mainAction() {
        finishOnDone = false;
        questions = new ArrayList<>();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if (questions.size() > 0) {
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    intent.putParcelableArrayListExtra("DATA", questions);
                    startActivity(intent);
                    finish();
                } else {
                    finishOnDone = true;
                }
            }
        }, SPLASH_TIME_OUT);

        new ApiIml().getQuestions(new BaseListener() {
            @Override
            public void onSuccess(String result) {
                Response response = new Gson().fromJson(result, Response.class);
                questions = response.getQuestions();

                if (finishOnDone) {
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    intent.putParcelableArrayListExtra("DATA", questions);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(SplashScreenActivity.this, getResources().getString(R.string.err_fail_to_get_questions), Toast.LENGTH_LONG).show();
            }
        });
    }
}