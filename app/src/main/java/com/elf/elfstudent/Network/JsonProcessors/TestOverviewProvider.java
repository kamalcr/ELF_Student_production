package com.elf.elfstudent.Network.JsonProcessors;

import com.android.volley.Response;

import org.json.JSONArray;

/**
 * Created by nandhu on 10/11/16.
 */
public class TestOverviewProvider  implements Response.Listener<JSONArray>{

    TestOverviewCallback mCallback = null;

    public TestOverviewProvider(TestOverviewCallback mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onResponse(JSONArray response) {

    }

    public interface TestOverviewCallback{
        public  void ShowOverview(String TestDesc,String SubjectName,String totalQues, String No_ofRight);
        public void noData();
    }
}
