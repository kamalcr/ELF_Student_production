package com.elf.elfstudent.model;

/**
 * Created by nandhu on 24/8/16.
 */
public class PublicTestModel {

    public String mTestId;
    public String mTestDetail;
    public String mSubjectName;
    public String mTotalQuestions;

    public String getmTestId() {
        return mTestId;
    }

    public void setmTestId(String mTestId) {
        this.mTestId = mTestId;
    }

    public String getmTestDetail() {
        return mTestDetail;
    }

    public void setmTestDetail(String mTestDetail) {
        this.mTestDetail = mTestDetail;
    }

    public String getmSubjectName() {
        return mSubjectName;
    }

    public void setmSubjectName(String mSubjectName) {
        this.mSubjectName = mSubjectName;
    }

    public String getmTotalQuestions() {
        return mTotalQuestions;
    }

    public void setmTotalQuestions(String mTotalQuestions) {
        this.mTotalQuestions = mTotalQuestions;
    }

    public PublicTestModel(String mTestId, String mTestDetail, String mSubjectName, String mTotalQuestions) {

        this.mTestId = mTestId;
        this.mTestDetail = mTestDetail;
        this.mSubjectName = mSubjectName;
        this.mTotalQuestions = mTotalQuestions;
    }
}
