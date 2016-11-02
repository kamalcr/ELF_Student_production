package com.elf.elfstudent.Network.JsonProcessors;

import android.util.Log;

import com.android.volley.Response;
import com.elf.elfstudent.model.InstitutionModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 20/10/16.
 * used in { @link {@link com.elf.elfstudent.Activities.InstitutePage}}
 */

public class InstituteRespHandler implements Response.Listener<JSONArray> {


    private static final String TAG = "ELF";
    List<InstitutionModel> institutionList = null;
    private InstituteHandler mCallback = null;

    public InstituteRespHandler(InstituteHandler mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onResponse(JSONArray response) {
        Log.d(TAG, "onResponse: from Ins "+response.toString());
        int count = response.length();
        if (institutionList == null) {
            institutionList = new ArrayList<>(count);
        }
        try {
            JSONObject mObject;
            for (int i = 0; i < count; i++) {
                //single Institution
                mObject = response.getJSONObject(i);
                Log.d(TAG, "onResponse: for each institution "+mObject.toString());
                //Add each single instituion
                institutionList.add(new InstitutionModel(mObject.getString("InstitutionName"),
                        mObject.getString("DistrictName"),
                        mObject.getString("CityName"),
                        mObject.getString("InstitutionId")
                ));
            }

        }
        catch (Exception e ){
            Log.d(TAG, "onResponse: Exception InsHandler "+e.getLocalizedMessage());
            Log.d(TAG, "ins Handler "+e.getMessage());
        }
        mCallback.setInstitutionList(institutionList);
    }

    public interface InstituteHandler{
        void setInstitutionList(List<InstitutionModel> list);
    }
}
