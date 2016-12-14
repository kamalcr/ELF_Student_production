package com.elf.elfstudent.Network.JsonProcessors;

import android.util.Log;

import com.android.volley.Response;
import com.elf.elfstudent.model.TestReportModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 20/10/16.
 * This class provides the list of Test Written by the Student and it is
 * used in {@link com.elf.elfstudent.Activities.TestReportsActivity}
 */

public class TestReportProvider implements Response.Listener<JSONArray> {

    private TestListCallback mCallback = null;

    public TestReportProvider(TestListCallback callback) {
        this.mCallback = callback;
    }

    private List<TestReportModel> mWrittenTest  = null;
    @Override
    public void onResponse(JSONArray response) {

        Log.d("TAG", "onResponse: from Test Reports  "+response.toString());
        int count = response.length();

        if (!(count>0)){

            //Empty Response
            mCallback.noWrittenTest();
        }
        else{
            try {
            JSONObject mObjet = null;
                mWrittenTest = new ArrayList<>(count);
            for (int i = 0; i < count;i++){


                    mObjet = response.getJSONObject(i);

                //// TODO: 2/11/16 get Strings
                mWrittenTest.add(new TestReportModel(mObjet.getString("TestId"),
                        mObjet.getString("Description"),
                        mObjet.getString("SubjectId"),
                        mObjet.getString("MarksScored")));


                }


                if (mWrittenTest != null){
                    mCallback.showWrittenTest(mWrittenTest);
                }

            }
            catch (Exception e){
                  mCallback.noWrittenTest();
            }
        }



    }

    public interface TestListCallback{
        void showWrittenTest(List<TestReportModel> mWrittenTestList);
        void noWrittenTest();
    }
}
