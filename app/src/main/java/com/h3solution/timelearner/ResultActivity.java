package com.h3solution.timelearner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.h3solution.listener.ApiIml;
import com.h3solution.listener.BaseListener;
import com.h3solution.model.Question;
import com.h3solution.model.Response;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class ResultActivity extends BaseActivity {

    @BindView(R.id.txt_points)
    TextView txtPoints;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_result;
    }

    @Override
    protected void mainAction() {
        int points = getIntent().getIntExtra("RESULT", 0);
        txtPoints.setText(getResources().getString(R.string.result, points));
    }

    @OnClick(R.id.btn_play_again)
    public void onViewClicked() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();

        new ApiIml().getQuestions(new BaseListener() {
            @Override
            public void onSuccess(String result) {
                Response response = new Gson().fromJson(result, Response.class);
                ArrayList<Question> questions = response.getQuestions();
                progressDialog.dismiss();
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                intent.putParcelableArrayListExtra("DATA", questions);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(ResultActivity.this, getResources().getString(R.string.err_fail_to_get_questions), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }
}