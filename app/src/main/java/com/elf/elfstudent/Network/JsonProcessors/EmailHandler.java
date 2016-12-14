package com.elf.elfstudent.Network.JsonProcessors;

import android.util.Log;

import com.android.volley.Response;
import com.google.firebase.crash.FirebaseCrash;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by nandhu on 20/10/16.
 */
public class EmailHandler implements Response.Listener<JSONArray> {

    private EmaiCallbacks mCallback=  null;

    public EmailHandler(EmaiCallbacks mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onResponse(JSONArray response) {
        try {
            Log.d("TAG", "onResponse: "+response.toString());
            JSONObject object = response.getJSONObject(0);
            if (object.getString("StatusCode").equals("1000")){
//                 new Email only Can Regsiter wit this email
                if (mCallback != null){
                    mCallback.ShowPersonalInfoPage();
                }
            }else {
                if (mCallback != null){
                    mCallback.emailAlreadyExists();
                }
            }

        }
        catch (Exception e ){
            FirebaseCrash.log("Exception in Parsing");
        }


    }

    public interface EmaiCallbacks{
        void ShowPersonalInfoPage();
        void emailAlreadyExists();
    }
}
