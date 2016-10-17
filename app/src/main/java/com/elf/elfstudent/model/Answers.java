package com.elf.elfstudent.model;

import android.util.Log;

/**
 * Created by Nandha on 9/29/2016.
 */
public class Answers {
    String questionId;
    String OptionSelected;
    boolean isOptionSelected = false;


    private static final String TAG = "ANSWER MODEL";
    public Answers(String questionId, String optionSelected, boolean isOptionSelected) {

        Log.d(TAG, "Answers: Added question Added "+questionId+ " OPtion selected:"+optionSelected);
        this.questionId = questionId;
        OptionSelected = optionSelected;
        this.isOptionSelected = isOptionSelected;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getOptionSelected() {
        return OptionSelected;
    }

    public void setOptionSelected(String optionSelected) {
        OptionSelected = optionSelected;
    }

    public boolean isOptionSelected() {
        return isOptionSelected;
    }

    public void setOptionSelected(boolean optionSelected) {
        isOptionSelected = optionSelected;
    }

    @Override
    public boolean equals(Object obj) {
        Log.d(TAG, "equals: ");
        return obj != null && obj instanceof Question && ((Question) obj).getmQuestionId() == this.getQuestionId();
    }
}
