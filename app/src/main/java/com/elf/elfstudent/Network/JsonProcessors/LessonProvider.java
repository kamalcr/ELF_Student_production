package com.elf.elfstudent.Network.JsonProcessors;

import android.util.Log;

import com.android.volley.Response;
import com.elf.elfstudent.model.Lesson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 22/10/16.
 *
 * used in {@link com.elf.elfstudent.Activities.SubjectViewActivity}
 *
 *
 * This Resposne does not provide full overall percentage is gotten in only {@link HomePageDataProvider}
 *
 * SO get the Percentage divide it by number of Subjects
 */
public class LessonProvider implements Response.Listener<JSONArray> {

    private List<Lesson> mlist = null;
    private SubjectLoaderCallback mCallback = null;

    public LessonProvider(SubjectLoaderCallback mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onResponse(JSONArray response) {

        int count = response.length();
        int percentageSum = 0;

        Log.d("Lesson List", "onResponse: "+response.toString());
        mlist=new ArrayList<>(count);
        if (!(count>1)){

            mCallback.noLesson();
        }
        else {

            JSONObject mObject;
            for (int i=0;i<response.length();i++){

//                    getting individual objects by index
                try {
                    mObject=(JSONObject) response.getJSONObject(i);

                    mlist.add(new Lesson(mObject.getString("LessionName"),
                            mObject.getString("LessionId"),
                            mObject.getString("Percentage"),
                            mObject.getString("QustionAsked"),
                            mObject.getString("CorrectAnswer")));

                    percentageSum = percentageSum+Integer.parseInt(mObject.getString("Percentage"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (mlist!=null){
                int overall = percentageSum/count;
                mCallback.setLessonList(mlist,overall);
            }
            else{
                mCallback.noLesson();
            }
        }

    }

    public interface SubjectLoaderCallback{
        void setLessonList(List<Lesson> mLessons, int overall);
        void noLesson();
    }
}
