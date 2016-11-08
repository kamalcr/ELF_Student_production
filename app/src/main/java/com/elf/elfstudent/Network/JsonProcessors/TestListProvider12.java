package com.elf.elfstudent.Network.JsonProcessors;

import com.android.volley.Response;
import com.elf.elfstudent.model.AllTestModels;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 20/10/16.
 *
 */

public class TestListProvider12 implements Response.Listener<JSONArray>{
    private List<AllTestModels> mPhysics = new ArrayList<>();
    private List<AllTestModels> mChem = new ArrayList<>();
    private List<AllTestModels> mMaths = new ArrayList<>();
    private List<AllTestModels> moptional = new ArrayList<>();

    TestProviderCallback12 mCallback = null;


    String subjectName = null;
    public TestListProvider12(TestProviderCallback12 mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onResponse(JSONArray response) {
        int count = response.length();


        if (count == 0){
            mCallback.NoTestListData();
        }
        else{
            String subId = null;
            JSONObject mobject = null;
            try {
                for (int i = 0; i < count; i++) {
                    mobject = response.getJSONObject(0);
                    //Get Subject Id
                    subId = mobject.getString("SubjectId");
//                // TODO: 26/10/16 find subject iD
                    //based on subject iD, add it to appropriate list
                    if (subId.equals("2")){
                        //Science Subject
                        mPhysics.add(new AllTestModels(mobject.getString("TestId"),
                                mobject.getString("Description")
                                , mobject.getString("SubjectName"),
                                mobject.getString("SubjectId")));
                    }
                    else if(subId.equals("3")){
                        mChem.add(new AllTestModels(mobject.getString("TestId"),
                                mobject.getString("Description")
                                , mobject.getString("SubjectName"),
                                mobject.getString("SubjectId")));
                    }
                    else if(subId.equals("3")){
                        mMaths.add(new AllTestModels(mobject.getString("TestId"),
                                mobject.getString("Description")
                                , mobject.getString("SubjectName"),
                                mobject.getString("SubjectId")));
                    }
                    //FOr computer science
                    else if (subId.equals("4")){
                        subjectName = "Computer Science";
                        moptional.add(new AllTestModels(mobject.getString("TestId"),
                                mobject.getString("Description")
                                , mobject.getString("SubjectName"),
                                mobject.getString("SubjectId")));
                    }
                    //bio
                    else if (subId.equals("5")){
                        subjectName = "Biology";
                        moptional.add(new AllTestModels(mobject.getString("TestId"),
                                mobject.getString("Description")
                                , mobject.getString("SubjectName"),
                                mobject.getString("SubjectId")));
                    }
                }
                mCallback.setTestListData(mPhysics,mChem,mMaths,moptional,subjectName);
            } catch (Exception e) {
                mCallback.NoTestListData();
            }
        }



    }

    public interface  TestProviderCallback12{

        void setTestListData(List<AllTestModels> mphysics,List<AllTestModels> mchemistry,List<AllTestModels> maths,List<AllTestModels> optionallist,String optionalName);
        void NoTestListData();


    }
}
