package com.elf.elfstudent.Network.JsonProcessors;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.android.volley.Response;
import com.elf.elfstudent.Adapters.SubjectHomeAdapter;
import com.elf.elfstudent.model.SubjectModel;
import com.google.firebase.crash.FirebaseCrash;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 21/10/16.
 * Resposne Handler used in {@link com.elf.elfstudent.Activities.HomeActivity}
 */

public class HomePageDataProvider implements Response.Listener<JSONArray> {

    private static final String TAG = "ELF";
    private HomeDataProvider mCallback = null;

    private String overallRank = null;
    private String districtRank = null;
    private String stateRank = null;


    private List<SubjectModel> mSubjectList = null;

    public HomePageDataProvider(HomeDataProvider mCallback) {
        this.mCallback = mCallback;
    }

    List<SubjectModel> mList;
    @Override
    public void onResponse(JSONArray response) {
        Log.d(TAG, "onResponse: "+response.toString());
        processResponse(response);

    }

    public interface HomeDataProvider{
        void  NewDataReceived(String overallRank,String districtRank,String stateRank ,List<SubjectModel> mSubjectList);
        void NoDataReceivedFromWebservice();
    }

    private void processResponse(JSONArray response) {


        if (mSubjectList == null) {
            mSubjectList = new ArrayList<>();
        }
        try {


            //the full objet ,from object get array  -- rank + subject list
            JSONObject mObject = response.getJSONObject(0);


            Log.d(TAG, "Full Response " + mObject.toString());
            // the rank array  has one array element(object) ,
            // subject array has dynamic array elements (objects)
            JSONArray mRankArray = mObject.getJSONArray("rank");


            Log.d(TAG, "Rank Array: " + mRankArray.toString());


            JSONArray mSubjectArray = mObject.getJSONArray("subjects");

            //get the first rank object which has list of key value pairs
            JSONObject mRankObject = mRankArray.getJSONObject(0);

            overallRank = mRankObject.getString("Rank");
            stateRank = mRankObject.getString("StateRank");
            districtRank = mRankObject.getString("DistrictRank");

            Log.d(TAG, "Rank Objects " + mRankObject.toString());


            // loop through subject list
            for (int i = 0; i < mSubjectArray.length(); i++) {

                // add to subject model
                JSONObject mSubject = mSubjectArray.getJSONObject(i);
                mSubjectList.add(new SubjectModel(mSubject.getString("SubjectName")
                        , mSubject.getString("SubjectId")
                        , mSubject.getString("Percentage")));


            }

            //data has been parsed, send it to adapter
            mCallback.NewDataReceived(overallRank,districtRank,stateRank,mSubjectList);
        }
        catch (Exception e) {
            FirebaseCrash.log("Exception in Home Dashboard Request");
            mCallback.NoDataReceivedFromWebservice();
        }




    }
}

