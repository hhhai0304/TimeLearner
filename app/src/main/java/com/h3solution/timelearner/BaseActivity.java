package com.h3solution.timelearner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Base for Activities
 * Created by HHHai on 16-05-2017.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG = getClass().getSimpleName();
    protected abstract int getLayoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        assert getSupportActionBar() != null;
        getSupportActionBar().hide();
        ButterKnife.bind(this);

        mainAction();
    }

    protected void mainAction() {

    }
}