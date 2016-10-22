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
    private static final String GET_TOPIC_FOR_LESSON = "";
    AppRequestQueue mRequestQueue = null;
    DataStore mStore = null;

    ErrorHandler errorHandler = null;


    //The Lesson iD, by Lesson get TOpic lists
    String lessonId = null;
    private String studentId= null;
    String subjectId = null;
    private TopicProvider topicProvider = null;

    TopicListAdapter mAdapter = null;

    //The Recycler view which shows Topic List

    @BindView(R.id.topic_list)
    RecyclerView mTopicListView;



    //TOolbar

    @BindView(R.id.sing_report_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.lesson_name_single)
    HelviticaMedium mLessonName;
    @BindView(R.id.percent_single) HelviticaMedium mPercentName;

    String lessonnametrans = null;
    String percenttransName  = null;

    String lessonName = null;
    String percentage = null;
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


        mAdapter = new TopicListAdapter(getApplicationContext(),getTopciList());
       /* topicProvider = new TopicProvider(this);
        studentId = mStore.getStudentId();
        if (lessonId != null && studentId != null){

            getTopicForlesson(lessonId,studentId);
        }
        */
        mTopicListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mTopicListView.setAdapter(mAdapter);
    }

    private List<Topic> getTopciList() {
        List<Topic> mTopicList = new ArrayList<>(12);
        mTopicList.add(new Topic("Addition of Matrix","458"));
        mTopicList.add(new Topic("Subtraction of Matrix","789"));
        mTopicList.add(new Topic("Determinants","789"));

        mTopicList.add(new Topic("Adjoints","789"));
        mTopicList.add(new Topic("Inverse","789"));
        mTopicList.add(new Topic("Cosine Transformations","789"));

        mTopicList.add(new Topic("Wave Theory","789"));
        mTopicList.add(new Topic("Boolean Algebra","789"));
        mTopicList.add(new Topic("Gravity pull of Earth","789"));

        mTopicList.add(new Topic("Carbon - Carbon Bonding","789"));
        mTopicList.add(new Topic("Electrical Conductance","789"));
        mTopicList.add(new Topic("Two Variables","789"));


        return mTopicList;


    }

    private void getTopicForlesson(String lessonId, String studentId) {
        JSONObject mObject  = new JSONObject();

        try {

            mObject.put(RequestParameterKey.STUDENT_ID,studentId);
            mObject.put(RequestParameterKey.LESSON_ID,lessonId);
        }
        catch (Exception e ){
            Log.d(TAG, "getTopicForlesson: ");
        }
        JsonArrayRequest mRequest = new JsonArrayRequest(Request.Method.POST,GET_TOPIC_FOR_LESSON,mObject,topicProvider,errorHandler);
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

    @Override
    public void TimeoutError() {

    }

    @Override
    public void NetworkError() {

    }

    @Override
    public void ServerError() {

    }

    @Override
    public void setTopics(List<Topic> mTopicList) {


        mAdapter = new TopicListAdapter(getApplicationContext(),mTopicList);
        if (mTopicListView != null){
            mTopicListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            mTopicListView.setAdapter(mAdapter);

        }
    }
}
