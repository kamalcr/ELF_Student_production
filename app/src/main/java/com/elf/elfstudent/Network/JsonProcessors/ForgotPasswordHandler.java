package com.elf.elfstudent.Network.JsonProcessors;

import com.android.volley.Response;

import org.json.JSONObject;

/**
 * Created by nandhu on 20/10/16.
 * used in {@link com.elf.elfstudent.Activities.ForgotPassword}
 */

public class ForgotPasswordHandler implements Response.Listener<JSONObject>{


    private ForgotPasswordCallbacks mCallback = null;

    public ForgotPasswordHandler(ForgotPasswordCallbacks mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onResponse(JSONObject response) {

    }

    public interface ForgotPasswordCallbacks{
        void ShowPassword(String password);
        void WrongDetailsEntered();

    }
}
