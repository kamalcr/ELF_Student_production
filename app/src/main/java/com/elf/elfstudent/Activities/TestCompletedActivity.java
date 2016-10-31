package com.elf.elfstudent.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.elf.elfstudent.Adapters.ViewPagerAdapters.TestCompletedPagerAdapter;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.Network.JsonProcessors.TestDetailReportProvider;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.BundleKey;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 18/10/16.
 * The Page which Show Test Completed
 */

public class TestCompletedActivity extends AppCompatActivity implements ErrorHandler.ErrorHandlerCallbacks {


    private static final String TEST_DETAIL_URL ="http://www.hijazboutique.com/elf_ws.svc/GetDetailedTestReport";
    String testId = null;
    String subjectId = null;
    String testDesc = null;

    String studentId = null;


    ErrorHandler mErrorHandler = null;
    TestDetailReportProvider testDetailReportProvider = null;

    DataStore mStore =  null;
    AppRequestQueue mRequestQueue = null;

    //The Views

    @BindView(R.id.test_completed_pager)
    ViewPager mPager;

    @BindView(R.id.test_completed_tab)
    TabLayout mTab;

    JsonArrayRequest getDeteailedRequest = null;
    ErrorHandler errorHandler = null;

    TestCompletedPagerAdapter mAdapter  = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_completed);
        ButterKnife.bind(this);


        mStore = DataStore.getStorageInstance(getApplicationContext());
        testDetailReportProvider  = new TestDetailReportProvider();
        mRequestQueue = AppRequestQueue.getInstance(this);
        errorHandler = new ErrorHandler(this);


        if (mStore == null){
            studentId = mStore.getStudentId();
        }

        //get The Test Variables from intent
        if (getIntent() != null){
            testDesc = getIntent().getStringExtra(BundleKey.TEST_ID);
            subjectId = getIntent().getStringExtra(BundleKey.SUBJECT_ID);
            testId = getIntent().getStringExtra(BundleKey.TEST_DESC);
        }


//        getDetailedTestReport(testId,studentId);


        setAdapterToPager(testId);


    }

    private void setAdapterToPager(String testId) {

        mAdapter = new TestCompletedPagerAdapter(getSupportFragmentManager(),testId);
        mPager.setAdapter(mAdapter);
        mTab.setupWithViewPager(mPager);
    }

    private void getDetailedTestReport(String testId, String studentId) {
        try{
            JSONObject mObj  = new JSONObject();
            mObj.put("TestId",testId);
            mObj.put("StudentId",studentId);
           getDeteailedRequest = new JsonArrayRequest(Request.Method.POST,TEST_DETAIL_URL,mObj,testDetailReportProvider,errorHandler);

            if (mRequestQueue != null){
                mRequestQueue.addToRequestQue(getDeteailedRequest);
            }
        }
        catch (Exception e){
            Log.d("TG", "getDetailedTestReport: ");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {

        final Intent i  = new Intent(this,BrowseTestActivity.class);
        startActivity(i);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
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
}
