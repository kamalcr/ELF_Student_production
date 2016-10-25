package com.elf.elfstudent.model;

/**
 * Created by nandhu on 20/10/16.
 *
 */
public class Topic {
    private String topicName;
    //Change to Percentage from Points
    private String points;
    //Growth Icon(Example 0-25 'Yellow' 25-50 'Orange'

    public Topic(String topicName, String points) {
        this.topicName = topicName;
        this.points = points;
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
