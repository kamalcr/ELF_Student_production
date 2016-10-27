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
    @Override
    public void onResponse(JSONArray response) {
        int total  = response.length();
//        JSONObject mOb

    }

    public interface TestDetailaCallbacks{
        void TestCallback(int no_of_correct,int no_of_wrong);
    }
}
