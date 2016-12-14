package com.elf.elfstudent.Activities;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.elf.elfstudent.Adapters.TopicListAdapter;
import com.elf.elfstudent.CustomUI.HelviticaLight;
import com.elf.elfstudent.CustomUI.HelviticaMedium;
import com.elf.elfstudent.CustomUI.QucikSand;
import com.elf.elfstudent.CustomUI.UbuntuRegular;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.Network.JsonProcessors.TopicProvider;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.BundleKey;
import com.elf.elfstudent.Utils.RVdecorator;
import com.elf.elfstudent.Utils.RequestParameterKey;
import com.elf.elfstudent.model.Topic;
import com.google.firebase.crash.FirebaseCrash;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 18/10/16.
 * THe PAge Which SHows Single Lesson In Detail , The last branch of Report
 *
 * The Topic list is shown here
 *
 *
 */

public class SingleSubjectReportActivity  extends AppCompatActivity implements
        ErrorHandler.ErrorHandlerCallbacks,
        TopicProvider.TopicProviderCallbacks {


    private static final String TAG = "TOPIC LISTS";
    private static final String GET_TOPIC_FOR_LESSON = "http://elfanalysis.net/elf_ws.svc/GetTopicwiseReport";
    AppRequestQueue mRequestQueue = null;
    DataStore mStore = null;

    ErrorHandler errorHandler = null;
    JsonArrayRequest getTopicRequest = null;


    //The Lesson iD, by Lesson get TOpic lists
    String lessonId = null;
    private String studentId= null;
    String subjectId = null;
    private TopicProvider topicProvider = null;

    TopicListAdapter mAdapter = null;

    //The Recycler view which shows Topic List


    RecyclerView mTopicListView = null;



    //TOolbar

    @BindView(R.id.sing_report_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.lesson_name_single)
    UbuntuRegular mLessonName;
    @BindView(R.id.percent_single)
    QucikSand mPercentName;


    @BindView(R.id.topic_list_root)
    FrameLayout mChangableRoot;

    @BindView(R.id.single_subject_content_root) RelativeLayout mlayout;





    String lessonnametrans = null;
    String percenttransName  = null;
    String layouttrns = null;
    String lessonName = null;
    String percentage = null;
    private int count = 0;
    private boolean adapterSet = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_lesson_activity);
        ButterKnife.bind(this);

        if (getIntent()!= null){
            lessonId  = getIntent().getStringExtra(BundleKey.LESSON_ID);
            subjectId = getIntent().getStringExtra(BundleKey.SUBJECT_ID);
            percentage = getIntent().getStringExtra(BundleKey.PERCENTAGE);
            Log.d(TAG, "onCreate: Lesson and Subject ID "+lessonId + " ..."+ subjectId);
//
//
            String lesson_trans_name = getIntent().getStringExtra(BundleKey.LESSON_NAME_TRANS);

            mPercentName.setText(percentage);

            if (lesson_trans_name != null){
                ViewCompat.setTransitionName(mLessonName,lesson_trans_name);
            }

            lessonName  = getIntent().getStringExtra(BundleKey.LESSON_NAME);
            mLessonName.setText(lessonName);
//            percentage = getIntent().getStringExtra(BundleKey.PERCENTAGE);
        }





        mToolbar.setTitle("Topics Overview");
        setSupportActionBar(mToolbar);


        
        ActionBar ab  = getSupportActionBar();
        try {
            ab.setDisplayShowHomeEnabled(true);
            ab.setDisplayShowCustomEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);

        }
        catch (Exception e ){
            Log.d(TAG, "Exception in Toolbar");
        }
        mLessonName.setText(lessonName);
//        mPercentName.setText(percentage);

//        if (lessonnametrans != null && percenttransName != null){
//            ViewCompat.setTransitionName(mLessonName,lessonnametrans);
//            ViewCompat.setTransitionName(mPercentName,percenttransName);
//        }
        mStore = DataStore.getStorageInstance(getApplicationContext());

        mRequestQueue = AppRequestQueue.getInstance(getApplicationContext());

        errorHandler = new ErrorHandler(this);

        topicProvider = new TopicProvider(this);


       
        topicProvider = new TopicProvider(this);
        studentId = mStore.getStudentId();

        //maiking Request with Known Vaiiables
        if (lessonId != null && studentId != null  && subjectId != null){
            getTopicForlesson(lessonId,studentId,subjectId);
        }
        else{
            throw new NullPointerException("input vairables cannot be null");

        }
      
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                }else{
                    finish();
                }
                break;
        }

        return true;
    }

    private void getTopicForlesson(String lessonId, String studentId, String id) {
        JSONObject mObject  = new JSONObject();
        Log.d(TAG, "getTopicForlesson: ");

        try {

            mObject.put("StudentId",studentId);
            mObject.put("SubjectId",subjectId);
            mObject.put(RequestParameterKey.LESSON_ID,lessonId);
        }
        catch (Exception e ){
            FirebaseCrash.log("Error in putting Json Values SingleSubjectReport ");
        }
        getTopicRequest = new JsonArrayRequest(Request.Method.POST,GET_TOPIC_FOR_LESSON,mObject,topicProvider,errorHandler);


        if (mRequestQueue != null){

            mRequestQueue.addToRequestQue(getTopicRequest);
        }
        else{
            mRequestQueue  = AppRequestQueue.getInstance(this);
            mRequestQueue.addToRequestQue(getTopicRequest);
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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void TimeoutError() {



        if ((count>2)){
            //Request has been sent two times / show Time out Error
            mChangableRoot.removeAllViews();
            View v = View.inflate(this,R.layout.try_again_layout,mChangableRoot);


        }
        else{
            //Request timed out , try again
            if (mRequestQueue != null){
                 if (getTopicRequest != null){
                     mRequestQueue.addToRequestQue(getTopicRequest);
                     count++;
                 }
            }
        }
    }

    @Override
    public void NetworkError() {
        //Request has been sent two times / show Time out Error
        try{

            mChangableRoot.removeAllViews();
            View v = View.inflate(this,R.layout.no_internet,mChangableRoot);
        }
        catch (Exception e ){
            FirebaseCrash.log("Exception ");
        }


    }

    @Override
    public void ServerError() {
        //Request has been sent two times / show Time out Error
        try{

            mChangableRoot.removeAllViews();
            View v = View.inflate(this,R.layout.no_data,mChangableRoot);
        }
        catch (Exception e ){
            FirebaseCrash.log("Exception Occured");
        }

    }

    @Override
    public void setTopics(List<Topic> mTopicList) {


        //set Adapter only if adapter is not set
        if (!adapterSet){

            mAdapter = new TopicListAdapter(this,mTopicList);
            mChangableRoot.removeAllViews();
            View v  = LayoutInflater.from(this).inflate(R.layout.topic_list,mChangableRoot,true);
            if (v != null){

                mTopicListView = (RecyclerView) v.findViewById(R.id.topic_list);
                if (mTopicListView != null && mAdapter != null){
                    adapterSet  = true;
                    mTopicListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    mTopicListView.addItemDecoration(new RVdecorator(ContextCompat.getDrawable(getApplicationContext(),R.drawable.divider)));
                    mTopicListView.setAdapter(mAdapter);
                }
                else{
                    FirebaseCrash.log("Adapter not set in Single Subject Adctivity");
                }
            }
        }
        else{
            //adapter set , do nothing
        }

    }

    @Override
    public void noTopics() {

        FirebaseCrash.log("No Topics Received");
        try{

            mChangableRoot.removeAllViews();
            View v = View.inflate(this,R.layout.no_data,mChangableRoot);
        }
        catch (Exception e) {
            FirebaseCrash.log("Exception Occured");
        }
    }
}
