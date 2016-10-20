package com.elf.elfstudent.Network;

import android.util.Log;

import com.android.volley.Response;

import org.json.JSONObject;

/**
 * Created by nandhu on 20/10/16.
 * final Listener used in {@link com.elf.elfstudent.Activities.InstitutePage}
 */

public class RegisterListener implements Response.Listener<JSONObject> {

    private static final String TAG = "ELF";
    RegistrationCallback mCallback = null;

    public RegisterListener(RegistrationCallback mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onResponse(JSONObject response) {

        Log.d(TAG, "onResponse: from Registration "+response.toString());
        mCallback.Registered("45");
        //String success = response.getJSONObject()
        //String studentId = response.getJSONObject("StudentId");
    }

    public interface RegistrationCallback{
        void Registered(String studentId);

    }
}
