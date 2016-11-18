package com.elf.elfstudent.Network.JsonProcessors;

import android.util.Log;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by nandhu on 20/10/16.
 * used in {@link com.elf.elfstudent.Activities.ForgotPassword}
 */

public class ForgotPasswordHandler implements Response.Listener<JSONArray>{


    private ForgotPasswordCallbacks mCallback = null;

    public ForgotPasswordHandler(ForgotPasswordCallbacks mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onResponse(JSONArray response) {
        Log.d("Forogot", "onResponse: "+response.toString());
        JSONObject mObjec  =  null;
        try {
            mObjec = response.getJSONObject(0);

            if (mObjec.getString("StatusCode").equals("1000")){
                //validated
                mCallback.ShowPassword("Password");
            }
            else{
                mCallback.WrongDetailsEntered();
            }
        }
        catch (Exception e ){
            Log.d("Forgot", "onResponse: exception " +e.getLocalizedMessage());
        }



    }

    public interface ForgotPasswordCallbacks{
        void ShowPassword(String password);
        void WrongDetailsEntered();

    }
}
