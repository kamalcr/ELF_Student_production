package com.elf.elfstudent.Network.JsonProcessors;

import android.util.Log;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by nandhu on 20/10/16.
 * final Listener used in {@link com.elf.elfstudent.Activities.InstitutePage}
 */

public class RegisterListener implements Response.Listener<JSONArray> {

    private static final String TAG = "ELF";
    RegistrationCallback mCallback = null;

    public RegisterListener(RegistrationCallback mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onResponse(JSONArray response) {

        //get the Object from Array
        try {

            JSONObject mObj = response.getJSONObject(0);
            String statusCode = mObj.getString("StatusCode");
            String statusKey  = mObj.getString("OutputStatus");
            Log.d(TAG, "Output Status "+statusKey);
            mCallback.Registered("1");
            if (statusCode.equals("1000")){
                //success full Registration
                //// TODO: 21/10/16 mcallbac.registered
            }
            else {
            }
        }
        catch (Exception e ){
            Log.d(TAG, "Cannot get Object");
        }

        Log.d(TAG, "onResponse: from Registration "+response.toString());
        mCallback.Registered("45");
        //String success = response.getJSONObject()
        //String studentId = response.getJSONObject("StudentId");
    }

    public interface RegistrationCallback{
        void Registered(String studentId);
        void NotRegistered();

    }
}
