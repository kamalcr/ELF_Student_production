package com.elf.elfstudent.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.elf.elfstudent.Adapters.ViewPagerAdapters.ReportPagerAdapter;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.RequestParameterKey;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;

/**
 * Created by nandhu on 18/10/16.
 *
 * The Top Level Report Activity that shows Overall Percentage in a Subject
 *
 *
 */

public class ReportActivity extends AppCompatActivity  implements  ErrorHandler.ErrorHandlerCallbacks
        , Response.Listener<JSONArray> ,Drawer.OnDrawerItemClickListener {


    private static final String TAG = "ELF";
    private static final String REPORT_URL = "";

    @BindView(R.id.report_tab)
    TabLayout mTab;
    @BindView(R.id.report_pager)
    ViewPager mPager;


    @BindView(R.id.report_toolbar)
    Toolbar mToolbar;


    Drawer result = null;

    ReportPagerAdapter mAdapter = null;


    //Request Queu
    private AppRequestQueue mRequestQueue;

    private ErrorHandler errorHandler = null;

    private DataStore mStore = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_activity);

        mRequestQueue = AppRequestQueue.getInstance(getApplicationContext());
        mStore = DataStore.getStorageInstance(getApplicationContext());

//        PrepareSubjectReportsFor(mStore.getStudentId());

        errorHandler = new ErrorHandler(this);

        setSupportActionBar(mToolbar);




        mPager.setAdapter(new ReportPagerAdapter(getSupportFragmentManager()));
        mTab.setupWithViewPager(mPager);
        initDrawer();

    }

    private void PrepareSubjectReportsFor(String studentId) {
        JSONObject mObject = new JSONObject();
        try {
            mObject.put(RequestParameterKey.STUDENT_ID,studentId);
        }
        catch (Exception e){
            Log.d(TAG, "PrepareSubjectReportsFor: ");
        }

        JsonArrayRequest mRequest = new JsonArrayRequest(Request.Method.POST,REPORT_URL,mObject,this,errorHandler);
        mRequestQueue.addToRequestQue(mRequest);
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

    /*
    *
    * This mehtod presents us a Method
    * it
    * subject Name , Subject iD , overall percentage, Lesson List and growth Percentage
    * may be witin lesson Percentage topic list may be present
    *
    * */
    @Override
    public void onResponse(JSONArray response) {









    }
}
