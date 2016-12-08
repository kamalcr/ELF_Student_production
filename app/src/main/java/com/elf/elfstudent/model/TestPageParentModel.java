package com.elf.elfstudent.model;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.bignerdranch.expandablerecyclerview.Model.ParentWrapper;

import java.util.List;

/**
 * Created by nandhu on 2/12/16.
 *
 */

public class TestPageParentModel implements ParentListItem{


    private String subjectId;
    private List<AllTestModels> mList;

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public List<AllTestModels> getmList() {
        return mList;
    }

    public void setmList(List<AllTestModels> mList) {
        this.mList = mList;
    }

    public TestPageParentModel(String subjectId , List<AllTestModels> mList) {
        this.mList = mList;
        this.subjectId   = subjectId;
    }

    @Override
    public List<AllTestModels> getChildItemList() {
        return mList;
    }

    private String getTestId(int pos){
        return mList.get(pos).getmTestId();
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}