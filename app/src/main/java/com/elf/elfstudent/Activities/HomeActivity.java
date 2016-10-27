package com.elf.elfstudent.Activities;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
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
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.elf.elfstudent.Adapters.SubjectHomeAdapter;
import com.elf.elfstudent.CustomUI.HelviticaLight;
import com.elf.elfstudent.CustomUI.RoundedImageView;
import com.elf.elfstudent.CustomUI.SansRegularTextview;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.Network.JsonProcessors.HomePageDataProvider;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.BundleKey;
import com.elf.elfstudent.Utils.ScreenUtil;
import com.elf.elfstudent.model.SubjectModel;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v4.app.ActivityOptionsCompat.makeSceneTransitionAnimation;

/**
 * Created by nandhu on 17/10/16.
 * The Home Acitivity
 *
 */
public class HomeActivity extends AppCompatActivity implements SubjectHomeAdapter.onCardClick, ErrorHandler.ErrorHandlerCallbacks, HomePageDataProvider.HomeDataProvider,
        Drawer.OnDrawerItemClickListener {


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


    RecyclerView mList=null;


//    @BindView(R.id.try_again_text)
//    TextView mTryagain;




    //The Adapter for The list
    SubjectHomeAdapter mSubjectAdapter = null;


    //The Toolbar
    @BindView(R.id.elf_toolbar)
    Toolbar mToolbar;

    //The App bar layout

    @BindView(R.id.home_app_bar)
    AppBarLayout mAppbar;


    /*THe Drawer Related Atrributes*/
    //The Drop Down Icon
    @BindView(R.id.tool_bar_drop) ImageView mDropIcon;
    @BindView(R.id.home_drawer_frame) FrameLayout mdrawerLayout;
    @BindView(R.id.home_menu) RelativeLayout mHomeButton;
    @BindView(R.id.test_menu) RelativeLayout mTestButton;
    @BindView(R.id.report_menu ) RelativeLayout mReportButton;
    @BindView(R.id.test_report_menu) RelativeLayout mTestReportButton;
    @BindView(R.id.payments_menu) RelativeLayout mPaymentsButton;

    //The Root Content Layyout
    @BindView(R.id.home_root) FrameLayout mRoot;
    @BindView(R.id.home_root_scrollview)
    ScrollView mRootScroll;



    //The Request Queue
    private AppRequestQueue mRequestQueue= null;
    private List<SubjectModel> mSubjectList = null;

    ErrorHandler errorHandler ;
    HomePageDataProvider mDataProvider = null;


    //The Request object  which is Added to Request Queue;
    JsonArrayRequest mHomeRequest = null;

    //The Drawer
    Drawer result = null;


    @BindView(R.id.home_frame) FrameLayout mContentRoot;

    @BindView(R.id.home_relative_root) RelativeLayout mRelativeRoot;


    boolean isDrawerShowing = false;



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

        ActionBar ab  = getSupportActionBar();
        try {
            ab.setDisplayShowHomeEnabled(true); // show or hide the default home button

            ab.setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
            ab.setDisplayShowTitleEnabled(false);
        }
        catch (Exception e ){
            Log.d(TAG, "onCreate:  exception in toolbar");
        }

        errorHandler = new ErrorHandler(this);
        mDataProvider = new HomePageDataProvider(this);

        mDropIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DropButtonClicked();
            }
        });

//        // TODO: 25/10/16 save student Dash board
        prepareDashBoardFor("1");
        setSupportActionBar(mToolbar);




    setUpCustomDrawer();





    }

    private void setUpCustomDrawer() {

        //Report
        mReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final  Intent i = new Intent(getApplicationContext(),ReportActivity.class);
                startActivity(i);
            }
        });

        //Browse test Page
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
                })
                        .setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float value = valueAnimator.getAnimatedFraction();
                        mRelativeRoot.setTranslationX(value * mdrawerLayout.getWidth());


                    }
                }).start();
            }
        }
        else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
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
                        })
                        .setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                float va = valueAnimator.getAnimatedFraction();
                                mRelativeRoot.setTranslationX(-va);
                            }
                        })
                        .translationX(-ScreenUtil.getScreenWidth(getApplicationContext())).start();
            }
        }

        Log.d(TAG, "DropButtonClicked: ");

        isDrawerShowing = !isDrawerShowing;




    }

    private void initDrawer() {
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header_img)
                .addProfiles(
                        new ProfileDrawerItem().withName(mStore.getUserName()).withEmail(mStore.getEmailId()).withIcon(R.drawable.ic_account_circle_white_48dp)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(0).withName("Home").withIcon(R.drawable.ic_home_black_48dp)
                .withIconTintingEnabled(true);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem()
                .withIdentifier(1).withName("Reports")
                .withIcon(R.drawable.ic_assessment_black_24dp).withIconTintingEnabled(true);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(2).withName("Tests").withIcon(R.drawable.ic_assignment_black_48dp) .withIconTintingEnabled(true);
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(3).withName("Notifications").withIcon(R.drawable.ic_message_black_48dp) .withIconTintingEnabled(true);
        PrimaryDrawerItem item5 = new PrimaryDrawerItem().withIdentifier(4).withName("Test Reports");

        result = new DrawerBuilder()
                .withActivity(this)
                .addDrawerItems(
                        item1,
                        item2,
                        item3,
                        item4,
                        item5
                )
                .withHasStableIds(true)

                .withActionBarDrawerToggle(true)
                .withToolbar(mToolbar)
                .withOnDrawerItemClickListener(this)
                .withAccountHeader(headerResult)
                .build();



    }
    private void setViewValues() {

//        // TODO: 25/10/16 institiution values
        mSchoolname.setText("Sri Akilandeswari Vidhyala");
        mStandardName.setText("10th Standard");
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
       mHomeRequest = new JsonArrayRequest(Request.Method.POST, HOME_URL, mReqObjects, mDataProvider, errorHandler);
        Log.d(TAG, "onCreate: making request");
        mRequestQueue.addToRequestQue(mHomeRequest);

    }


    /*
    * Response is Obtained Stop SHoing Progress Bar
    * and Update the View
    * */


        @Override
        protected void onDestroy () {
            super.onDestroy();
            Log.d(TAG, "onDestroy: ");
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
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.home_activity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()){
           case R.id.profile_menu:
               final  Intent i = new Intent(this,ChangeProfileActivity.class);
               startActivity(i);
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
        }

        @Override
        public void onBackPressed () {
            Log.d(TAG, "onBackPressed: ");
            finish();
            super.onBackPressed();
            
        }

        @Override
        protected void onPause () {
            super.onPause();
            Log.d(TAG, "onPause: ");
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

                Log.d(TAG, "InfoButtonClicked: ");
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


                //Putting Transition Name Values

                i.putExtra(BundleKey.HOME_SUBJECT_TRANS_NAME,subTransName);
                i.putExtra(BundleKey.HOME_PERCENT_TRANS_NAME,percentTransName);

                i.putExtra(BundleKey.HOME_SUBJECT_IMAGE_TRANS_NAME,img_trans_name);

                //send Subject iD to NExt Activity
                i.putExtra(BundleKey.SUBJECT_ID,mSubjectList.get(position).getmSubjectId());

                Log.d(TAG, "Sending Transition Values "+subTransName +" "+percentTransName + " "+img_trans_name);



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
            catch (Exception e ){
                Log.d(TAG, "InfoButtonClicked: Exception "+e.getLocalizedMessage());
            }
        }

        @Override
        public void DetailsButtonClicked(int position, View itemView){


        }

    @Override
    public void TimeoutError() {

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

    @Override
    public void NetworkError() {
        Log.d(TAG, "NetworkError: ");
        mRoot.removeAllViews();
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.no_internet,mRoot,true);


        Button b = (Button)view.findViewById(R.id.no_internet_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick:NO Internet Button CLicked");
            }
        });



    }

    @Override
    public void ServerError() {


        Log.d(TAG, "ServerError: ");

        Log.d(TAG, "ServerError: ");
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
        mRoot.removeAllViews();
        View v  = LayoutInflater.from(this).inflate(R.layout.home_recycler,mRoot,true);

        mList = (RecyclerView) v.findViewById(R.id.home_list);
        try {
            mList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            mList.setAdapter(mSubjectAdapter);
            mList.setHasFixedSize(true);
        }
        catch (Exception e ){
            Log.d(TAG, "Exception in setting adapter to Recycler view");
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

    }



    /*From Drawer Item CLick events
    *
    * getIdentifier and Send to Appropriate Activity
    * */
    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        Intent i = null;
        if (drawerItem != null) {
            if (drawerItem.getIdentifier() == 0) {
              //Current Activity do Nothing
                return true;
            }
            if (drawerItem.getIdentifier() == 1) {
             i = new Intent(this,ReportActivity.class);
            }
            if (drawerItem.getIdentifier() == 2) {
              i =new Intent(this,BrowseTestActivity.class);
            }
            if (drawerItem.getIdentifier() == 3) {
               i = new Intent(this,NotificationsActivity.class);
            }
            if (drawerItem.getIdentifier() == 4){
              i = new Intent(this,TestReportsActivity.class);
            }


            result.closeDrawer();

            if (i != null){
                startActivity(i);
            }




        }
        return true;
    }
}
