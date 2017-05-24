package com.h3solution.timelearner;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.h3solution.model.Question;
import com.h3solution.view.ClockView;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    final int DIALOG_TIME_OUT = 1000;

    @BindView(R.id.txt_score)
    TextView txtScore;
    @BindView(R.id.txt_question)
    TextView txtQuestion;
    @BindView(R.id.btnAnswer1)
    Button btnAnswer1;
    @BindView(R.id.btnAnswer2)
    Button btnAnswer2;
    @BindView(R.id.btnAnswer3)
    Button btnAnswer3;
    @BindView(R.id.ll_clock_place)
    LinearLayout llClockPlace;
    @BindView(R.id.txt_day_night)
    TextView txtDayNight;

    int currentScore;
    int currentQuestion;
    int totalQuestions;
    String currentAnswer;
    ArrayList<Question> questions;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void mainAction() {
        currentScore = 0;
        currentQuestion = 0;
        totalQuestions = 0;
        currentAnswer = "";

        questions = getIntent().getParcelableArrayListExtra("DATA");
        totalQuestions = questions.size();

        setCurrentQuestion(questions.get(currentQuestion));
    }

    private void setCurrentQuestion(Question question) {
        txtScore.setText(getResources().getString(R.string.score, currentScore));
        txtQuestion.setText(getResources().getString(R.string.question, currentQuestion + 1, totalQuestions));

        try {
            currentAnswer = question.getTime_to_display();
            String[] split = question.getTime_to_display().split(":");

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR, Integer.parseInt(split[0]));
            calendar.set(Calendar.MINUTE, Integer.parseInt(split[1]));

            txtDayNight.setText(Integer.parseInt(split[0]) >= 12 ? "PM" : "AM");

            ClockView clockView = new ClockView(this, calendar);
            llClockPlace.addView(clockView);

            btnAnswer1.setText(question.getOptions()[0]);
            btnAnswer2.setText(question.getOptions()[1]);
            btnAnswer3.setText(question.getOptions()[2]);
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.err_fail_to_get_questions), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @OnClick({R.id.btnAnswer1, R.id.btnAnswer2, R.id.btnAnswer3})
    public void onViewClicked(View view) {
        Button clicked = (Button) view;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View v = View.inflate(this, R.layout.dialog_alert, null);
        TextView txtQuestionResult = (TextView) v.findViewById(R.id.txt_question_result);
        RelativeLayout rlDialog = (RelativeLayout) v.findViewById(R.id.rl_dialog);

        if (clicked.getText().equals(currentAnswer)) {
            txtQuestionResult.setText(getResources().getString(R.string.correct));
            rlDialog.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_green_dark));
            currentScore++;
        } else {
            txtQuestionResult.setText(getResources().getString(R.string.incorrect));
            rlDialog.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_red_dark));
        }

        builder.setView(v);

        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                alertDialog.dismiss();
                updateNextQuestion();
            }
        }, DIALOG_TIME_OUT);
    }

    private void updateNextQuestion() {
        if ((currentQuestion + 1) == totalQuestions) {
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra("RESULT", currentScore);
            startActivity(intent);
            finish();
        } else {
            currentQuestion++;
            currentAnswer = "";

            llClockPlace.removeViewAt(1);
            setCurrentQuestion(questions.get(currentQuestion));
        }
    }
}