package com.elf.elfstudent.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Utils.RequestParameterKey;
import com.elf.elfstudent.Utils.WebServices;
import com.elf.elfstudent.model.InstitutionModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 17/10/16.
 */
public class SchoolListAdapter extends BaseAdapter {
    private static final String TAG = "Adapter";
    private Context mContext;
    private AppRequestQueue mRequestQueue = null;
    private String baordId  = null;
    private String stateId = null;
    private List<InstitutionModel> resultList = new ArrayList<InstitutionModel>();

    public SchoolListAdapter(Context context) {
        mContext = context;
        Log.d(TAG, "SchoolListAdapter: ");
        mRequestQueue = AppRequestQueue.getInstance(mContext);
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public InstitutionModel getItem(int index) {
        return resultList.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
        }

        return convertView;
    }



    public void setStateId(String stateId) {

        this.stateId = stateId;
        sendRequest(stateId,1);
    }


    /*This Method Calls the Webservice and Upadtest the { @link InstitutionModel }
    *  based on Id Changes
    *     { @param i 0  - boardId has Been Changed
    *              i -1 - state Id has Been Changed
     *              Prepare Request Body Accordint to that
    *
    *   */
    private void sendRequest(String id, int i) {
        JSONObject reqObject = new JSONObject();
        //0 - change BoardId
        // 1 - change state Id
        try {

            if (i == 0) {
                reqObject.put(RequestParameterKey.board_id, id);
                reqObject.put(RequestParameterKey.state_id, this.stateId);
            }
            JSONArray marraJsonArray = new JSONArray(reqObject);
            JsonArrayRequest mRequest = new JsonArrayRequest(Request.Method.POST, WebServices.institutionList, marraJsonArray, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });


        }
        catch (Exception e){
            Log.d(TAG, "sendRequest: exception in Adapter");
        }

    }

    public void setBoardId(String boardId) {
        this.baordId = boardId;
        sendRequest(baordId, 0);
    }
}


