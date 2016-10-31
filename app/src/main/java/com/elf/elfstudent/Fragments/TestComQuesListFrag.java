package com.elf.elfstudent.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.elf.elfstudent.Adapters.TestCompQuestionsAdapter;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.Network.JsonProcessors.TestQuestionReportProvider;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.BundleKey;
import com.elf.elfstudent.model.Answers;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 30/10/16.
 * The Fragment which show list of questions that is right or wrong
 *
 *
 */

public class TestComQuesListFrag extends Fragment implements ErrorHandler.ErrorHandlerCallbacks, TestQuestionReportProvider.TestCallbacks {


    private static final String TAG = "TestComList Frag";
    private static final String TEST_REPORT_URL = "";
    @BindView(R.id.test_comp_rv_list)
    RecyclerView mList;



    String testId = null;
    String studentId = null;
    DataStore mStore = null;

    AppRequestQueue mRequestQueue = null;
    TestQuestionReportProvider reportProvider = null;
    ErrorHandler errorHandler = null;

    JsonArrayRequest mRequest = null;



    TestCompQuestionsAdapter mAdapter = null;
    private List<Answers> mListData;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        errorHandler = new ErrorHandler(this);
        reportProvider  = new TestQuestionReportProvider(this);
        mStore = DataStore.getStorageInstance(getActivity().getApplicationContext());
        mRequestQueue = AppRequestQueue.getInstance(getActivity().getApplicationContext());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.test_comp_ques_list,container,false);
        ButterKnife.bind(this,v);




        if (getArguments()!= null){
                testId = getArguments().getString(BundleKey.TEST_ID);

        }
        if (mStore != null){
            studentId = mStore.getStudentId();
        }

        if (testId!= null && studentId != null){
            getTestReport(testId,studentId);
        }








        return v;
    }

    private void getTestReport(String testId, String studentId) {
        JSONObject mObject = new JSONObject();
        try {
            mObject.put("studnetId",studentId);
            mObject.put("TestId",testId);
        }
        catch (Exception e ){
            Log.d(TAG, "getTestReport: ");
        }

        mRequest = new JsonArrayRequest(Request.Method.POST,TEST_REPORT_URL,mObject,reportProvider,errorHandler);

        if (mRequestQueue!= null){
            mRequestQueue.addToRequestQue(mRequest);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void TimeoutError() {

    }

    @Override
    public void NetworkError() {

    }

    @Override
    public void ServerError() {

    }

    @Override
    public void TestDetails(String subjectName, String subDesc, List<Answers> mTestReportQuestions) {
        this.mListData = mTestReportQuestions;
        mAdapter = new TestCompQuestionsAdapter(mListData,getContext());
        mList.setLayoutManager(new LinearLayoutManager(getContext()));
        mList.setAdapter(mAdapter);
    }
}
