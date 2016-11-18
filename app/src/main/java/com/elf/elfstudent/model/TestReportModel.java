package com.elf.elfstudent.model;



/**
 * Created by nandhu on 3/10/16.
 *
 * The Model For TestReport Objects
 *
 *
 *  thisis simple POJO class which forms the list of Test Objects
 *  to be supplied as Model to {@link com.elf.elfstudent.Activities.TestReportsActivity}
 *
 *
 *
 */
public class TestReportModel {
    public String testId;
    public String subjectId;
    public String subjectName;
    public String overAll;
    public String description;



    public TestReportModel(String testId, String description, String subjectId, String marksScored) {
        this.testId = testId;
        this.overAll = marksScored;
        this.subjectId = subjectId;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }


    public String getOverAll() {
        return overAll;
    }

    public void setOverAll(String overAll) {
        this.overAll = overAll;
    }


    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
