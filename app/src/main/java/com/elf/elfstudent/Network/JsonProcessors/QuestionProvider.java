package com.elf.elfstudent.Network.JsonProcessors;

import android.util.Log;

import com.android.volley.Response;
import com.elf.elfstudent.model.Question;

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
        JSONObject mObject;
        mQuestionCount = response.length();
        String[] mTitles = new String[mQuestionCount];
        try {
            if (mQuestionList == null) {
                mQuestionList = new ArrayList<>(mQuestionCount);
            }

            for (int i = 0; i < mQuestionCount; i++) {
                mObject = response.getJSONObject(i);

                //for each question in Resposne , get Question and Add it
                //question List
                mQuestionList.add(new Question(mObject.getString("QuestionId")
                        , mObject.getString("Question"),
                        mObject.getString("OptionA"),
                        mObject.getString("OptionB"),
                        mObject.getString("OptionC"),
                        mObject.getString("OptionD"),
                        mObject.getString("Answer"), false
                ));

                //set title(question count ) for tabs  ( +1 for not showing zero)
                mTitles[i] = String.valueOf(i + 1);


            }

            //All Request has been Processed fire up the implementation
            mCallback.setQuestionList(mQuestionList,mTitles);

        } catch (Exception e) {

        }


        //set title(question count ) for tabs  ( +1 for not showing zero)

    }

    public interface QuestionCallback {
        void setQuestionList(List<Question> mQuestionList,String[] mTitles);
        void NoQuestionObtained();
    }
}
