package com.elf.elfstudent.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.elf.elfstudent.Adapters.ViewPagerAdapters.TestPagerAdapter;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Fragments.AllTestFragment;
import com.elf.elfstudent.R;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 18/10/16.
 * THe Activity that is used to Browse Tests
 * It has a view pager which has 3 fragments
 *   {
 *    {@link AllTestFragment}
 *    {@link com.elf.elfstudent.Fragments.PublicTestFragmet}
 *    {@link com.elf.elfstudent.Fragments.RecommendedTestFragment}
 *
 *    this is provided by {@link TestPagerAdapter}
 *
 *
 *   }
 *
 *   supply each Fragment with Student iD or Data Store
 */

public class BrowseTestActivity extends AppCompatActivity implements Drawer.OnDrawerItemClickListener {


    //The singeleton class which provides student details
   


    //The Views

    //The Tab Layout
    @BindView(R.id.test_tab)
    TabLayout mTab;

    //The View pager
    @BindView(R.id.test_pager)
    ViewPager mPager;

    @BindView(R.id.browse_test_toolbar)
    Toolbar mToolbar;

    //The Pager Adapter for View pager
    TestPagerAdapter mAdapter;

    Drawer result = null;


    DataStore mStore = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_test_activity);
        ButterKnife.bind(this);
        mAdapter = new TestPagerAdapter(getSupportFragmentManager());
        mStore = DataStore.getStorageInstance(getApplicationContext());

        if (mAdapter != null) {

            mPager.setAdapter(mAdapter);
        }

        mTab.setupWithViewPager(mPager);
        setSupportActionBar(mToolbar);

        initDrawer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(2).withName("Tests").withIcon(R.drawable.ic_assignment_black_48dp).withIconTintingEnabled(true);
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(3).withName("Notifications").withIcon(R.drawable.ic_message_black_48dp).withIconTintingEnabled(true);
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
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        Intent i = null;
        if (drawerItem != null) {
            if (drawerItem.getIdentifier() == 0) {
                //Current Activity do Nothing
                return true;
            }
            if (drawerItem.getIdentifier() == 1) {
                i = new Intent(this, ReportActivity.class);
            }
            if (drawerItem.getIdentifier() == 2) {
                i = new Intent(this, BrowseTestActivity.class);
            }
            if (drawerItem.getIdentifier() == 3) {
                i = new Intent(this, NotificationsActivity.class);
            }
            if (drawerItem.getIdentifier() == 4) {
                i = new Intent(this, TestReportsActivity.class);
            }


            result.closeDrawer();

            if (i != null) {
                startActivity(i);
            }


        }
        return true;
    }
}
