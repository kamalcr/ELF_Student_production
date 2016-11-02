package com.elf.elfstudent.Network.JsonProcessors;

import android.util.Log;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by nandhu on 25/10/16.
 *
 */

public class TestSubitter implements Response.Listener<JSONArray> {
    private static final String TAG = "Test Submit";
    private SubmittedTestCallback mCallback = null;

    public TestSubitter(SubmittedTestCallback mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onResponse(JSONArray response) {


        Log.d(TAG, "onResponse: from TestSubmitted "+response.toString());
        try {

            JSONObject object = response.getJSONObject(0);
            if (object.getString("StatusCode").equals("1000")){
                mCallback.testSubmitted();
            }
            else{
                mCallback.testNotSubmitted();
            }
        }
        catch (Exception e ){
            Log.d(TAG, "onResponse: ");
        }
    }


    public  interface SubmittedTestCallback {
        void testSubmitted();
        void testNotSubmitted();
    }
}
