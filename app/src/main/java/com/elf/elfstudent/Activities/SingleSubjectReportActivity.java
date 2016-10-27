package com.elf.elfstudent.Activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.elf.elfstudent.Adapters.TopicListAdapter;
import com.elf.elfstudent.CustomUI.HelviticaLight;
import com.elf.elfstudent.CustomUI.HelviticaMedium;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.Network.JsonProcessors.TopicProvider;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.BundleKey;
import com.elf.elfstudent.Utils.RequestParameterKey;
import com.elf.elfstudent.model.Topic;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 18/10/16.
 * THe PAge Which SHows Single Lesson In Detail
 */

public class SingleSubjectReportActivity  extends AppCompatActivity implements ErrorHandler.ErrorHandlerCallbacks, TopicProvider.TopicProviderCallbacks {


    private static final String TAG = "TOPIC LISTS";
    private static final String GET_TOPIC_FOR_LESSON = "http://www.hijazboutique.com/elf_ws.svc/GetTopicwiseReport";
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
    HelviticaMedium mLessonName;
    @BindView(R.id.percent_single) HelviticaMedium mPercentName;

    @BindView(R.id.loading_list_root)
    RelativeLayout mChangableRoot;



    String lessonnametrans = null;
    String percenttransName  = null;

    String lessonName = null;
    String percentage = null;
    private int count = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_lesson_activity);
        ButterKnife.bind(this);

        if (getIntent()!= null){
            lessonId  = getIntent().getStringExtra(BundleKey.LESSON_ID);
            subjectId = getIntent().getStringExtra(BundleKey.SUBJECT_ID);

            lessonnametrans = getIntent().getStringExtra(BundleKey.LESSON_NAME_TRANS);
            percenttransName = getIntent().getStringExtra(BundleKey.PERCENT_TRANS);
            lessonName  = getIntent().getStringExtra(BundleKey.LESSON_NAME);
            percentage = getIntent().getStringExtra(BundleKey.PERCENTAGE);


        }


        setSupportActionBar(mToolbar);
        mToolbar.setTitle("Topic Wise Report");
        
        ActionBar ab  = getSupportActionBar();
        try {
            ab.setDisplayShowHomeEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
        }
        catch (Exception e ){
            Log.d(TAG, "Exception in Toolbar");
        }
        mLessonName.setText(lessonName);
        mPercentName.setText(percentage);

        if (lessonnametrans != null && percenttransName != null){
            ViewCompat.setTransitionName(mLessonName,lessonnametrans);
            ViewCompat.setTransitionName(mPercentName,percenttransName);
        }
        mStore = DataStore.getStorageInstance(getApplicationContext());
        mRequestQueue = AppRequestQueue.getInstance(getApplicationContext());
        errorHandler = new ErrorHandler(this);
        topicProvider = new TopicProvider(this);


       
        topicProvider = new TopicProvider(this);
        studentId = mStore.getStudentId();
        if (lessonId != null && studentId != null){

            getTopicForlesson(lessonId,studentId,lessonId);
        }
      
    }

   

    private void getTopicForlesson(String lessonId, String studentId, String id) {
        JSONObject mObject  = new JSONObject();

        try {

            mObject.put(RequestParameterKey.STUDENT_ID,studentId);
            mObject.put(RequestParameterKey.LESSON_ID,lessonId);
            mObject.put(RequestParameterKey.SUBJECT_ID,id);
        }
        catch (Exception e ){
            Log.d(TAG, "getTopicForlesson: ");
        }
        getTopicRequest = new JsonArrayRequest(Request.Method.POST,GET_TOPIC_FOR_LESSON,mObject,topicProvider,errorHandler);
        mRequestQueue.addToRequestQue(getTopicRequest);
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
                 }
            }
        }
    }

    @Override
    public void NetworkError() {

    }

    @Override
    public void ServerError() {
        Log.d(TAG, "ServerError: ");
    }

    @Override
    public void setTopics(List<Topic> mTopicList) {

        mAdapter = new TopicListAdapter(this,mTopicList);
       mChangableRoot.removeAllViews();
        View v  = LayoutInflater.from(this).inflate(R.layout.topic_list,mChangableRoot,true);
        if (v != null){

            mTopicListView = (RecyclerView) v.findViewById(R.id.topic_list);
            if (mTopicListView != null && mAdapter != null){
                mTopicListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                mTopicListView.setAdapter(mAdapter);
            }
        }

    }
}
