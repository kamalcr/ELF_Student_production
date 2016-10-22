package com.elf.elfstudent.model;

/**
 * Created by nandhu on 17/8/16.
 * The Subject Model
 */
public class SubjectModel {
    private String mSubjectName;
    private String mSubjectId;
    private String mPercentage;

    public SubjectModel(String mSubjectName, String mSubjectId, String mPercentage) {
        this.mSubjectName = mSubjectName;
        this.mSubjectId = mSubjectId;
        this.mPercentage = mPercentage;
    }

    public String getmSubjectName() {
        return mSubjectName;
    }

    public void setmSubjectName(String mSubjectName) {
        this.mSubjectName = mSubjectName;
    }

    public String getmSubjectId() {
        return mSubjectId;
    }

    public void setmSubjectId(String mSubjectId) {
        this.mSubjectId = mSubjectId;
    }

    public String getmPercentage() {
        return mPercentage;
    }

    public void setmPercentage(String mPercentage) {
        this.mPercentage = mPercentage;
    }
}
