package com.elf.elfstudent.Network.JsonProcessors;

import android.util.Log;

import com.android.volley.Response;
import com.elf.elfstudent.model.Topic;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 20/10/16.
 *  used in {@link com.elf.elfstudent.Activities.SubjectViewActivity}
 */

public class TopicProvider implements Response.Listener<JSONArray> {

    private static final String TAG = "ELF";
    private List<Topic> mTopicList;
    private TopicProviderCallbacks mCallback = null;

    public TopicProvider(TopicProviderCallbacks mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onResponse(JSONArray response) {
        Log.d(TAG, "onResponse: got topic response " +response.toString());
        try {


            int count = response.length();
            if (!(count > 0)) {
                mCallback.noTopics();
            } else {


                if (mTopicList == null) {

                    mTopicList = new ArrayList<>(count);
                }
                JSONObject mObj;
                for (int i = 0; i < count; i++) {
                    mObj = response.getJSONObject(i);
                    mTopicList.add(new Topic(mObj.getString("TopicName"), mObj.getString("Percentage")));

                }
                //send Topic back to Activity
                mCallback.setTopics(mTopicList);

            }
        }
        catch (Exception e){
            Log.d(TAG, "onResponse: ");


        }


    }

    public interface TopicProviderCallbacks{
        void setTopics(List<Topic> mTopicList);
        void noTopics();
    }
}
