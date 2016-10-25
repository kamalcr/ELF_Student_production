package com.elf.elfstudent.Activities;

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
import android.view.MenuItem;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.elf.elfstudent.Adapters.LessonListAdapter;
import com.elf.elfstudent.CustomUI.HelviticaLight;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.Network.JsonProcessors.LessonProvider;
import com.elf.elfstudent.Network.JsonProcessors.TopicProvider;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.BundleKey;
import com.elf.elfstudent.model.Lesson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 * Created by nandhu on 19/10/16.
 *
 * Called from {@link HomeActivity}
 * displays individual Subject and its lesson and covererage by the student
 *
 */

public class SubjectViewActivity extends AppCompatActivity implements
        ErrorHandler.ErrorHandlerCallbacks,
         LessonProvider.SubjectLoaderCallback {


    private static final String TAG = "SUBJECT_VIEW";
    private static final String GET_LESSON_URL = "";

    //The VIews of this Activity


    //The Subject Name text View
    @BindView(R.id.sub_view_sub_name)
    HelviticaLight mSubjectName ;

    // The percentage Text

    @BindView(R.id.sub_view_percent) HelviticaLight mPercentage;


    //The Recycler view
    @BindView(R.id.sub_view_list)
    RecyclerView mSubList;



    //The Tool Bar
    @BindView(R.id.sub_view_toolbar)
    Toolbar mToolbar;
    private String mSubjectId;


    //The Image
    @BindView(R.id.subject_view_image)
    ImageView mSubjectViewImage;
    //The Request Quue



    private AppRequestQueue mRequestQueue = null;

    LessonListAdapter mAdapter = null;

    ErrorHandler errorHandler ;
    LessonProvider mLessonProvider = null;

    List<Lesson> mLessonList = null;
    JsonArrayRequest mLessonListRequestor = null;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject_view_activity);

        ButterKnife.bind(this);
        if (getIntent() != null){
//            String transName = getIntent().getStringExtra(BundleKey.ROOT_VIEW_TRANS_NAME);
            String subjectName = getIntent().getStringExtra(BundleKey.SUBJECT_NAME);
            String percent = getIntent().getStringExtra(BundleKey.PERCENTAGE);
            String subject_trans_name = getIntent().getStringExtra(BundleKey.HOME_SUBJECT_TRANS_NAME);
            String percent_trans_name  = getIntent().getStringExtra(BundleKey.HOME_PERCENT_TRANS_NAME);

            String img_transName = getIntent().getStringExtra(BundleKey.HOME_SUBJECT_IMAGE_TRANS_NAME);
            Log.d(TAG, "Trans Values are image"+img_transName+ " sub : "+subject_trans_name + " percnet "+percent_trans_name);

            //setting values to Textview
            mSubjectName.setText(subjectName);
            mPercentage.setText(percent);

            mSubjectViewImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_lightbulb));

            //setting Transition Name
            ViewCompat.setTransitionName(mSubjectName,subject_trans_name);
            ViewCompat.setTransitionName(mPercentage,percent_trans_name);
//            ViewCompat.setTransitionName(mRoot,transName);
            ViewCompat.setTransitionName(mSubjectViewImage,img_transName);

            mLessonProvider = new LessonProvider(this);

            //getSubject ID
            mSubjectId = getIntent().getStringExtra(BundleKey.SUBJECT_ID);
        }


        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        //inititlize Request Quw
        mRequestQueue = AppRequestQueue.getInstance(getApplicationContext());
        errorHandler = new ErrorHandler(this);

//        setLessonList(getLessonList());

        getLessonListFromServer();
    }



    private void getLessonListFromServer(){


        //Networks Related Code
        try {
            JSONObject mObject = new JSONObject();
            mObject.put("SubjectId",mSubjectId);
           mLessonListRequestor = new JsonArrayRequest(Request.Method.POST,
                    GET_LESSON_URL, mObject,mLessonProvider,errorHandler);
        }
        catch (Exception e ){
            Log.d(TAG, "getLessonListFromServer: exception "+e.getLocalizedMessage());
        }

        //Add to Request Que
        mRequestQueue.addToRequestQue(mLessonListRequestor);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return true;
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
    public void TimeoutError() {

    }

    @Override
    public void NetworkError() {

    }

    @Override
    public void ServerError() {

    }


    @Override
    public void setLessonList(List<Lesson> mLessons) {
//        mLessonList = mLessons;
        mAdapter = new LessonListAdapter(getApplicationContext(),mLessons);
        mSubList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mSubList.setAdapter(mAdapter);
    }


    @Override
    public void noLesson() {

    }
}
