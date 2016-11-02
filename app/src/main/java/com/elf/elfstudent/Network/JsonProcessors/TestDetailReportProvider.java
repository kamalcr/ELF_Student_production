package com.elf.elfstudent.Network.JsonProcessors;

import com.android.volley.Response;
import com.elf.elfstudent.model.Answers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by nandhu on 20/10/16.
 * used in {@link com.elf.elfstudent.Activities.DetailedTestReportActivity}
 */

public class TestDetailReportProvider  implements Response.Listener<JSONArray>{

    int no_of_right = 0;
    int no_of_wrong = 0;
    private List<Answers> mAnserList = null;

    private TestDetailaCallbacks mCallbacks = null;

    public TestDetailReportProvider(TestDetailaCallbacks mCallbacks) {
        this.mCallbacks = mCallbacks;
    }

    @Override
    public void onResponse(JSONArray response) {
        int total  = response.length();


        //if total test Written is not greater than zero
        if(!(total>0)){
               mCallbacks.noDetail();
        }
//        JSONObject mOb

    }

    public interface TestDetailaCallbacks{
        void TestCallback(int no_of_correct,int no_of_wrong);

        void noDetail();
    }
}
