package com.elf.elfstudent.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.elf.elfstudent.Adapters.QuestionPagerAdapter;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.BundleKey;
import com.elf.elfstudent.model.Answers;
import com.elf.elfstudent.model.Question;
import com.elf.elfstudent.model.TestSubmit;
import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

/**
 * Created by nandhu on 18/10/16.
 *
 * The page Which Pulls The Test Question According to
 *
 * {@param testiD from Intent and Displays Them in View pager}
 */

public class TestPageActivity extends AppCompatActivity implements ErrorHandler.ErrorHandlerCallbacks {
    private static final String TAG = "TestWritePage";
    private static final String TEST_SUBMIT = "";
    private static final String GET_QUESTIONS_URL = "http://www.hijazboutique.com/elf_ws.svc/GetTestQuestions";


    //The Views

    //Top Tab
    @BindView(R.id.nts_center)
    NavigationTabStrip mTab;


    //The Pager, which Displays question one by one
    @BindView(R.id.test_write_viewpager)
    ViewPager mPager;

    //The finish Test Button
    @BindView(R.id.test_write_finish)
    Button testWriteFinish;



    //The Adapter which Shows Question One by one
    private QuestionPagerAdapter mAdapter = null;

    //The Total No of Questions Count for this Test iD obtained from Making Request
    private int mQuestionCount = 0;
    private List<Question> mQuestionList = new ArrayList<>();



    //The Object Which Provdies student Details
    private DataStore mStore;


    //The Request Queue for This Activity

    private AppRequestQueue mRequestQueue = null;

    AVLoadingIndicatorView mBar;


    //Single Test Id for WHich Question Are Pulled and SHowed
    private String mTestId = null;
    private String mSubjectId = null;
    private String mSubjectName = null;
    private String mTestDesc = null;


    //Error Handler for Volley
    ErrorHandler errorHandler ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_write_activity);
        ButterKnife.bind(this);

        //initialise Request Que
        mRequestQueue = AppRequestQueue.getInstance(this);

        //get Test Details From Intent From Intent
        if (getIntent() != null){

            mTestId = getIntent().getStringExtra(BundleKey.TEST_ID);
            mSubjectId = getIntent().getStringExtra(BundleKey.SUBJECT_ID);
            mSubjectName  =getIntent().getStringExtra(BundleKey.SUBJECT_NAME);
            mTestDesc = getIntent().getStringExtra(BundleKey.TEST_DESC);
        }


        //get Question From webServices , until then
        //SHow progress Dialog or Some Animation

        errorHandler = new ErrorHandler(this);

        prepareTestQuestionsFor(mTestId,mSubjectId);

        testWriteFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());
                alertDialog.setTitle("Finish Test");
                alertDialog.setMessage("Are you sure  you want to finish the test");
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishTest();
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //No Has Been Clicked, Dismiss Dialog


                    }
                });
                alertDialog.show();

            }
        });

    }

    /*This method Gets the Question Clicked SO for From
    { @link QuestionPagerAdapter and Sends to adapter for validation*/

    private void finishTest() {
        if(mAdapter != null){


            List<Question> answerList =  mAdapter.getQuestionList();
            int count = answerList.size();

            //The Rest Model That is sent to server
             //Adding selected option From Pager Adapter data
            TestSubmit testSubmit = new TestSubmit(mStore.getStudentId(),mTestId,count);
            for (int i= 0; i<count ; i++){
                //setting The Question Id and option to Test Submit Object
                testSubmit.setAnswerForQuestion(answerList.get(i).getmQuestionId()
                        ,answerList.get(i).getSelectedOption());

            }


            //Adding Student Id and test Id to
            testSubmit.setStudnetID(mStore.getStudentId());
            testSubmit.setTestId(mTestId);
            SubmitTest(testSubmit,count);

        }
    }

    private void SubmitTest(TestSubmit testSubmit, int count) {

        JSONArray testArray = new JSONArray();
        final JSONObject testObject = new JSONObject();
        testArray.put(testSubmit.getQuestionSet());

        try {



            //preapare test Object
            testObject.put("StudentID",testSubmit.getStudnetID());
            testObject.put("testId",testSubmit.getTestId());
            testObject.put("AnswersList",testArray);

            //send Request
            JsonObjectRequest mRequest  = new JsonObjectRequest(Request.Method.POST, TEST_SUBMIT, testObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //Response is obtained
                    //todo :, change Fragment Now , using Activity

                    Log.d(TAG, "onResponse: "+response.toString());

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "onErrorResponse: ");
                }
            });


            mRequestQueue.addToRequestQue(mRequest);
        }


        catch (Exception e){
            Log.d(TAG, "SubmitTest: No Json Object Created");
        }

    }




    /*This Method hits the webserivice and gets the Question*/

    private void prepareTestQuestionsFor(final String mTestId, final String mSubjectId) {

        //contstruct Request Parameters
        final JSONObject mObject = new JSONObject();
        // todo: Dynamic Student id
        //       {"StudentId":1,"TestId":1,"SubjectId":2}
        try {
            mObject.put("StudentId", mStore.getStudentId());
            mObject.put("TestId", mTestId);
            mObject.put("SubjectId", mSubjectId);
        } catch (Exception e) {
            Log.d(TAG, "Exception in putting JSON Objects: ");
        }

        //Request Object
        final JsonArrayRequest mRequest = new JsonArrayRequest(Request.Method.POST, URL, mObject, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d(TAG, "onResponse: Obtained for " + mTestId);
                processQuestionList(response);


            }
        }, errorHandler);


        mRequestQueue.addToRequestQue(mRequest);


    }



    /*
    * The Method parses the response and Builds Appropriate
     * List of Model object  {@link  Question }
     * and Provides that to recycler view
     *
    *
    * */


    private void processQuestionList(JSONArray response) {
        JSONObject mObject;
        Log.d(TAG, "onResponse: Test Result");
        mQuestionCount = response.length();
        String[] mTitles = new String[mQuestionCount];
        try {
            if (mQuestionList == null) {
                mQuestionList = new ArrayList<>(mQuestionCount);
            }

            for (int i = 0; i < mQuestionCount; i++) {
                mObject = response.getJSONObject(i);
                Log.d(TAG, "onResponse: " + mObject);

                //for each question in Resposne , get Question and Add it
                //question List
                mQuestionList.add(new Question(mObject.getString("QuestionId")
                        , mObject.getString("Question"),
                        mObject.getString("OptionA"),
                        mObject.getString("OptionB"),
                        mObject.getString("OptionC"),
                        mObject.getString("OptionD"),
                        mObject.getString("Answer"), false
                ));

                //set title(question count ) for tabs  ( +1 for not showing zero)
                mTitles[i] = String.valueOf(i + 1);


            }
            setAdapter(mTitles, mQuestionList);

        } catch (Exception e) {
            Log.d(TAG, "Exception in parsing " + e.getLocalizedMessage());
        }


    }


    //set Adapter data and Tab
    private void setAdapter(String[] mTitles, List<Question> mQuestionList) {
        if (mAdapter == null) {
            Log.d(TAG, "onResponse: Adapter  = new Adapter");
            mAdapter = new QuestionPagerAdapter(this, mQuestionList);
            if (mPager != null) {
                mPager.setAdapter(mAdapter);
                mPager.setOffscreenPageLimit(0);


            }
        } else {
            Log.d(TAG, "onResponse: notify Data set Changed");
            mAdapter.notifyDataSetChanged();


        }
        mTab.setTitles(mTitles);
        mTab.setTabIndex(0);
        mTab.setViewPager(mPager);
        //Hide the Progress Bar

        if (mBar.isShown()){
            mBar.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putParcelable(BundleKey.TEST_PAGE_SELECTION,);
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
}
