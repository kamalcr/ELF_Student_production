package com.elf.elfstudent.Activities;

import android.animation.Animator;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.elf.elfstudent.Adapters.QuestionPagerAdapter;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.Network.JsonProcessors.QuestionProvider;
import com.elf.elfstudent.Network.JsonProcessors.TestSubitter;
import com.elf.elfstudent.Network.TestSubmitEH;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.BundleKey;
import com.elf.elfstudent.model.Question;
import com.elf.elfstudent.model.TestSubmit;
import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 18/10/16.
 * <p>
 * The page Which Pulls The Test Question According to
 * <p>
 * {@param testiD from Intent and Displays Them in View pager}
 */

public class TestWriteActivity extends AppCompatActivity implements ErrorHandler.ErrorHandlerCallbacks, QuestionProvider.QuestionCallback, TestSubitter.SubmittedTestCallback, TestSubmitEH.ErrorCallbacks {
    private static final String TAG = "TestWritePage";
    private static final String TEST_SUBMIT = "http://elfanalysis.net/elf_ws.svc/SubmitTest";
    private static final String GET_QUESTIONS_URL = "http://elfanalysis.net/elf_ws.svc/GetTestQuestions";


    //The Views


    @BindView(R.id.test_write_root)
    FrameLayout mChangableRoot;
    //Top Tab
    @BindView(R.id.nts_center)
    NavigationTabStrip mTab;


    //The Pager, which Displays question one by one
    @BindView(R.id.test_write_viewpager)
    ViewPager mPager;

    //The finish Test Button
    @BindView(R.id.test_write_finish)
    Button testWriteFinish;
    @BindView(R.id.elf_toolbar_test)
    Toolbar toolbarTest;
    @BindView(R.id.timer_text)
    TextView timerText;


    //The Adapter which Shows Question One by one
    private QuestionPagerAdapter mAdapter = null;

    //The Total No of Questions Count for this Test iD obtained from Making Request
    private int mQuestionCount = 0;
    private List<Question> mQuestionList = new ArrayList<>();


    //The Object Which Provdies student Details
    private DataStore mStore;


    //The Request Queue for This Activity

    private AppRequestQueue mRequestQueue = null;

    private ProgressDialog mBar;

    QuestionProvider mQuestionProvider = null;


    //Single Test Id for WHich Question Are Pulled and SHowed
    private String mTestId = null;
    private String mSubjectId = null;
    private String mSubjectName = null;
    private String mTestDesc = null;
    private String mStudentID = null;


    //Error Handler for Volley
    ErrorHandler errorHandler;

    TestSubitter mTestSubmiter = null;
    TestSubmitEH er = null;

    //The Request Objects
    private JsonArrayRequest getQuestionRequest = null;
    private JsonArrayRequest submitTestRequest = null;
    private int count = 0;
    private long timer = 1000 * 60 * 20;  //20minutes

    FirebaseAnalytics mAnalytics = null;
    ProgressDialog mCompleteDialog = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_write_activity);
        ButterKnife.bind(this);


        //initialise Request Que
        mRequestQueue = AppRequestQueue.getInstance(this);
        mStore = DataStore.getStorageInstance(this);


        setSupportActionBar(toolbarTest);


        if (mStore != null) {
            mStudentID = mStore.getStudentId();
        }


        //get Test Details From Intent From Intent
        if (getIntent() != null) {

            mTestId = getIntent().getStringExtra(BundleKey.TEST_ID);
            mSubjectId = getIntent().getStringExtra(BundleKey.SUBJECT_ID);
            Log.d(TAG, "onCreate: TestId "+mTestId);

        } else {
            throw new NullPointerException("TestID cannot be Null");
        }


        //get Question From webServices , until then
        //SHow progress Dialog or Some Animation
        mQuestionProvider = new QuestionProvider(this);
        errorHandler = new ErrorHandler(this);
        er = new TestSubmitEH(this);
        mTestSubmiter = new TestSubitter(this);


        if (mTestId != null && mSubjectId != null) {
            Log.d(TAG, "onCreate: TestID " + mTestId + " Subject iD " + mSubjectId + " Student ID : " + mStudentID);
            prepareTestQuestionsFor(mTestId, mSubjectId);
        } else {

            //NO Test PAge Noticed
            Log.d(TAG, "onCreate: NO Request Sent");
            FirebaseCrash.log("NO Request is being Passed");
            mChangableRoot.removeAllViews();
            View v = View.inflate(this, R.layout.no_data, mChangableRoot);
        }

        //The Button which Completed the Test
        testWriteFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                finishButtonClicked();

            }
        });

    }

    private void finishButtonClicked() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Finish Test ?");
        alertDialog.setMessage("Are you sure  you want to finish the test?");
        alertDialog.setPositiveButton("COMPLETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finishTest();
            }
        });
        alertDialog.setNegativeButton("STAY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //No Has Been Clicked, Dismiss Dialog


            }
        });
        alertDialog.show();
    }

    /*This method Gets the Question Clicked SO for From
    { @link QuestionPagerAdapter and Sends to adapter for validation*/

    private void finishTest() {
        mCompleteDialog = new ProgressDialog(this);
        mCompleteDialog.setIndeterminate(true);
        mCompleteDialog.setMessage("Validating your Answers");
        mCompleteDialog.setCanceledOnTouchOutside(false);
        mCompleteDialog.show();
        if (mAdapter != null) {


            try {

                List<Question> answerList = mAdapter.getQuestionList();
                JSONObject obj = null;
                JSONArray mArray = new JSONArray();

                int count = answerList.size();

                //The Rest Model That is sent to server
                //Adding selected option From Pager Adapter data
                TestSubmit testSubmit = new TestSubmit(mStudentID, mTestId, count);
                for (int i = 0; i < count; i++) {
                    obj = new JSONObject();
                    obj.put("QuestionId", answerList.get(i).getmQuestionId());
                    obj.put("AnswerSelected", answerList.get(i).getSelectedOption());

                    mArray.put(obj);
                    obj = null;

                }


                //Adding Student Id and test Id to

                testSubmit.setStudnetID(mStore.getStudentId());
                testSubmit.setTestId(mTestId);
                SubmitTest(testSubmit, count, mArray);
            } catch (Exception e) {
                Log.d(TAG, "finishTest: exception " + e.getLocalizedMessage());
                FirebaseCrash.log("Finish Test Exception " + e.getLocalizedMessage());
            }
        }
    }

    private void SubmitTest(TestSubmit testSubmit, int count, JSONArray mArray) {

        JSONArray testArray = new JSONArray();
        final JSONObject testObject = new JSONObject();
        testArray.put(testSubmit.getQuestionSet());

        try {


            //preapare test Object
            testObject.put("StudentId", testSubmit.getStudnetID());
            testObject.put("TestId", testSubmit.getTestId());
            testObject.put("Answers", mArray);

            Log.d(TAG, "Submit Test Rquest body " + testObject.toString());
            //send Request
            submitTestRequest = new JsonArrayRequest(Request.Method.POST, TEST_SUBMIT, testObject, mTestSubmiter, er);

            if (mRequestQueue != null) {

                mRequestQueue.addToRequestQue(submitTestRequest);
            } else {
                Log.d(TAG, "SubmitTest: not sumitted");
                testNotSubmitted();
            }

        } catch (Exception e) {
            Log.d(TAG, "SubmitTest: exception in ");
            FirebaseCrash.log("Test Not Submitted in " + e.getLocalizedMessage());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        return true;
    }


    /*This Method hits the webserivice and gets the Question*/

    private void prepareTestQuestionsFor(final String mTestId, final String mSubjectId) {

        //contstruct Request Parameters
        final JSONObject mObject = new JSONObject();

        try {
            mObject.put("TestId", mTestId);
        } catch (Exception e) {
            FirebaseCrash.log("Exception in Putting  Test Request JSOn Object");
        }

        //Request Object
        getQuestionRequest = new JsonArrayRequest(Request.Method.POST, GET_QUESTIONS_URL, mObject, mQuestionProvider, errorHandler);
        if (mRequestQueue != null) {
            mRequestQueue.addToRequestQue(getQuestionRequest);
        } else {
            throw new NullPointerException("Request Queue cannot be Null");
        }
    }

    //set Adapter data and Tab
    private void setAdapter(String[] mTitles, List<Question> mQuestionList) {


        mAdapter = new QuestionPagerAdapter(this, mQuestionList);
        if (mPager != null) {
            mPager.setAdapter(mAdapter);
            mPager.setOffscreenPageLimit(0);

        }

        mTab.setTitles(mTitles);
        mTab.setTabIndex(0);
        mTab.setViewPager(mPager);
        new CountDownTimer(timer, 1000) {

            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                String hms = (TimeUnit.MILLISECONDS.toHours(millis)) + ":" + (TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))) + ":" + (TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));


                timerText.setText(hms);
                timer = millis;

            }

            public void onFinish() {
                timerText.setText("done!");
                finishTest();
            }
        }.start();



        //Hide the Progress Bar


    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return mAdapter.getQuestionList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putParcelable(BundleKey.TEST_PAGE_SELECTION,); // TODO: 27/10/16 save adapter state
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

        finishButtonClicked();
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
        // TODO: 10/11/16 set tag ,retry  , i.e add to Request quueue based on Request TAG

        FirebaseCrash.log("Time put Error ");
        if (mRequestQueue != null) {
            mRequestQueue.addToRequestQue(getQuestionRequest);
        }


    }

    @Override
    public void NetworkError() {


        mChangableRoot.removeAllViews();
        View v = View.inflate(this, R.layout.no_internet, mChangableRoot);

    }

    @Override
    public void ServerError() {
        mChangableRoot.removeAllViews();
        View v = View.inflate(this, R.layout.no_data, mChangableRoot);
    }

    @Override
    public void setQuestionList(List<Question> mQuestionList, String[] mTitles) {

        //set Adapter and page titles


        Log.d(TAG, "setQuestionList: " + mQuestionList.toString());
        setAdapter(mTitles, mQuestionList);

    }

    @Override
    public void NoQuestionObtained() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("No Questions");
        alertDialog.setMessage("It seems some Error Occured , Please try again after some time, or try another tests ");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishButtonClicked();
//                startActivity();
            }
        });

        alertDialog.show();
    }

    @Override
    public void testSubmitted() {
        stopSubmitDialog();

        //Show Tick Activity
        try {


            mChangableRoot.removeAllViews();
            View v = View.inflate(this, R.layout.test_submitted_view, mChangableRoot);
            final TextView mSubmitText = (TextView) v.findViewById(R.id.test_submit_text);
            final ImageView tickImage = (ImageView) v.findViewById(R.id.tick_image);
            tickImage.setScaleX(0);
            tickImage.setScaleY(0);
            tickImage.animate().scaleY(1).scaleY(1)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .setDuration(500)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                            tickImage.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            mSubmitText.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    }).start();


        } catch (Exception e) {
            FirebaseCrash.log("Exception in cnhanging Leayout");
        }

        final Intent i = new Intent(this, TestCompletedActivity.class);
        if (mTestId != null && mSubjectId != null) {
            i.putExtra(BundleKey.SUBJECT_ID, mSubjectId);
            i.putExtra(BundleKey.TEST_ID, mTestId);
            i.putExtra(BundleKey.FROM_TEST_PAGE, true);
            startActivity(i);
        } else {

            throw new NullPointerException("Activity is not started Due to either TestId or Subject is Null");
        }
    }

    private void stopSubmitDialog() {
        if (mCompleteDialog != null) {
            if (mCompleteDialog.isShowing()) {
                //Dialog is visible hide it
                mCompleteDialog.dismiss();
            }
        }
    }

    @Override
    public void testNotSubmitted() {
        stopSubmitDialog();



    }

    @Override
    public void ErrorOccurred() {
        Log.d(TAG, "ErrorOccurred: in test submit ");
        stopSubmitDialog();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Test Not Submitted!");
        alertDialog.setMessage("It seems there is a glitch in submitting test.");
        alertDialog.setPositiveButton("RETRY SUBMIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                retrySubmitRequest();
            }


        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //No Has Been Clicked, Dismiss Dialog
                 final Intent intent = new Intent(getApplicationContext(),BrowseTestActivity.class);
                startActivity(intent);


            }
        });
        alertDialog.show();

    }

    private void retrySubmitRequest() {
        if (submitTestRequest != null && mRequestQueue != null){
            mRequestQueue.addToRequestQue(submitTestRequest);
        }
    }
}
