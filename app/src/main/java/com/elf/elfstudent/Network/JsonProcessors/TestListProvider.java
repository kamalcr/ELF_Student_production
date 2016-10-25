package com.elf.elfstudent.Network.JsonProcessors;

import com.android.volley.Response;
import com.elf.elfstudent.model.AllTestModels;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by nandhu on 25/10/16.
 */

public class TestListProvider implements Response.Listener<JSONArray> {
    private List<AllTestModels> mSocial = null;
    private List<AllTestModels> mScience = null;
    private List<AllTestModels> mMaths = null;

    private TestProviderCallback mCallback = null;

    public TestListProvider(TestProviderCallback mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onResponse(JSONArray response) {

    }

    public interface  TestProviderCallback{

       void setTestListData(List<AllTestModels> mScience,List<AllTestModels> mSocial,List<AllTestModels> maths);


    }
}
