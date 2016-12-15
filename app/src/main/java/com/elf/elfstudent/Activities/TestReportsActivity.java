package com.elf.elfstudent.Activities;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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
import com.elf.elfstudent.Utils.ScreenUtil;
import com.elf.elfstudent.model.TestReportModel;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 18/10/16.
 * The Top Level Activity that shows the Already Wriiten Test with its Reports
 */

public class TestReportsActivity extends AppCompatActivity
        implements ErrorHandler.ErrorHandlerCallbacks,
        TestReportProvider.TestListCallback,
        TestReportsAdapter.TestReportCallbacks {


    private static final String TAG = "TEST_REPORT";

    private static final String GET_TEST_REPORT_URL = "http://elfanalysis.net/elf_ws.svc/GetWritenTestDetails";

    RecyclerView mList = null;

    TestReportsAdapter mAdapter = null;

    private DataStore mStore = null;



    //The COntenet Root
    @BindView(R.id.test_report_content_root) RelativeLayout mContentRoot;

    //THe Changeale Root

    @BindView(R.id.frame_root_treports) FrameLayout mChangableRoot;

    /*THe Drawer Related Atrributes*/
    //The Drop Down Icon
    @BindView(R.id.tool_bar_drop)
    ImageView mDropIcon;
    @BindView(R.id.test_report_drawer_frame)
    FrameLayout mdrawerLayout;
    @BindView(R.id.home_menu)
    CardView mHomeButton;
    @BindView(R.id.test_menu) CardView mTestButton;
    @BindView(R.id.report_menu ) CardView mReportButton;
    @BindView(R.id.test_report_menu) CardView mTestReportButton;
    @BindView(R.id.payments_menu) CardView mPaymentsButton;

    ErrorHandler errorHandler = null;
    TestReportProvider mListDataProvider = null;
    private AppRequestQueue mRequestQueue;

    JsonArrayRequest mRequest = null;


    List<TestReportModel> mTestListdata = null;
    private boolean isDrawerShowing =false;
    private int count = 0;

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
        else{
            throw new NullPointerException("Student id null");
        }

        setUpCustomDrawer();

        mDropIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DropButtonClicked();
            }
        });

    }



    public void closeDrawer(){
        if (isDrawerShowing){
            mdrawerLayout.animate().setDuration(500)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {

                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    })
                    .translationX(-ScreenUtil.getScreenWidth(getApplicationContext()))
                    .start();
        }

        Log.d(TAG, "DropButtonClicked: ");

        isDrawerShowing = !isDrawerShowing;
    }
    private void setUpCustomDrawer() {

        mHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(i);
            }
        });

        //Report

        mReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final  Intent i = new Intent(getApplicationContext(),ReportActivity.class);
                startActivity(i);
            }
        });

        //Browse test Page
        mTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final  Intent i = new Intent(getApplicationContext(),BrowseTestActivity.class);
                startActivity(i);
            }
        });

        //Test Reports

        mTestReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Intent i = new Intent(getApplicationContext(),TestReportsActivity.class);
                startActivity(i);
            }
        });

        //Payments
        mPaymentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Intent i  = new Intent(getApplicationContext(),CouponActivity.class);
                startActivity(i);
            }
        });
    }

    private void DropButtonClicked() {
//




        if (!isDrawerShowing){
            //Drawer is not showing  , open with open animations
            mdrawerLayout.setTranslationX(-ScreenUtil.getScreenWidth(this));

            mdrawerLayout.animate().translationX(0).setInterpolator(new DecelerateInterpolator(1.5f)).setDuration(600).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    mdrawerLayout.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animator) {

                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            }).start();
        }
        else{

            //drawer is showing close drawer , update the boolean
                mdrawerLayout.animate().setDuration(500)
                        .setInterpolator(new AccelerateDecelerateInterpolator())
                        .setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {

                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {

                            }
                        }).translationX(-ScreenUtil.getScreenWidth(getApplicationContext())).start();
            }


        Log.d(TAG, "DropButtonClicked: ");

        isDrawerShowing = !isDrawerShowing;




    }

    private void getWriitetenTestFor(String studentId) {
        JSONObject mObject  = new JSONObject();
        try{
            mObject.put("StudentId",studentId);

        }
        catch (Exception e ){
            Log.d(TAG, "getWriitetenTestFor: ");
        }
        mRequest  = new JsonArrayRequest(Request.Method.POST,GET_TEST_REPORT_URL,mObject,mListDataProvider,errorHandler);
        if (mRequestQueue != null){
            Log.d(TAG, "getWriitetenTestFor: adding to request queue");
            mRequestQueue.addToRequestQue(mRequest);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        errorHandler = null;
        mStore  = null;
        mRequestQueue = null;
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


        if (!(count>2)){
            //Retry Request
            mRequestQueue.addToRequestQue(mRequest);
            count++;
        }
        else{

            mChangableRoot.removeAllViews();
            View view = View.inflate(this,R.layout.try_again_layout,mChangableRoot);
        }


    }

    @Override
    public void NetworkError() {


        mChangableRoot.removeAllViews();
        View view = View.inflate(this,R.layout.no_internet,mChangableRoot);
    }

    @Override
    public void ServerError() {

        mChangableRoot.removeAllViews();
        View view = View.inflate(this,R.layout.no_data,mChangableRoot);

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

        mChangableRoot.removeAllViews();
        View view = View.inflate(this,R.layout.home_recycler,mChangableRoot);

        mList = (RecyclerView) view.findViewById(R.id.home_list);

        mList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mList.setAdapter(mAdapter);
    }

    @Override
    public void noWrittenTest() {


        mChangableRoot.removeAllViews();
        View view  = View.inflate(this,R.layout.no_test_written,mChangableRoot);
    }

    /*the callback from Recycler view Adapter { @link TestReportsAdapter }

    show The Test Results in Details
     */
    @Override
    public void ShowTestReportFor(int position, TestReportsAdapter.TestReportHolder holder,String subId) {

      if (isDrawerShowing){
          closeDrawer();
      }

        //first get the Test Id
        String testId = null;
         if (mTestListdata != null){
             testId = mTestListdata.get(position).getTestId();
             if (testId != null){
                 //Test ID is Present
                 //show Detailed test Report for that Test Id bu that student
                 Intent i = new Intent(this,TestCompletedActivity.class);
                 i.putExtra(BundleKey.TEST_ID,testId);
                 i.putExtra(BundleKey.SUBJECT_ID,subId);
                 i.putExtra(BundleKey.FROM_TEST_PAGE,false);

                 startActivity(i);

             }
             else {
                 throw new NullPointerException("TestID cannot be null");
             }
         }
    }

    @Override
    public void reWriteTest(String testid, String subjectId) {
        final Intent i = new Intent(this,TestWriteActivity.class);
        i.putExtra(BundleKey.TEST_ID,testid);
        i.putExtra(BundleKey.SUBJECT_ID,subjectId);
        startActivity(i);
    }

}
