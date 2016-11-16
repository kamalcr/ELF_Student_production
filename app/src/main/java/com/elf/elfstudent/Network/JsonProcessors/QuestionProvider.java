package com.elf.elfstudent.Network.JsonProcessors;

import android.util.Log;

import com.android.volley.Response;
import com.elf.elfstudent.model.Question;
import com.google.firebase.crash.FirebaseCrash;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 25/10/16.
 *
 */


public class QuestionProvider implements Response.Listener<JSONArray> {


    private List<Question > mQuestionList = null;
    private QuestionCallback mCallback = null;

    private int mQuestionCount;

    public QuestionProvider(QuestionCallback mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onResponse(JSONArray response) {
        Log.d("TestQUestion ", "onResponse: "+response.toString());
        JSONObject mObject = null;

        mQuestionCount = response.length();
        if (!(mQuestionCount>0)){

            Log.d("Adapter", "onResponse: No Questions");
            //zero Questions , show no QUestion Page
            mCallback.NoQuestionObtained();
        }
        else{
            Log.d("Questions", "onResponse: PReparing Questions");
            mQuestionList = new ArrayList<>(mQuestionCount);
            String[] mTitles = new String[mQuestionCount];
            try {

                for (int i = 0; i < mQuestionCount; i++) {
                    mObject = response.getJSONObject(i);

                    //for each question in Resposne , get Question and Add it
                    //question List
                    try{

                        mQuestionList.add(new Question(mObject.getString("QuestionId")
                                , mObject.getString("Question"),
                                mObject.getString("OptionA"),
                                mObject.getString("OptionB"),
                                mObject.getString("OptionC"),
                                mObject.getString("OptionD"),
                                mObject.getString("Answer"), false
                        ));
                    }
                    catch (Exception e ) {
                        Log.d("Adapter", "onResponse: ");

                    }





                    //set title(question count ) for tabs  ( +1 for not showing zero)
                    mTitles[i] = String.valueOf(i + 1);

                    Log.d("Adapter ", "onResponse: "+mQuestionList.toString());

                }

                //All Request has been Processed fire up the implementation
                Log.d("Adapter", "onResponse: sending Lesson list "+mQuestionList.toString());
                mCallback.setQuestionList(mQuestionList,mTitles);

            } catch (Exception e) {
                FirebaseCrash.log("Exception in Puttin Page");
                Log.d("Adapter", "onResponse: exception "+e.getLocalizedMessage());
                mCallback.NoQuestionObtained();
            }
        }
        //set title(question count ) for tabs  ( +1 for not showing zero)
    }

    public interface QuestionCallback {
        void setQuestionList(List<Question> mQuestionList,String[] mTitles);
        void NoQuestionObtained();
    }
}
