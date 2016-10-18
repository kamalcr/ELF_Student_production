package com.elf.elfstudent.model;

import android.graphics.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by nandhu on 25/8/16.
 *
 * The Rest Model To send it to server
 * on Completion of test
 *  {@param Student ID }
 *
 *  {@param test id}
 *
 *               {@param HashMap for storing question iD and Associated answer the user has clicked}
 *
 */
public class TestSubmit {
    String studnetID;
    String testId;
    private HashMap<String ,String> questionSet;
    int questionCount;

    public void setStudnetID(String studnetID) {
        this.studnetID = studnetID;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public TestSubmit(String studnetID, String testId, int qCount) {
        this.studnetID = studnetID;
        this.questionCount = qCount;
        this.testId = testId;

        questionSet= new HashMap<>(qCount);
    }


    public HashMap<String, String> getQuestionSet() {
        return questionSet;
    }

    public void setAnswerForQuestion(String questionId, String optionSelected){
        if (questionSet != null){

            questionSet.put(questionId,optionSelected);
        }

    }

    public String getStudnetID() {
        return studnetID;
    }

    public String getTestId() {
        return testId;
    }
}
