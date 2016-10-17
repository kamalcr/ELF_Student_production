package com.elf.elfstudent.model;

/**
 * Created by nandhu on 15/9/16.
 */
public class TestResult {
    String testId;
    String questionId;
    String optionselected;

    public TestResult(String testId, String questionId, String optionselected, boolean selected) {
        this.testId = testId;
        this.questionId = questionId;
        this.optionselected = optionselected;
        this.selected = selected;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getOptionselected() {
        return optionselected;
    }

    public void setOptionselected(String optionselected) {
        this.optionselected = optionselected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    boolean selected;
}


