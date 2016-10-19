package com.elf.elfstudent.model;


/**
 * Created by user on 07-08-2016.
 */


//Lesson data model shown at report page (for each subject)
public class Lesson {

    public String mLessonName;
    public String mGrowthPercentage;
    public boolean isHeader;


    public Lesson(String mLessonName, String mGrowthPercentage) {
        this.mLessonName = mLessonName;
        this.mGrowthPercentage = mGrowthPercentage;
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
