package com.elf.elfstudent.model;

/**
 * Created by nandhu on 23/8/16.
 */
public class AllTestModels {
    public String mTestId;
    public String mTestDetail;
    public String mSubjectName;
    public String mSubjectId;

//ParticularTestDetails
//{
// Subject Name
// TestID
// Time
 //Nos of Questions
// }
    public AllTestModels(String mTestId, String mTestDetail, String mSubjectName, String subId) {
        this.mTestId = mTestId;
        this.mTestDetail = mTestDetail;
        this.mSubjectName = mSubjectName;
        this.mSubjectId =  subId;

    }

    public String getmSubjectId() {
        return mSubjectId;
    }

    public void setmSubjectId(String mSubjectId) {
        this.mSubjectId = mSubjectId;
    }

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


}
