package com.elf.elfstudent.model;

/**
 * Created by nandhu on 20/10/16.
 */

public class StandardModel {
    public String name ;
    public String classID;

    public StandardModel(String name, String stdId) {
        this.name = name;
        this.classID = stdId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }
}
