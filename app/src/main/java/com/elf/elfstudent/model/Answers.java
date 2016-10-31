package com.elf.elfstudent.model;

import android.util.Log;

/**
 * Created by Nandha on 9/29/2016.
 *
 */
public class Answers {

    public String mQuestion;

    public String Correctoption;
    public String AnswerStatus;

    public Answers(String mQuestion, String correctoption, String answerStatus) {
        this.mQuestion = mQuestion;
        Correctoption = correctoption;
        AnswerStatus = answerStatus;
    }

    public String getmQuestion() {
        return mQuestion;
    }

    public void setmQuestion(String mQuestion) {
        this.mQuestion = mQuestion;
    }

    public String getCorrectoption() {
        return Correctoption;
    }

    public void setCorrectoption(String correctoption) {
        Correctoption = correctoption;
    }

    public String getAnswerStatus() {
        return AnswerStatus;
    }

    public void setAnswerStatus(String answerStatus) {
        AnswerStatus = answerStatus;
    }
}
