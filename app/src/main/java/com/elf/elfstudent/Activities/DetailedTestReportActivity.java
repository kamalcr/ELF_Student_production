package com.elf.elfstudent.Activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.elf.elfstudent.Adapters.ViewPagerAdapters.TestDetailPagerAdapter;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.Network.JsonProcessors.TestDetailReportProvider;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.BundleKey;
import com.elf.elfstudent.Utils.RequestParameterKey;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 20/10/16.
 * The Activity which is  invoked from {@link TestReportsActivity}
 *
 * shows the Detailed Report For a Test iD
 */
public class DetailedTestReportActivity  extends AppCompatActivity{

    private static final String TAG = "ELF";
    private static final String GET_TEST_DETAIL_REPORT = "";
    String testId = null;
    String studnetId  = null;




    @BindView(R.id.test_detail_tab)
    TabLayout mTab;

    @BindView(R.id.test_detail_pager)
    ViewPager mPager;

    @BindView(R.id.test_detail_toolbar)
    Toolbar mToolbar;


    ErrorHandler errorHandler  = null;

//    // TODO: 20/10/16 implement interface here, dont know what kind of data is gonna come here
    TestDetailReportProvider testDetailReportProvider = null;


    TestDetailPagerAdapter mAdapter = null;

    DataStore mStore  = null;

    private AppRequestQueue mRequestQueue = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_test_activity);
        ButterKnife.bind(this);


        mRequestQueue  = AppRequestQueue.getInstance(getApplicationContext());

        mStore = DataStore.getStorageInstance(getApplicationContext());
        if (getIntent() != null){
            testId = getIntent().getStringExtra(BundleKey.TEST_ID);
            studnetId = mStore.getStudentId();
        }



        getDetailedTestReportFor(testId,studnetId);

        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);



    }

    private void getDetailedTestReportFor(String testId, String studnetId) {
        JSONObject mObject = new JSONObject();
        try{
            mObject.put(RequestParameterKey.STUDENT_ID,studnetId);
            mObject.put(RequestParameterKey.TEST_ID,testId);
        }
        catch (Exception e ){
            Log.d(TAG, "Exception");
        }
        JsonArrayRequest mRequest = new JsonArrayRequest(Request.Method.POST,GET_TEST_DETAIL_REPORT,mObject,testDetailReportProvider,errorHandler);
        mRequestQueue.addToRequestQue(mRequest);

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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
