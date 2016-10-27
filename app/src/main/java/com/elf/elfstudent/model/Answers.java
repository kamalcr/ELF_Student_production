package com.elf.elfstudent.model;

import android.util.Log;

/**
 * Created by Nandha on 9/29/2016.
 *
 */
public class Answers {

    public String mQuestion;
    public String mOpt_a;
    public String mOpt_b;
    public String mOpt_c;
    public String mOpt_d;
    String Correctoption;

    public Answers(String mQuestion, String mOpt_a, String mOpt_b, String mOpt_c, String mOpt_d, String correctoption) {
        this.mQuestion = mQuestion;
        this.mOpt_a = mOpt_a;
        this.mOpt_b = mOpt_b;
        this.mOpt_c = mOpt_c;
        this.mOpt_d = mOpt_d;
        Correctoption = correctoption;
    }

    public String getmQuestion() {
        return mQuestion;
    }

    public void setmQuestion(String mQuestion) {
        this.mQuestion = mQuestion;
    }

    public String getmOpt_a() {
        return mOpt_a;
    }

    public void setmOpt_a(String mOpt_a) {
        this.mOpt_a = mOpt_a;
    }

    public String getmOpt_b() {
        return mOpt_b;
    }

    public void setmOpt_b(String mOpt_b) {
        this.mOpt_b = mOpt_b;
    }

    public String getmOpt_c() {
        return mOpt_c;
    }

    public void setmOpt_c(String mOpt_c) {
        this.mOpt_c = mOpt_c;
    }

    public String getmOpt_d() {
        return mOpt_d;
    }

    public void setmOpt_d(String mOpt_d) {
        this.mOpt_d = mOpt_d;
    }

    public String getCorrectoption() {
        return Correctoption;
    }

    public void setCorrectoption(String correctoption) {
        Correctoption = correctoption;
    }
}
