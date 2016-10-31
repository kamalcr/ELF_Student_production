package com.elf.elfstudent.model;

/**
 * Created by user on 09-08-2016.
 */
public class Question {

    public String mQuestion;
    public String mOpt_a;
    public String mOpt_b;
    public String mOpt_c;
    public String mOpt_d;
    public String mQuestionId;

    public boolean isSelected =false;
    public String selectedOption = "null";

    public Question(boolean isSelected, String selectedOption) {
        this.isSelected = isSelected;
        this.selectedOption = selectedOption;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public String getmQuestionId() {
        return mQuestionId;
    }

    public void setmQuestionId(String mQuestionId) {
        this.mQuestionId = mQuestionId;
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

    public Question(String mQuestionId,String mQuestion, String mOpt_a, String mOpt_b, String mOpt_c, String mOpt_d, String mCorrect,boolean isSelected) {
        this.mQuestionId  = mQuestionId;
        this.mQuestion = mQuestion;
        this.isSelected = isSelected;
        this.mOpt_a = mOpt_a;
        this.mOpt_b = mOpt_b;
        this.mOpt_c = mOpt_c;
        this.mOpt_d = mOpt_d;

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


}
