package com.elf.elfstudent.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.elf.elfstudent.Adapters.SubjectHomeAdapter;
import com.elf.elfstudent.CustomUI.HelviticaLight;
import com.elf.elfstudent.CustomUI.RoundedImageView;
import com.elf.elfstudent.CustomUI.SansRegularTextview;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.BundleKey;
import com.elf.elfstudent.Utils.ScreenUtil;
import com.elf.elfstudent.model.SubjectModel;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v4.app.ActivityOptionsCompat.makeSceneTransitionAnimation;
import static com.elf.elfstudent.R.layout.no_internet;

/**
 * Created by nandhu on 17/10/16.
 * The Home Acitivity
 *
 */
public class HomeActivity extends AppCompatActivity implements SubjectHomeAdapter.onCardClick, ErrorHandler.ErrorHandlerCallbacks {


    private static final String HOME_URL = "http://www.hijazboutique.com/elf_ws.svc/GetStudentDashboard";
    private static final String TAG = "ELF";


    DataStore mStore;

    //the Views OF this Activity


    //student Name
    @BindView(R.id.home_student_name)
    HelviticaLight mStudentName;

    //School Name
    @BindView(R.id.home_school_name) HelviticaLight mSchoolname;

    //standard Name
    @BindView(R.id.home_section_name) HelviticaLight mStandardName;

    //Profile picture
    @BindView(R.id.home_profile_imageview)
    RoundedImageView mProfilePicture;

    //state Rank Value

    @BindView(R.id.home_state_value)
    SansRegularTextview mStateRank;

    //Overall rank

    @BindView(R.id.home_overall_rank_value) SansRegularTextview mOverallValue;

    //District

    @BindView(R.id.home_dist_rank_value) SansRegularTextview mDistrictRankValue;



    //The Subject List

    @BindView(R.id.home_list)
    RecyclerView mList;


    //The Adapter for The list
    SubjectHomeAdapter mSubjectAdapter;

    //The Progress Bar
    @BindView(R.id.home_progress_bar)
    AVLoadingIndicatorView mProgressbar;

    //The Toolbar
    @BindView(R.id.act_toolbar)
    Toolbar mToolbar;

    //The Request Queue
    private AppRequestQueue mRequestQueue= null;
    private List<SubjectModel> mSubjectList;

    ErrorHandler errorHandler ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        ButterKnife.bind(this);


        //get The details for this User from Shared PRefs
        mStore  = DataStore.getStorageInstance(this.getApplicationContext());
        setViewValues();

        //initialising Request Queue
        mRequestQueue = AppRequestQueue.getInstance(getApplicationContext());


        setSupportActionBar(mToolbar);

        errorHandler = new ErrorHandler(this);
        prepareDashBoardFor("1");
        setSupportActionBar(mToolbar);








    }

    private void setViewValues() {
        mSchoolname.setText(mStore.getInstituionName());
        mStandardName.setText(mStore.getStandard());
        mStudentName.setText(mStore.getUserName());
    }

    private void prepareDashBoardFor(String studentId) {
        JSONObject mReqObjects = new JSONObject();
        try {


            //// TODO: 23/8/16 dynamic student id
            mReqObjects.put("studentId", studentId);

        }
        catch (Exception e) {

        }
        JsonArrayRequest mReq = new JsonArrayRequest(Request.Method.POST, HOME_URL, mReqObjects, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                processResponse(response);
            }
        }, errorHandler);
        Log.d(TAG, "onCreate: making request");
        mRequestQueue.addToRequestQue(mReq);

    }


    /*
    * Response is Obtained Stop SHoing Progress Bar
    * and Update the View
    * */
    private void processResponse(JSONArray response) {
        if (mSubjectList == null) {
            mSubjectList = new ArrayList<>();
        }
        try {


            //the full objet ,from object get array  -- rank + subject list
            JSONObject mObject = response.getJSONObject(0);


            Log.d(TAG, "Full Response "+mObject.toString());
            // the rank array  has one array element(object) ,
            // subject array has dynamic array elements (objects)
            JSONArray mRankArray = mObject.getJSONArray("rank");


            Log.d(TAG, "Rank Array: "+mRankArray.toString());


            JSONArray mSubjectArray = mObject.getJSONArray("subjects");

            //get the first rank object which has list of key value pairs
            JSONObject mRankObject = mRankArray.getJSONObject(0);

            Log.d(TAG, "Rank Objects "+mRankObject.toString());


            // loop through subject list
            for (int i = 0; i < mSubjectArray.length(); i++) {

                // add to subject model
                JSONObject mSubject = mSubjectArray.getJSONObject(i);
                mSubjectList.add(new SubjectModel(mSubject.getString("SubjectName")
                        , mSubject.getString("SubjectId")
                        , mSubject.getString("Percentage")));


            }
            Log.d(TAG, "processResponse: ");

            if (mProgressbar.isShown()) {
                mProgressbar.setVisibility(View.INVISIBLE);
            }
            if (!mList.isShown()) {
                mList.setVisibility(View.VISIBLE);
            }
            mSubjectAdapter = new SubjectHomeAdapter(getApplicationContext(), mSubjectList, this);


//            mSubjectAdapterReady = true;
            Log.d(TAG, "settting adpater");

            mList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            mList.setAdapter(mSubjectAdapter);


        } catch (Exception e) {
            Log.d(TAG, "processResponse: ");
        }
    }

        @Override
        protected void onDestroy () {
            super.onDestroy();
        }

        @Override
        protected void onSaveInstanceState (Bundle outState){
            super.onSaveInstanceState(outState);
        }

        @Override
        protected void onStart () {
            super.onStart();
        }

        @Override
        protected void onStop () {
            super.onStop();
        }

        @Override
        public void onBackPressed () {
            super.onBackPressed();
        }

        @Override
        protected void onPause () {
            super.onPause();
        }

        @Override
        protected void onResume () {
            super.onResume();
        }

        @Override
        protected void onRestoreInstanceState (Bundle savedInstanceState){
            super.onRestoreInstanceState(savedInstanceState);
        }


        @Override
        public void InfoButtonClicked(int position, View itemView){

            //get the view and transition Name of views
          HelviticaLight subjectName = (HelviticaLight) itemView.findViewById(R.id.subject_title);
            String subTransName = ViewCompat.getTransitionName(subjectName);
            CardView viewRoot = (CardView) itemView;
            String root_transName = ViewCompat.getTransitionName(viewRoot);
            HelviticaLight percentText = (HelviticaLight) itemView.findViewById(R.id.percent);
            String percentTransName = ViewCompat.getTransitionName(percentText);


            ImageView subjectImage = (ImageView)itemView.findViewById(R.id.home_card_sub_imageview);
            String img_trans_name = ViewCompat.getTransitionName(subjectImage);

            //Intent for Next Activity
            Intent i = new Intent(this,SubjectViewActivity.class);
            i.putExtra(BundleKey.SUBJECT_NAME,subjectName.getText());
            i.putExtra(BundleKey.PERCENTAGE,percentText.getText());
//            i.putExtra(BundleKey.ROOT_VIEW_TRANS_NAME,root_transName);
            i.putExtra(BundleKey.HOME_SUBJECT_TRANS_NAME,subTransName);
            i.putExtra(BundleKey.HOME_PERCENT_TRANS_NAME,percentTransName);
            Log.d(TAG, "InfoButtonClicked: image transName "+img_trans_name);
            i.putExtra(BundleKey.HOME_SUBJECT_IMAGE_TRANS_NAME,img_trans_name);

            //send Subject iD to NExt Activity
            i.putExtra(BundleKey.SUBJECT_ID,mSubjectList.get(position).getmSubjectId());

            //MAking pairs for many Share elements
            Pair<View, String> p1 = Pair.create((View)subjectImage, img_trans_name);
            Pair<View, String> p2 = Pair.create((View) subjectName, subTransName);
            Pair<View, String> p3 = Pair.create((View)percentText, percentTransName);


            ActivityOptionsCompat options = makeSceneTransitionAnimation(this, p1, p2,p3);
            if (ScreenUtil.isAndroid5()){

                startActivity(i, options.toBundle());
            }else{
                startActivity(i);
            }
        }

        @Override
        public void DetailsButtonClicked(int position, View itemView){


        }

    @Override
    public void TimeoutError() {
        Log.d(TAG, "TimeoutError: ");
        prepareDashBoardFor(mStore.getStudentId());
    }

    @Override
    public void NetworkError() {
        Log.d(TAG, "NetworkError: ");
        FrameLayout mRoot = (FrameLayout) findViewById(R.id.home_frame);
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.no_internet,mRoot,false);
        mRoot.removeAllViews();
        mRoot.addView(view);



    }

    @Override
    public void ServerError() {


        Log.d(TAG, "ServerError: ");
    }
}
