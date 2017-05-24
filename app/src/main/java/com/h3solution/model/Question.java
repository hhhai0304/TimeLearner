package com.h3solution.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model of a question
 * Created by HHHai on 16-05-2017.
 */
public class Question implements Parcelable {
    private String time_to_display;
    private String[] options;

    public Question() {
    }

    public Question(String time_to_display, String[] options) {
        this.time_to_display = time_to_display;
        this.options = options;
    }

    public String getTime_to_display() {
        return time_to_display;
    }

    public void setTime_to_display(String time_to_display) {
        this.time_to_display = time_to_display;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.time_to_display);
        dest.writeStringArray(this.options);
    }

    protected Question(Parcel in) {
        this.time_to_display = in.readString();
        this.options = in.createStringArray();
    }

    public static final Parcelable.Creator<Question> CREATOR = new Parcelable.Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel source) {
            return new Question(source);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
}