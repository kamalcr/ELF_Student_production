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



    String testId = null;
    String subjectId = null;
    String testDesc = null;
    String studentId = null;





    //The Views

    @BindView(R.id.test_completed_pager)
    ViewPager mPager;

    @BindView(R.id.test_completed_tab)
    TabLayout mTab;



    TestCompletedPagerAdapter mAdapter  = null;
    private boolean fromtest =false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_completed);
        ButterKnife.bind(this);










        //get The Test Variables from intent
        if (getIntent() != null){
//            testDesc = getIntent().getStringExtra(BundleKey.TEST_ID);
//            subjectId = getIntent().getStringExtra(BundleKey.SUBJECT_ID);
            testId = getIntent().getStringExtra(BundleKey.TEST_ID);
            subjectId  = getIntent().getStringExtra(BundleKey.SUBJECT_ID);
            fromtest =getIntent().getBooleanExtra(BundleKey.FROM_TEST_PAGE,true);
        }
        else{
            throw new NullPointerException("Intent  Cannot Be null");
        }





        if (testId!= null && subjectId != null){

            setAdapterToPager(testId,subjectId);
        }
        else{
            throw  new NullPointerException("Test ID cannot be Null");
        }



    }

    private void setAdapterToPager(String testId, String subjectId) {

        mAdapter = new TestCompletedPagerAdapter(getSupportFragmentManager(),testId,subjectId);
        mPager.setAdapter(mAdapter);
        mTab.setupWithViewPager(mPager);
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


        if (fromtest){
            //From Test Page, this iacitivty is shown from Test ActivityPage , show Browse Test Activity
            final Intent i  = new Intent(this,BrowseTestActivity.class);
            startActivity(i);
        }
        else{
            final   Intent i  = new Intent(this,TestReportsActivity.class);
            startActivity(i);
        }

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
