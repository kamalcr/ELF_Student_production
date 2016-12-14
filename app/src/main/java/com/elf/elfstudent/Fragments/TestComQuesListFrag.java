package com.elf.elfstudent.Fragments;

import android.content.Context;
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
import android.widget.FrameLayout;
import android.widget.Toast;

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
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;

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

//    Add detailed Test Reports
    private static final String TEST_DETAIL_URL ="http://elfanalysis.net/elf_ws.svc/GetDetailedTestReport";

    RecyclerView mList;



    String testId = null;
    String studentId = null;
    DataStore mStore = null;

    AppRequestQueue mRequestQueue = null;
    TestQuestionReportProvider reportProvider = null;
    ErrorHandler errorHandler = null;

    JsonArrayRequest mRequest = null;



    @BindView(R.id.frame_root_qlist_frag)
    FrameLayout mChangableLayout;


    FirebaseAnalytics mAnalytics = null;

    TestCompQuestionsAdapter mAdapter = null;
    private List<Answers> mListData;
    private Context mContext = null;
    private boolean adapterset = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
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
        Log.d(TAG, "onCreateView: ");
        mAnalytics = FirebaseAnalytics.getInstance(mContext);
        errorHandler = new ErrorHandler(this);
        reportProvider  = new TestQuestionReportProvider(this);
        mStore = DataStore.getStorageInstance(getActivity().getApplicationContext());
        mRequestQueue = AppRequestQueue.getInstance(getActivity().getApplicationContext());



        if (getArguments()!= null){
                testId = getArguments().getString(BundleKey.TEST_ID);

        }
        if (mStore != null){
            studentId = mStore.getStudentId();
        }


        if (studentId != null && testId != null){

            getTestReport(testId,studentId);
        }
        else{
            FirebaseCrash.log("TestCompListFrag, student id or test id is null");
        }










        return v;
    }

    private void getTestReport(String testId, String studentId) {


        Log.d(TAG, "getTestReport: ");
        JSONObject mObject = new JSONObject();
        try {
            mObject.put("StudentId",studentId);
            mObject.put("TestId",testId);
        }
        catch (Exception e ){
            Log.d(TAG, "getTestReport: ");
        }

        mRequest = new JsonArrayRequest(Request.Method.POST,TEST_DETAIL_URL,mObject,reportProvider,errorHandler);

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

        Bundle  b = new Bundle();
        b.putString(BundleKey.TIMEOUT,BundleKey.TIMEOUT);
        mAnalytics.logEvent(BundleKey.TIMEOUT,b);
        Toast.makeText(getActivity().getApplicationContext(),"Time Out Error",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void NetworkError() {

        Toast.makeText(getActivity().getApplicationContext(),"Network Out Error",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ServerError() {
        FirebaseCrash.log("Error in server");
        Toast.makeText(getActivity().getApplicationContext(),"server Out Error",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void TestDetails(String subjectName, String subDesc, List<Answers> mTestReportQuestions) {
        Bundle b  = new Bundle();
        b.putString(FirebaseAnalytics.Event.VIEW_ITEM_LIST,"Test Completed Questions Report -later");
        mAnalytics.logEvent("TestView - Later",b);
        if (!adapterset){
            //adapter is not set
            this.mListData = mTestReportQuestions;
            mChangableLayout.removeAllViews();
            View view = View.inflate(getActivity().getApplicationContext(),R.layout.home_recycler,mChangableLayout);
            mAdapter = new TestCompQuestionsAdapter(mListData,getContext());
            mList = (RecyclerView) view.findViewById(R.id.home_list);
            mList.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
            mList.setAdapter(mAdapter);
            adapterset = true;
        }
        else{
            //adapter is already set , do not do anything
            Log.d(TAG, "TestDetails: else");
        }

    }
}
