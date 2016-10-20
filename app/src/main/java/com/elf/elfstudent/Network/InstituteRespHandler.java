package com.elf.elfstudent.Network;

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


    private static final String TAGI = "InstTAG";
    List<InstitutionModel> institutionList = null;
    private InstituteHandler mCallback = null;

    public InstituteRespHandler(InstituteHandler mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onResponse(JSONArray response) {
        int count = response.length();
        if (institutionList == null) {
            institutionList = new ArrayList<>(count);
        }
        try {
            JSONObject mObject;
            for (int i = 0; i < count; i++) {
                //single Institution
                mObject = response.getJSONObject(i);
                //Add each single instituion
                institutionList.add(new InstitutionModel(mObject.getString("InstitutionName"),
                        mObject.getString("District"),
                        mObject.getString("StateName"),
                        mObject.getString("CityName")
                ));
            }
            mCallback.setInstitutionList(institutionList);
        }
        catch (Exception e ){
            Log.d(TAGI, "onResponse: Exception");
        }
    }

    public interface InstituteHandler{
        void setInstitutionList(List<InstitutionModel> list);
    }
}
