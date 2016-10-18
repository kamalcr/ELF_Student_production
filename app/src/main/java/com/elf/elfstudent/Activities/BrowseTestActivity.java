package com.elf.elfstudent.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.elf.elfstudent.Adapters.ViewPagerAdapters.TestPagerAdapter;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Fragments.AllTestFragment10;
import com.elf.elfstudent.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 18/10/16.
 * THe Activity that is used to Browse Tests
 * It has a view pager which has 3 fragments
 *   {
 *    {@link AllTestFragment10}
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

public class BrowseTestActivity extends AppCompatActivity {


    //The singeleton class which provides student details
    private DataStore mStore;


    //The Views

    //The Tab Layout
    @BindView(R.id.test_tab)
    TabLayout mTab;

    //The View pager
    @BindView(R.id.test_pager)
    ViewPager mPager;

    //The Pager Adapter for View pager
    TestPagerAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_test_activity);
        ButterKnife.bind(this);
        mAdapter = new TestPagerAdapter(getSupportFragmentManager());

        if (mAdapter != null) {

            mPager.setAdapter(mAdapter);
        }

        mTab.setupWithViewPager(mPager);
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
}
