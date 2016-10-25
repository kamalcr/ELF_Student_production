package com.elf.elfstudent.model;


/**
 * Created by user on 07-08-2016.
 */


//Lesson data model shown at report page (for each subject)
public class Lesson {

    public String mLessonName;
    public String lessonId;

    public Lesson(String mLessonName, String lessonId, String mGrowthPercentage, String questionAsked, String correctanswer) {
        this.mLessonName = mLessonName;
        this.lessonId = lessonId;
        this.mGrowthPercentage = mGrowthPercentage;
        this.questionAsked = questionAsked;
        this.correctanswer = correctanswer;
    }

    public String mGrowthPercentage;
    public String questionAsked ;
    public String correctanswer;

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
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

    public String getmLessonName() {
        return mLessonName;
    }

    public void setmLessonName(String mLessonName) {
        this.mLessonName = mLessonName;
    }

    public String getmGrowthPercentage() {
        return mGrowthPercentage;
    }

    public void setmGrowthPercentage(String mGrowthPercentage) {
        this.mGrowthPercentage = mGrowthPercentage;
    }
}
