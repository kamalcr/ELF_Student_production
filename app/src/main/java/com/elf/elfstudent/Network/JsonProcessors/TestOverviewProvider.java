package com.elf.elfstudent.Network.JsonProcessors;

import android.util.Log;

import com.android.volley.Response;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by nandhu on 10/11/16.
 *
 */
public class TestOverviewProvider  implements Response.Listener<JSONArray>{

    TestOverviewCallback mCallback = null;

    public TestOverviewProvider(TestOverviewCallback mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onResponse(JSONArray response) {

        Log.d("OVervie Response", "onResponse: "+response.toString());
        JSONObject object = null;
        try {

           object = response.getJSONObject(0);
        }
        catch (Exception e ){
            Log.d("Response", "onResponse: ");
            mCallback.noData();

        }

        if (object != null){
            try {

                String testDesc  = object.getString("Description");
                Log.d("TestDesc", "onResponse: "+testDesc);
                String correc  = object.getString("CorrectAnswers");
                String total  = object.getString("TotalQuestionsAsked");
                String subName =  object.getString("SubjectName");
                mCallback.ShowOverview(testDesc,subName,total,correc);
            }
            catch (Exception e ){
                FirebaseCrash.log("Exception in parsing Test Overview");
                mCallback.noData();
            }
        }
        else{
            mCallback.noData();
        }



    }

    public interface TestOverviewCallback{
        public  void ShowOverview(String TestDesc,String SubjectName,String totalQues, String No_ofRight);
        public void noData();
    }
}
