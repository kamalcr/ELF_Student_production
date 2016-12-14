package com.elf.elfstudent.Network.JsonProcessors;

import android.util.Log;

import com.android.volley.Response;
import com.elf.elfstudent.model.Lesson;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

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

    private static final String TAG = "Lesson Provider";
    private List<Lesson> mlist = null;
    private SubjectLoaderCallback mCallback = null;

    public LessonProvider(SubjectLoaderCallback mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onResponse(JSONArray response) {

        int count = response.length();
        int percentageSum = 0;

        Log.d("Report Fragment ", "onResponse: "+response.toString());
        mlist=new ArrayList<>(count);
        if (!(count>0)){
            //count is not greater than 1 , show error
            FirebaseCrash.log("No Lesson List in Response ");
            mCallback.noLesson();
        }
        else {

            JSONObject mObject;
            for (int i=0;i<response.length();i++){

//                    getting individual objects by index
                try {
                    mObject=(JSONObject) response.getJSONObject(i);
                    JSONArray testWrittens = mObject.getJSONArray("TestWritten");
                    int arraysize = testWrittens.length();
                    String[] marks  = new String[arraysize];
                    Log.d("Lessson Provider ", "Arrys size: "+arraysize);
                    for (int j = 0;i<arraysize;i++){
                        Log.d(TAG, "onResponse: Inside j loop");
                        JSONObject ind= testWrittens.getJSONObject(i);
                        String percent = ind.getString("Marks");
                        marks[j] = percent;
                        Log.d(TAG, "marks[i] "+marks[j]);
                    }

                    mlist.add(new Lesson(mObject.getString("LessionName"),
                            mObject.getString("LessionId"),
                            mObject.getString("Percentage"),
                            mObject.getString("QuestionsAsked"),
                            mObject.getString("CorrectAnswers"),arraysize,marks));



                    percentageSum = (int) (percentageSum+Float.parseFloat(mObject.getString("Percentage")));

                } catch (Exception e) {
                    Log.d("Adapter", "onResponse: exception " +e.getLocalizedMessage() );
                   FirebaseCrash.log(" "+e.getLocalizedMessage());
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
