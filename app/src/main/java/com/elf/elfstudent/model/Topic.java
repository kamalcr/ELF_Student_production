package com.elf.elfstudent.model;

/**
 * Created by nandhu on 20/10/16.
 *
 */



public class Topic {

private String topicName;
    private String points;
    public String questionAsked ;
    public String correctanswer;

    public Topic(String topicName, String points) {
        this.topicName = topicName;
        this.points = points;
    }

    public Topic(String topicName, String points, String questionAsked, String correctanswer) {
        this.topicName = topicName;
        this.points = points;
        this.questionAsked = questionAsked;
        this.correctanswer = correctanswer;
    }

    public String getQuestionAsked() {
        return questionAsked;
    }

    public void setQuestionAsked(String questionAsked) {
        this.questionAsked = questionAsked;
    }

    public String getCorrectanswer() {
        return correctanswer;
    }

    public void setCorrectanswer(String correctanswer) {
        this.correctanswer = correctanswer;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}