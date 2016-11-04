package com.elf.elfstudent.Activities;

import android.animation.Animator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.elf.elfstudent.Adapters.ViewPagerAdapters.BiologyReportPagerAdapter;
import com.elf.elfstudent.Adapters.ViewPagerAdapters.ComputerReportPagerAdapter;
import com.elf.elfstudent.Adapters.ViewPagerAdapters.TenthReportPagerAdapter;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.RequestParameterKey;
import com.elf.elfstudent.Utils.ScreenUtil;


import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 18/10/16.
 *
 * The Top Level Report Activity that shows Overall Percentage in a Subject
 *
 *
 */

public class ReportActivity extends AppCompatActivity  implements  ErrorHandler.ErrorHandlerCallbacks
        , Response.Listener<JSONArray> {


    private static final String TAG = "ELF";
    private static final String REPORT_URL ="http://www.hijazboutique.com/elf_ws.svc/GetLessionWiseReport";

    @BindView(R.id.report_tab)
    TabLayout mTab;
    @BindView(R.id.report_pager)
    ViewPager mPager;


    @BindView(R.id.report_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.report_content_root)
    LinearLayout mContentRoot;




    /*THe Drawer Related Atrributes*/
    //The Drop Down Icon
    @BindView(R.id.tool_bar_drop)
    ImageView mDropIcon;
    @BindView(R.id.report_drawer_frame)
    FrameLayout mdrawerLayout;
    @BindView(R.id.home_menu)
    CardView mHomeButton;
    @BindView(R.id.test_menu) CardView mTestButton;
    @BindView(R.id.report_menu ) CardView mReportButton;
    @BindView(R.id.test_report_menu) CardView mTestReportButton;
    @BindView(R.id.payments_menu) CardView mPaymentsButton;



    TenthReportPagerAdapter tenthAdapter = null;
    BiologyReportPagerAdapter bioAdapter = null;
    ComputerReportPagerAdapter compAdapter = null;


    //Request Queu
    private AppRequestQueue mRequestQueue;

    private ErrorHandler errorHandler = null;

    private DataStore mStore = null;


    String classId = null;
    int group;
    private boolean isDrawerShowing =false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_activity);
        ButterKnife.bind(this);

        mRequestQueue = AppRequestQueue.getInstance(getApplicationContext());
        mStore = DataStore.getStorageInstance(getApplicationContext());


        //First Find Which Standard

       setPagerAdapter();
       PrepareSubjectReportsFor(mStore.getStudentId());


        errorHandler = new ErrorHandler(this);

        setSupportActionBar(mToolbar);
        mDropIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DropButtonClicked();
            }
        });
        setUpCustomDrawer();





    }



    private void setUpCustomDrawer() {

        try{

            mHomeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final  Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(i);
                }
            });
        }
        catch (Exception e){
            Log.d(TAG, "setUpCustomDrawer: "+e.getLocalizedMessage());
        }

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
                final Intent i  = new Intent(getApplicationContext(),PaymentActivity.class);
                startActivity(i);
            }
        });
    }

    private void DropButtonClicked() {
//


        if (!isDrawerShowing){
            mdrawerLayout.setTranslationX(-ScreenUtil.getScreenWidth(this));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
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
        }
        else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
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
                        .translationX(-ScreenUtil.getScreenWidth(getApplicationContext())).start();
            }
        }

        Log.d(TAG, "DropButtonClicked: ");

        isDrawerShowing = !isDrawerShowing;




    }
    private void setPagerAdapter() {

        if (mStore != null){
            classId = mStore.getStandard();
            Log.d(TAG, "student class "+classId);
            if (classId != null){


                if (classId.equals("10")){

                    //user is 10
                    Log.d(TAG, "user is 10");
                    tenthAdapter = new TenthReportPagerAdapter(getSupportFragmentManager());
                    mPager.setAdapter(tenthAdapter);
                }
                else{
                    //he is 12 , get the group
                    String group = mStore.getStudentGroup();
                    Log.d(TAG, "student group "+group);
                    if (group.equals("COMPUTER")){
                        //COmputer Group
                        Log.d(TAG, "Computer Group ");
                        compAdapter = new ComputerReportPagerAdapter(getSupportFragmentManager());
                        mPager.setAdapter(compAdapter);
                    }
                    else if (group.equals("BIOLOGY")){
                        Log.d(TAG, "Biology group");
                        bioAdapter = new BiologyReportPagerAdapter(getSupportFragmentManager());
                        mPager.setAdapter(bioAdapter);

                    }
                }
            }
        }

        mTab.setupWithViewPager(mPager);
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
