package com.elf.elfstudent.model;

/**
 * Created by DS on 9/21/2016.
 */

public class StateModel {

    public String stateName;
    public String stateId;

    public StateModel(String stateName, String stateId) {
        this.stateName = stateName;
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    @Override
    public String toString() {
        return stateName;
    }
}
