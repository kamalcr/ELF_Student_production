package com.elf.elfstudent.Network.JsonProcessors;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.elf.elfstudent.Activities.SubjectViewActivity;
import com.elf.elfstudent.model.Lesson;
import com.elf.elfstudent.model.Topic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 12/12/16.
 */
public class AverageProvider implements Response.Listener<JSONArray> {

    List<Topic> averageList = null;
    List<Topic> goodList = null;
    List<Topic> badList = null;
        private avgCallback mCallback = null;

    public AverageProvider(Context context) {
        this.mCallback  = (avgCallback) context;
    }


    @Override
    public void onResponse(JSONArray response) {

        Log.d("reposme", "onResponse: "+response.toString());


        try {
            JSONArray average;
            JSONArray good;
            JSONArray bad;

            JSONObject mObject = response.getJSONObject(0);
            if (mObject == null) {
                    mCallback.noProvider();
            } else {

            if (mObject.getJSONArray("Average") != null){

                average = mObject.getJSONArray("Average");
                averageList = new ArrayList<>(average.length());
                for (int i = 0; i < average.length(); i++) {
                    JSONObject obj = average.getJSONObject(i);
                    averageList.add(new Topic(obj.getString("Topic"), obj.getString("Percentage")));
                }
            }
                else{
                averageList = new ArrayList<>(1);
            }


                if (mObject.getJSONArray("Good") != null  ){

                    good = mObject.getJSONArray("Good");
                    goodList = new ArrayList<>(good.length());
                    for (int i = 0; i < good.length(); i++) {
                        JSONObject obj = good.getJSONObject(i);
                        goodList.add(new Topic(obj.getString("Topic"), obj.getString("Percentage")));
                    }
                }
                else{
                    if (mObject.getJSONArray("Good").equals(null) || mObject.getJSONObject("Good").equals(null)  )
                    goodList =null;
                }


                if (mObject.getJSONArray("Bad") != null){

                    bad = mObject.getJSONArray("Bad");
                    badList = new ArrayList<>(bad.length());

                    for (int i = 0; i < bad.length(); i++) {
                        JSONObject obj = bad.getJSONObject(i);
                        badList.add(new Topic(obj.getString("Topic"), obj.getString("Percentage")));
                    }
                }


                mCallback.avgProvider(averageList, goodList, badList);
            }
        }catch (JSONException e) {
            Log.d("Average", "onResponse: "+e.getLocalizedMessage());
            mCallback.noProvider();
        }
    }


    public interface avgCallback{
        void avgProvider(List<Topic> average, List<Topic> good, List<Topic> bad);
        void noProvider();

    }

}
