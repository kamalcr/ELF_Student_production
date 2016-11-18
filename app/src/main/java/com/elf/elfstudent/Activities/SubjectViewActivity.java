package com.elf.elfstudent.Activities;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.elf.elfstudent.Adapters.LessonListAdapter;
import com.elf.elfstudent.CustomUI.HelviticaLight;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.Network.JsonProcessors.LessonProvider;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.BundleKey;
import com.elf.elfstudent.Utils.RVdecorator;
import com.elf.elfstudent.Utils.SubjectIMAGE;
import com.elf.elfstudent.Utils.SubjectImage;
import com.elf.elfstudent.model.Lesson;
import com.google.firebase.crash.FirebaseCrash;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.sql.Time;
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
    private static final String GET_LESSON_URL = "http://www.hijazboutique.com/elf_ws.svc/GetLessionWiseReport";

    //The VIews of this Activity


    //The Subject Name text View
//    @BindView(R.id.sub_view_sub_name)
//    HelviticaLight mSubjectName ;

    // The percentage Text

    @BindView(R.id.sub_view_percent) HelviticaLight mPercentage;


    //The Recycler view

    RecyclerView mSubListView;



    //The Tool Bar
    @BindView(R.id.sub_view_toolbar)
    Toolbar mToolbar;
    private String mSubjectId;


    //The Image
    @BindView(R.id.subject_view_image)
    ImageView mSubjectViewImage;


    @BindView(R.id.report_changable_root)
    FrameLayout mChangableRoot;


    private AppRequestQueue mRequestQueue = null;

    LessonListAdapter mAdapter = null;

    ErrorHandler errorHandler ;
    LessonProvider mLessonProvider = null;


    JsonArrayRequest mLessonListRequestor = null;
    private String studentId = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject_view_activity);

        ButterKnife.bind(this);

        //First get the Intent
        if (getIntent() != null){


                //Get the Percentage & Subject iD
                String percent = getIntent().getStringExtra(BundleKey.PERCENTAGE);
                String subjectID = getIntent().getStringExtra(BundleKey.SUBJECT_ID);

            //set The Background image
            Picasso.with(this).load(SubjectIMAGE.getBIgSubjectImage(subjectID)).into(mSubjectViewImage);


            //Get The Student Id
            studentId = getIntent().getStringExtra(BundleKey.ARG_STUDENT_ID);


            //Get The Transitions Name
            String percent_trans_name  = getIntent().getStringExtra(BundleKey.HOME_PERCENT_TRANS_NAME);
            String img_transName = getIntent().getStringExtra(BundleKey.HOME_SUBJECT_IMAGE_TRANS_NAME);

            //set the Transition Names
            ViewCompat.setTransitionName(mPercentage,percent_trans_name);
            ViewCompat.setTransitionName(mSubjectViewImage,img_transName);

            //Set the View values
            if (percent != null){

                mPercentage.setText(percent);
            }



            mLessonProvider = new LessonProvider(this);

            mSubjectId = getIntent().getStringExtra(BundleKey.SUBJECT_ID);
        }
        else{
            throw new NullPointerException("Intent Cannot be null");
        }


        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        //inititlize Request Quw
        mRequestQueue = AppRequestQueue.getInstance(getApplicationContext());
        errorHandler = new ErrorHandler(this);

//        setLessonList(getLessonList());

        if(mSubjectId != null && studentId != null){
            getLessonListFromServer();
        }
        else{
            throw  new NullPointerException("Subject ID cannot be null");
        }
    }



    private void getLessonListFromServer(){


        //Networks Related Code
        try {
            JSONObject mObject = new JSONObject();
            mObject.put("studentId", studentId);
            mObject.put("subjectId", mSubjectId);
           mLessonListRequestor = new JsonArrayRequest(Request.Method.POST,
                    GET_LESSON_URL, mObject,mLessonProvider,errorHandler);
        }
        catch (Exception e ){
            Log.d(TAG, "getLessonListFromServer: exception "+e.getLocalizedMessage());
        }

        //Add to Request Que

        if(mLessonListRequestor != null){

            mRequestQueue.addToRequestQue(mLessonListRequestor);
        }
        else{
            //no Request Obejct
            TimeoutError();
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLessonListRequestor = null;
        errorHandler = null;
        mLessonProvider = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                }
                else{
                    finish();
                }
        }

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


        try {

            mChangableRoot.removeAllViews();
            View v = LayoutInflater.from(this).inflate(R.layout.try_again_layout,mChangableRoot,true);
            TextView t = (TextView) v.findViewById(R.id.try_again_text);
            t.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mRequestQueue != null){
                        mRequestQueue.addToRequestQue(mLessonListRequestor);
                    }
                }
            });
        }
        catch (Exception e ){
            Log.d(TAG, "TimeoutError: ");
        }

    }

    @Override
    public void NetworkError() {


        try{

            mChangableRoot.removeAllViews();
            View v = LayoutInflater.from(this).inflate(R.layout.no_internet,mChangableRoot,true);
        }
        catch (Exception e ){
            // Do nothing
        }

    }

    @Override
    public void ServerError() {

        FirebaseCrash.log("Server Error");
        try{
            mChangableRoot.removeAllViews();
            View v = LayoutInflater.from(this).inflate(R.layout.no_data,mChangableRoot,true);
        }
        catch (Exception e){


        }


    }


    /*Got Lesson List from Provider*/

    @Override
    public void setLessonList(List<Lesson> mLessons, int overall) {

        mAdapter = new LessonListAdapter(getApplicationContext(),mLessons);

        try{

            mChangableRoot.removeAllViews();
            View listView = LayoutInflater.from(this).inflate(R.layout.transparent_recycler,mChangableRoot,true);
            mSubListView = (RecyclerView) listView.findViewById(R.id.subview_list);
            mSubListView.setLayoutManager(new LinearLayoutManager(this));
            mSubListView.addItemDecoration(new RVdecorator(ContextCompat.getDrawable(getApplicationContext(),R.drawable.divider)));

            if (mAdapter != null){
                mSubListView.setAdapter(mAdapter);
            }
        }
        catch (Exception e ){
            Log.d(TAG, "setLessonList: Exception");
        }


    }


    @Override
    public void noLesson() {

        try{

            mChangableRoot.removeAllViews();
            View v  = LayoutInflater.from(this).inflate(R.layout.no_data,mChangableRoot,true);
        }
        catch (Exception e ){
            Log.d(TAG, "Exception");
        }
    }
}
