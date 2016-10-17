package com.elf.elfstudent.model;



/**
 * Created by nandhu on 3/10/16.
 *
 * The Model For TestReport Objects
 *
 *
 *  thisis simple POJO class which forms the list of Test Objects
 *  to be supplied as Model to
 */
public class TestReportModel {
    public String testId;
    public String testDescription;
    public String subjectName;
    public String overAll;



    public TestReportModel(String testId, String testDescription, String subjectName,String overall) {
        this.testId = testId;
        this.overAll = overall;
        this.testDescription = testDescription;
        this.subjectName = subjectName;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getTestDescription() {
        return testDescription;
    }

    public String getOverAll() {
        return overAll;
    }

    public void setOverAll(String overAll) {
        this.overAll = overAll;
    }

    public void setTestDescription(String testDescription) {
        this.testDescription = testDescription;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
