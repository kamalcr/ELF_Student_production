package com.elf.elfstudent.Network.JsonProcessors;

import com.android.volley.Response;

import org.json.JSONArray;

/**
 * Created by nandhu on 25/10/16.
 */

public class TestSubitter implements Response.Listener<JSONArray> {
    private SubmittedTestCallback mCallback = null;

    public TestSubitter(SubmittedTestCallback mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onResponse(JSONArray response) {

    }


    public  interface SubmittedTestCallback {
        void testSubmitted();
        void testNotSubmitted();
    }
}
