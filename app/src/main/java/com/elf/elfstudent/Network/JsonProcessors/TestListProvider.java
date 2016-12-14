package com.elf.elfstudent.Network.JsonProcessors;

import android.util.Log;

import com.android.volley.Response;
import com.elf.elfstudent.model.AllTestModels;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 25/10/16.
 *
 */

public class TestListProvider implements
        Response.Listener<JSONArray> {
    private static final String TAG = "AllTestFrag";
   private List<AllTestModels> mTests = null;

    private TestProviderCallback mCallback = null;

    public TestListProvider(TestProviderCallback mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onResponse(JSONArray response) {
        Log.d(TAG, "onResponse: "+response.toString());
        int count = response.length();

        Log.d(TAG, "onResponse: count "+count);

        if (count==0 ){

            //No test Data Received
            mCallback.NoTestListData();
        }
        else{

            String subId = null;


            JSONObject mobject = null;
            try {
                mTests = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    mobject = response.getJSONObject(i);


                    subId = mobject.getString("SubjectId");
                    Log.d(TAG,"Subject iD "+subId);

//

                        mTests.add(new AllTestModels(mobject.getString("TestId"),
                                mobject.getString("Description")
                                , mobject.getString("SubjectName"),
                                mobject.getString("SubjectId")));





                }
                mCallback.setTestListData(mTests);

            } catch (Exception e) {
                Log.d(TAG, "onResponse: exception "+e.getLocalizedMessage());
                mCallback.NoTestListData();
            }
        }
    }

    public interface  TestProviderCallback{

       void setTestListData(List<AllTestModels> mTests);
        void NoTestListData();


    }
}
