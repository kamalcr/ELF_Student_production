package com.elf.elfstudent.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.elf.elfstudent.Adapters.ViewPagerAdapters.ReportPagerAdapter;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.RequestParameterKey;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;

/**
 * Created by nandhu on 18/10/16.
 *
 * The Top Level Report Activity that shows Overall Percentage in a Subject
 *
 *
 */

public class ReportActivity extends AppCompatActivity  implements  ErrorHandler.ErrorHandlerCallbacks , Response.Listener<JSONArray> {


    private static final String TAG = "ELF";
    private static final String REPORT_URL = "";

    @BindView(R.id.report_tab)
    TabLayout mTab;
    @BindView(R.id.report_pager)
    ViewPager mPager;




    ReportPagerAdapter mAdapter = null;


    //Request Queu
    private AppRequestQueue mRequestQueue;

    private ErrorHandler errorHandler = null;

    private DataStore mStore = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_activity);

        mRequestQueue = AppRequestQueue.getInstance(getApplicationContext());
        mStore = DataStore.getStorageInstance(getApplicationContext());

        PrepareSubjectReportsFor(mStore.getStudentId());

        errorHandler = new ErrorHandler(this);



    }

    private void PrepareSubjectReportsFor(String studentId) {
        JSONObject mObject = new JSONObject();
        try {
            mObject.put(RequestParameterKey.STUDENT_ID,studentId);
        }
        catch (Exception e){
            Log.d(TAG, "PrepareSubjectReportsFor: ");
        }

        JsonArrayRequest mRequest = new JsonArrayRequest(Request.Method.POST,REPORT_URL,mObject,this,errorHandler);
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


    /*
    *
    * This mehtod presents us a Method
    * it
    * subject Name , Subject iD , overall percentage, Lesson List and growth Percentage
    * may be witin lesson Percentage topic list may be present
    *
    * */
    @Override
    public void onResponse(JSONArray response) {







    }
}
