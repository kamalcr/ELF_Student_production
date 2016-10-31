package com.elf.elfstudent.Network.JsonProcessors;

import android.util.Log;

import com.android.volley.Response;
import com.elf.elfstudent.model.Answers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 31/10/16.
 *
 */
public class TestQuestionReportProvider  implements Response.Listener<JSONArray>{
    private static final String TAG = "Testcom";
    private List<Answers> mAnswersList = null;

    private TestCallbacks mCallback = null;
    String subName = null;
    String subDesc = null;

    public TestQuestionReportProvider(TestCallbacks mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onResponse(JSONArray response) {

        JSONObject mObject ;
        int count = response.length();
        if (mAnswersList == null){
            mAnswersList = new ArrayList<>(count);

        }

        try {

            for (int i =0 ;i<count; i++){

                mObject = response.getJSONObject(i);
                if (i==0){
                    subDesc = mObject.getString("SubjectName");
                    subDesc = mObject.getString("SubjectDescription");
                }
                mAnswersList.add(new Answers(mObject.getString("Question")
                        ,mObject.getString("AnswerStatus"),mObject.getString("Correctoption")));
            }
            //End of Loop
            if (mCallback != null){

                mCallback.TestDetails(subName,subDesc,mAnswersList);
            }

        }
        catch (Exception e) {
            Log.d(TAG, "onResponse: ");
        }

    }

    public interface TestCallbacks{
        void TestDetails(String subjectName, String subDesc, List<Answers> mTestReportQuestions);
    }


}
