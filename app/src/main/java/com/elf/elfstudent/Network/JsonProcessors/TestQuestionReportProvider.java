package com.elf.elfstudent.Network.JsonProcessors;

import com.android.volley.Response;
import com.elf.elfstudent.model.Answers;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by nandhu on 31/10/16.
 */
public class TestQuestionReportProvider  implements Response.Listener<JSONArray>{
    @Override
    public void onResponse(JSONArray response) {

    }

    public interface TestCallbacks{
        void TestDetails(String subjectName, String subDesc, List<Answers> mTestReportQuestions);
    }
}
