package com.elf.elfstudent.model;

/**
 * Created by nandhu on 20/10/16.
 */

public class StandardModel {
    public String name ;
    public String stdId ;

    public StandardModel(String name, String stdId) {
        this.name = name;
        this.stdId = stdId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStdId() {
        return stdId;
    }

    public void setStdId(String stdId) {
        this.stdId = stdId;
    }
}
