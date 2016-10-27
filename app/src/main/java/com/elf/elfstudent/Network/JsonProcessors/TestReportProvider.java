package com.elf.elfstudent.Network.JsonProcessors;

import com.android.volley.Response;
import com.elf.elfstudent.model.TestReportModel;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by nandhu on 20/10/16.
 * This class provides the list of Test Written by the Student and it is
 * used in {@link com.elf.elfstudent.Activities.TestReportsActivity}
 */

public class TestReportProvider implements Response.Listener<JSONArray> {

    private TestListCallback mCallback = null;

    public TestReportProvider(TestListCallback callback) {
        this.mCallback = callback;
    }

    private List<TestReportModel> mWrittenTest  = null;
    @Override
    public void onResponse(JSONArray response) {



    }

    public interface TestListCallback{
        void showWrittenTest(List<TestReportModel> mWrittenTestList);
    }
}
