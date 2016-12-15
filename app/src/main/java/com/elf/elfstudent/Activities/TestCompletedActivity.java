package com.elf.elfstudent.Activities;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.elf.elfstudent.Adapters.ViewPagerAdapters.TestCompletedPagerAdapter;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.Network.JsonProcessors.TestDetailReportProvider;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.BundleKey;
import com.elf.elfstudent.Utils.ScreenUtil;
import com.google.firebase.crash.FirebaseCrash;

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


    //The Drop Down Icon
    @BindView(R.id.tool_bar_drop)
    ImageView mDropIcon;
    @BindView(R.id.test_comp_drawer) FrameLayout mdrawerLayout;
    @BindView(R.id.home_menu)
    CardView mHomeButton;
    @BindView(R.id.test_menu) CardView mTestButton;
    @BindView(R.id.report_menu ) CardView mReportButton;
    @BindView(R.id.test_report_menu) CardView mTestReportButton;
    @BindView(R.id.payments_menu) CardView mPaymentsButton;



    TestCompletedPagerAdapter mAdapter  = null;
    private boolean fromtest =false;
    private boolean isDrawerShowing  =false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_completed);
        ButterKnife.bind(this);


        //saved State exists , get the Test iD






        //get The Test Variables from intent
        if (getIntent() != null){
//            testDesc = getIntent().getStringExtra(BundleKey.TEST_ID);
//            subjectId = getIntent().getStringExtra(BundleKey.SUBJECT_ID);
            testId = getIntent().getStringExtra(BundleKey.TEST_ID);
            subjectId  = getIntent().getStringExtra(BundleKey.SUBJECT_ID);
            fromtest =getIntent().getBooleanExtra(BundleKey.FROM_TEST_PAGE,true);
        }
        else{

            //Check whether  activitu was relaunched from save instance state
            if(savedInstanceState != null){
                testId = savedInstanceState.getString(BundleKey.TEST_ID);
                subjectId = savedInstanceState.getString(BundleKey.SUBJECT_ID);
                fromtest = savedInstanceState.getBoolean(BundleKey.FROM_TEST_PAGE);
            }
            else{
                throw new NullPointerException("Test Id is null");
            }
        }



        mDropIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DropButtonClicked();
            }
        });



        if (testId!= null && subjectId != null){

            setAdapterToPager(testId,subjectId);
        }
        else{
            throw  new NullPointerException("Test ID or subject is null cannot be Null, NO Adapter is set");
        }

        setUpCustomDrawer();



    }

    private void setAdapterToPager(String testId, String subjectId) {

        mAdapter = new TestCompletedPagerAdapter(getSupportFragmentManager(),testId,subjectId);
        mPager.setAdapter(mAdapter);
        mTab.setupWithViewPager(mPager);
    }


    private void setUpCustomDrawer() {

        FirebaseCrash.log("Drawer setted up in home activity");
        mHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final  Intent i = new Intent(getApplicationContext(),HomeActivity.class);
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

        //Browse test Page/
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
            mdrawerLayout.setTranslationX(-ScreenUtil.getScreenWidth(this));
            mdrawerLayout.animate()
                    .translationX(0)
                    .setInterpolator(new DecelerateInterpolator(1.5f))
                    .setDuration(600)
                    .setListener(new Animator.AnimatorListener() {
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

            mdrawerLayout.animate()
                    .setDuration(500)
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



        //Invalidate Boolean
        isDrawerShowing = !isDrawerShowing;




    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BundleKey.TEST_ID,testId);
        outState.putString(BundleKey.SUBJECT_ID,subjectId);
        outState.putBoolean(BundleKey.FROM_TEST_PAGE,fromtest);
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
