package com.elf.elfstudent.Network;

import com.android.volley.Response;

import org.json.JSONObject;

/**
 * Created by nandhu on 20/10/16.
 */
public class EmailHandler implements Response.Listener<JSONObject> {

    private EmaiCallbacks mCallback=  null;

    public EmailHandler(EmaiCallbacks mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onResponse(JSONObject response) {

    }

    public interface EmaiCallbacks{
        void ShowPersonalInfoPage();
        void emailAlreadyExists();
    }
}
