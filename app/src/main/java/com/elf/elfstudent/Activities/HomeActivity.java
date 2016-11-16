package com.elf.elfstudent.Activities;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityOptionsCompat;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.elf.elfstudent.Adapters.SubjectHomeAdapter;
import com.elf.elfstudent.CustomUI.HelviticaLight;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.Network.JsonProcessors.HomePageDataProvider;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.BundleKey;
import com.elf.elfstudent.Utils.ScreenUtil;
import com.elf.elfstudent.model.SubjectModel;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.support.v4.app.ActivityOptionsCompat.makeSceneTransitionAnimation;

/**
 * Created by nandhu on 17/10/16.
 * The Home Acitivity
 *
 */
public class HomeActivity extends AppCompatActivity  implements ErrorHandler.ErrorHandlerCallbacks,
        HomePageDataProvider.HomeDataProvider,SubjectHomeAdapter.onCardClick{

    private static final String HOME_URL ="http://www.hijazboutique.com/elf_ws.svc/GetStudentDashboard";
    private static final String TAG = "ELF";


    @BindView(R.id.home_state_image)
    CircleImageView mStateImage;
    @BindView(R.id.home_overall_image) CircleImageView overall;
    @BindView(R.id.home_district_image) CircleImageView mdist;



    DataStore mStore;

    //the Views OF this Activity



//

    //student Name
    @BindView(R.id.home_student_name)
    HelviticaLight mStudentName;

    //School Name
    @BindView(R.id.home_school_name) HelviticaLight mSchoolname;

    //standard Name
    @BindView(R.id.home_section_name) HelviticaLight mStandardName;

    //Profile picture
    @BindView(R.id.home_profile_imageview)
    CircleImageView mProfilePicture;

    //state Rank Value

    @BindView(R.id.home_state_value)
    HelviticaLight mStateRank;

    //Overall rank

    @BindView(R.id.home_overall_rank_value) HelviticaLight mOverallValue;

    //District

    @BindView(R.id.home_dist_rank_value) HelviticaLight mDistrictRankValue;



    //The Subject List


    RecyclerView mList=null;


//    @BindView(R.id.try_again_text)
//    TextView mTryagain;




    //The Adapter for The list
    SubjectHomeAdapter mSubjectAdapter = null;


    //The Toolbar
    @BindView(R.id.elf_toolbar)
    Toolbar mToolbar;

    //The App bar layout




    //The Drop Down Icon
    @BindView(R.id.tool_bar_drop) ImageView mDropIcon;
    @BindView(R.id.home_drawer_frame) FrameLayout mdrawerLayout;
    @BindView(R.id.home_menu) CardView mHomeButton;
    @BindView(R.id.test_menu) CardView mTestButton;
    @BindView(R.id.report_menu ) CardView mReportButton;
    @BindView(R.id.test_report_menu) CardView mTestReportButton;
    @BindView(R.id.payments_menu) CardView mPaymentsButton;

    //The Root Content Layyout
    @BindView(R.id.changable_home_root) FrameLayout mRoot;
    @BindView(R.id.home_root_scrollview)
    ScrollView mRootScroll;





    FirebaseAnalytics mAnalytics = null;

    //The Request Queue
    private AppRequestQueue mRequestQueue= null;
    private List<SubjectModel> mSubjectList = null;

    ErrorHandler errorHandler ;
    HomePageDataProvider mDataProvider = null;


    //The Request object  which is Added to Request Queue;
    JsonArrayRequest mHomeRequest = null;

    //The Drawer





    boolean isDrawerShowing = false;
    String studnetId = null;
    private int count = 0;
    private boolean adapterSet = false;

    
    
    public void pop(String s ){
        Log.d(TAG, " "+s);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        ButterKnife.bind(this);
        
        pop("oncreate");





        //setting trophy images

        Picasso.with(this).load(R.drawable.district).into(mdist);
        mStateImage.setImageResource(R.drawable.state);
        overall.setImageResource(R.drawable.overall);
        mdist.setImageResource(R.drawable.district);

        //get The details for this User from Shared PRefs
        mStore  = DataStore.getStorageInstance(this.getApplicationContext());
        setViewValues();


        //initialising Request Queue
        mRequestQueue = AppRequestQueue.getInstance(getApplicationContext());





        //Toolbar setup
        setSupportActionBar(mToolbar);

        ActionBar ab  = getSupportActionBar();
        try {
            ab.setDisplayShowHomeEnabled(true); // show or hide the default home button

            ab.setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
            ab.setDisplayShowTitleEnabled(false);
        }
        catch (Exception e ){
            Log.d(TAG, "onCreate:  exception in toolbar");
        }


        // Network Related Codes
        errorHandler = new ErrorHandler(this);
        mDataProvider = new HomePageDataProvider(this);

        //navigation ICon

        mDropIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DropButtonClicked();
            }
        });

        if (mStore != null){
            studnetId = mStore.getStudentId();
        }
        else{
            FirebaseCrash.log("Store null in Home");
        }
        //sending DahsoArd Request
        if (studnetId != null){

            prepareDashBoardFor(studnetId);
        }

         setUpCustomDrawer();





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
                final Intent i  = new Intent(getApplicationContext(),PaymentActivity.class);
                startActivity(i);
            }
        });
    }

    private void DropButtonClicked() {
//


        if (!isDrawerShowing){
            mdrawerLayout.setTranslationX(-ScreenUtil.getScreenWidth(this));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mdrawerLayout.animate().translationX(0).setInterpolator(new DecelerateInterpolator(1.5f)).setDuration(600)
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
        }
        else{

                mdrawerLayout.animate().setDuration(500)
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

        Log.d(TAG, "DropButtonClicked: ");

        isDrawerShowing = !isDrawerShowing;




    }


    private void setViewValues() {


        if(mStore != null){

            Picasso.with(this).load(R.mipmap.pro_pic)
                    .resize(100,100)
                    .into(mProfilePicture);
            mSchoolname.setText(mStore.getInstituionName());

            mStandardName.setText(String.format("%s Grade", mStore.getStandard()));
            mStudentName.setText(mStore.getUserName());
            studnetId  = mStore.getStudentId();
        }
    }

    private void prepareDashBoardFor(String studentId) {
        JSONObject mReqObjects = new JSONObject();
        try {
            mReqObjects.put("studentId", studentId);
        }
        catch (Exception e) {
            FirebaseCrash.log("Error in putting JSON value , Home");
        }


       mHomeRequest = new JsonArrayRequest(Request.Method.POST, HOME_URL, mReqObjects, mDataProvider, errorHandler);
        if (mRequestQueue !=null){

            mRequestQueue.addToRequestQue(mHomeRequest);
        }

    }


    /*
    * Response is Obtained Stop SHoing Progress Bar
    * and Update the View
    *
    *
    * */


        @Override
        protected void onDestroy (){
            super.onDestroy();
            Log.d(TAG, "onDestroy: ");
            mStore = null;
            mDataProvider = null;
            errorHandler = null;


        }

        @Override
        protected void onSaveInstanceState (Bundle outState){
            super.onSaveInstanceState(outState);

        }

        @Override
        protected void onStart () {
            super.onStart();
            mAnalytics = FirebaseAnalytics.getInstance(this);
        }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_activity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()){
           case R.id.profile_menu:
               final  Intent i = new Intent(this,ChangeProfileActivity.class);
               startActivity(i);

               break;
           case R.id.about_menu :
               final  Intent ii  = new Intent(this,AboutUsActivity.class);
               startActivity(ii);
               break;
           case R.id.settings_menu :
               final Intent iii = new Intent(this,SettingActivity.class);
               startActivity(iii);
               break;
           case R.id.feedback_menu:
               final  Intent iiii = new Intent(this,FeedbackActivity.class);
               startActivity(iiii);
               break;
       }
        return true;
    }

    @Override
        protected void onStop () {
            super.onStop();
            errorHandler = null;
            mDataProvider = null;
            mSubjectAdapter = null;
        Log.d(TAG, "onStop: ");
        }

        @Override
        public void onBackPressed () {
            Log.d(TAG, "onBackPressed: ");
            finish();

            
        }

        @Override
        protected void onPause () {
            super.onPause();

//          unbindDrawables(mProfilePicture);


        }


    //Unbinds Drawable , call this method in onPause , with a  to call System.gc();

    /**
     * see the post  <a href = "http://stackoverflow.com/questions/14620848/getting-out-of-memory-error-while-starting-a-activity-in-android-app"></a>
     *
     *  for futher clairificaitons
     * */



    private void unbindDrawables(View view) {
        try{
            System.out.println("UNBINDING"+view);
            if (view.getBackground() != null) {

                ((BitmapDrawable)view.getBackground()).getBitmap().recycle();
                view.getBackground().setCallback(null);
                view=null;
            }

            if (view instanceof ViewGroup) {
                for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                    unbindDrawables(((ViewGroup) view).getChildAt(i));
                }
                ((ViewGroup) view).removeAllViews();
            }

        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
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



            try{
                if(isDrawerShowing){

                    DropButtonClicked();
                }
                Log.d(TAG, "InfoButtonClicked: ");
                //get the view and transition Name of views
//                HelviticaLight subjectName = (HelviticaLight) itemView.findViewById(R.id.subject_title);
//                String subTransName = ViewCompat.getTransitionName(subjectName);
                CardView viewRoot = (CardView) itemView;
                HelviticaLight percentText = (HelviticaLight) itemView.findViewById(R.id.percent);
                String percentTransName = ViewCompat.getTransitionName(percentText);


                ImageView subjectImage = (ImageView)itemView.findViewById(R.id.home_card_sub_imageview);
                String img_trans_name = ViewCompat.getTransitionName(subjectImage);

                //Intent for Next Activity
                Intent i = new Intent(this,SubjectViewActivity.class);
//                i.putExtra(BundleKey.SUBJECT_NAME,subjectName.getText());
                i.putExtra(BundleKey.PERCENTAGE,percentText.getText());
                i.putExtra(BundleKey.SUBJECT_ID,mSubjectList.get(position).getmSubjectId());
//            i.putExtra(BundleKey.ROOT_VIEW_TRANS_NAME,root_transName);


                //Putting Transition Name Values

//                i.putExtra(BundleKey.HOME_SUBJECT_TRANS_NAME,subTransName);           i.p
                i.putExtra(BundleKey.ARG_STUDENT_ID,studnetId);
                i.putExtra(BundleKey.HOME_PERCENT_TRANS_NAME,percentTransName);

                i.putExtra(BundleKey.HOME_SUBJECT_IMAGE_TRANS_NAME,img_trans_name);

                //send Subject iD to NExt Activity
                i.putExtra(BundleKey.SUBJECT_ID,mSubjectList.get(position).getmSubjectId());

//                Log.d(TAG, "Sending Transition Values "+subTransName +" "+percentTransName + " "+img_trans_name);



                //MAking pairs for many Share elements
                Pair<View, String> p1 = Pair.create((View)subjectImage, img_trans_name);
//                Pair<View, String> p2 = Pair.create((View) subjectName, subTransName);
                Pair<View, String> p3 = Pair.create((View)percentText, percentTransName);


                ActivityOptionsCompat options = makeSceneTransitionAnimation(this, p1,p3);
                if (ScreenUtil.isAndroid5()){

                    startActivity(i, options.toBundle());
                }else{
                    startActivity(i);
                }
            }
            catch (Exception e ){
                Log.d(TAG, "InfoButtonClicked: Exception "+e.getLocalizedMessage());
            }
        }

        @Override
        public void DetailsButtonClicked(int position, View itemView){


        }

    @Override
    public void TimeoutError() {


        Bundle  b = new Bundle();
        b.putString(BundleKey.TIMEOUT,BundleKey.TIMEOUT);
        mAnalytics.logEvent(BundleKey.TIMEOUT,b);


        if (!(count>2)){


            //Retrying
            if (mHomeRequest != null){
                mRequestQueue.addToRequestQue(mHomeRequest);
                count++;
            }
        }
        else{

            mRoot.removeAllViews();
            View v = View.inflate(this,R.layout.try_again_layout,mRoot);
            ButterKnife.bind(v);
            TextView t = (TextView) v.findViewById(R.id.try_again_text);
            t.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mRequestQueue != null){
                        mRequestQueue.addToRequestQue(mHomeRequest);
                    }
                }
            });
        }


    }

    @Override
    public void NetworkError() {
        Log.d(TAG, "NetworkError: ");
        mRoot.removeAllViews();
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.no_internet,mRoot,true);






    }

    @Override
    public void ServerError() {
        FirebaseCrash.log("Error in server");

        mRoot.removeAllViews();
        View v = View.inflate(this,R.layout.send_feedback,mRoot);
    }



    /**   Inter face Methods called from {@link HomePageDataProvider }
     *   This  Method provides the Process Response
     *
     * */


    @Override
    public void NewDataReceived(String overallRank, String districtRank, String stateRank, List<SubjectModel> mSubjectList) {


        this.mSubjectList = mSubjectList;
        mSubjectAdapter = getNewOrModifiedAdapter(mSubjectList);
        setListAdapter(mSubjectAdapter);

        //Set Rank Values
        mStateRank.setText(stateRank);
        mOverallValue.setText(overallRank);
        mDistrictRankValue.setText(districtRank);
    }

    private void setListAdapter(SubjectHomeAdapter mSubjectAdapter) {

        //Remove the Present View in Frame Layout ,

        if (!adapterSet){
            //if adapter is not set .. then

            mRoot.removeAllViews();
            View v  = LayoutInflater.from(this).inflate(R.layout.home_recyler_view,mRoot,true);

            mList = (RecyclerView) v.findViewById(R.id.home_list_rv);
            try {
                adapterSet = true;
                mList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                mList.setAdapter(mSubjectAdapter);
                mList.setHasFixedSize(true);
            }
            catch (Exception e ){
                FirebaseCrash.log("Exception in setting Home Adapter");
            }
        }
        else{
            // adapter already set
            //dont do anything
            Log.d(TAG, "adapter already  set");
        }
    }



    /**
     *This method Returns NEw Adapter from the data or calls notifydatasetChanged
     * {@param List<SubjectModel> subjectList}
     * {@return SubjectHomeAdapter mAdapter }
    * */

    private SubjectHomeAdapter getNewOrModifiedAdapter(List<SubjectModel> mSubjectList) {
        if (mSubjectAdapter == null){
            mSubjectAdapter = new SubjectHomeAdapter(getApplicationContext(),mSubjectList,this);
            return mSubjectAdapter;
        }else{
            //Adapter Already Exists
            mSubjectAdapter.setNewSubjectList(mSubjectList);
            return mSubjectAdapter;
        }
    }



    @Override
    public void NoDataReceivedFromWebservice() {
        FirebaseCrash.log("getting Response but showing no data in Home student dashboard API");

        if (mRoot != null){
            try {

                View v = View.inflate(this,R.layout.no_data,mRoot);
            }
            catch (Exception e ){
                Toast.makeText(this,"NO data Received , please try again later",Toast.LENGTH_SHORT).show();
            }
        }
    }


}
