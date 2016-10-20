package com.elf.elfstudent.Network;

import android.util.Log;

import com.android.volley.Response;
import com.elf.elfstudent.model.Topic;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 20/10/16.
 */

public class TopicProvider implements Response.Listener<JSONArray> {

    private static final String TAAG = "TOpICPROIVER";
    private List<Topic> mTopicList;
    private TopicProviderCallbacks mCallback = null;

    public TopicProvider(TopicProviderCallbacks mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onResponse(JSONArray response) {


        try {


            int count = response.length();
            if (mTopicList == null){

                mTopicList = new ArrayList<>(count);
            }
            JSONObject mObj ;
            for (int i  = 0; i<count; i++){
                mObj = response.getJSONObject(i);
                mTopicList.add(new Topic(mObj.getString("TopicName"),mObj.getString("Points")));

            }
            //send Topic back to Activity
            mCallback.setTopics(mTopicList);

        }
        catch (Exception e){
            Log.d(TAAG, "onResponse: ");

        }

    }

    public interface TopicProviderCallbacks{
        void setTopics(List<Topic> mTopicList);
    }
}
