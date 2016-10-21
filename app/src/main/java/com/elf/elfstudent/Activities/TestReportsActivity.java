package com.elf.elfstudent.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.elf.elfstudent.Adapters.TestReportsAdapter;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.Network.JsonProcessors.TestReportProvider;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.BundleKey;
import com.elf.elfstudent.Utils.RequestParameterKey;
import com.elf.elfstudent.model.TestReportModel;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 18/10/16.
 * The Top Level Activity that shows the Already Wriiten Test with its Reports
 */

public class TestReportsActivity extends AppCompatActivity implements ErrorHandler.ErrorHandlerCallbacks, TestReportProvider.TestListCallback, TestReportsAdapter.TestReportCallbacks {


    private static final String TAG = "TEST_REPORT";
    private static final String GET_TEST_REPORT_URL = "";
    @BindView(R.id.test_report_list)
    RecyclerView mList;

    TestReportsAdapter mAdapter = null;

    private DataStore mStore = null;




    ErrorHandler errorHandler = null;
    TestReportProvider mListDataProvider = null;
    private AppRequestQueue mRequestQueue;


    List<TestReportModel> mTestListdata = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test_report_activity);
        ButterKnife.bind(this);

        mRequestQueue = AppRequestQueue.getInstance(getApplicationContext());
        mStore = DataStore.getStorageInstance(getApplicationContext());
        errorHandler = new ErrorHandler(this);
        mListDataProvider = new TestReportProvider(this);

        String studentId=  mStore.getStudentId();
        if (studentId != null){

            getWriitetenTestFor(studentId);
        }

    }

    private void getWriitetenTestFor(String studentId) {
        JSONObject mObject  = new JSONObject();
        try{
            mObject.put(RequestParameterKey.STUDENT_ID,studentId);

        }
        catch (Exception e ){
            Log.d(TAG, "getWriitetenTestFor: ");
        }
        JsonArrayRequest mRequest = new JsonArrayRequest(Request.Method.POST,GET_TEST_REPORT_URL,mObject,mListDataProvider,errorHandler);
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
        super.onBackPressed();
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

    /*After making Network Request resposne is obtained
    *
    * show it in Recycler view*/

    @Override
    public void showWrittenTest(List<TestReportModel> mWrittenTestList) {
        //store the list to activity variable for later use
        mTestListdata = mWrittenTestList;
        //prepare adapter
        mAdapter = new TestReportsAdapter(getApplicationContext(), mWrittenTestList, this);
        mList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mList.setAdapter(mAdapter);
    }

    /*the callback from Recycler view Adapter { @link TestReportsAdapter }

    show The Test Results in Details
     */
    @Override
    public void ShowTestReportFor(int position, TestReportsAdapter.TestReportHolder holder) {
        //first get the Test Id
        String testId = null;
         if (mTestListdata != null){
             testId = mTestListdata.get(position).getTestId();
             if (testId != null){
                 //Test ID is Present
                 //show Detailed test Report for that Test Id bu that student
                 Intent i = new Intent(this,DetailedTestReportActivity.class);
                 i.putExtra(BundleKey.TEST_ID,testId);
                 startActivity(i);

             }
         }
    }
}
