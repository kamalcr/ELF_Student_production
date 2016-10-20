package com.elf.elfstudent.Activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.elf.elfstudent.Adapters.TopicListAdapter;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.Network.TopicProvider;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.BundleKey;
import com.elf.elfstudent.Utils.RequestParameterKey;
import com.elf.elfstudent.model.Topic;

import org.json.JSONObject;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_lesson_activity);
        ButterKnife.bind(this);

        if (getIntent()!= null){
            lessonId  = getIntent().getStringExtra(BundleKey.LESSON_ID);
            subjectId = getIntent().getStringExtra(BundleKey.SUBJECT_ID);
        }
        mStore = DataStore.getStorageInstance(getApplicationContext());
        mRequestQueue = AppRequestQueue.getInstance(getApplicationContext());
        errorHandler = new ErrorHandler(this);

        topicProvider = new TopicProvider(this);
        studentId = mStore.getStudentId();
        if (lessonId != null && studentId != null){

            getTopicForlesson(lessonId,studentId);
        }
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
