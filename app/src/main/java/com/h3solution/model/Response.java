package com.h3solution.model;

import java.util.ArrayList;

/**
 * Response model from backend
 * Created by HHHai on 16-05-2017.
 */
public class Response {
    private String version;
    private ArrayList<Question> questions;

    public Response() {
    }

    public Response(String version, ArrayList<Question> questions) {
        this.version = version;
        this.questions = questions;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
}