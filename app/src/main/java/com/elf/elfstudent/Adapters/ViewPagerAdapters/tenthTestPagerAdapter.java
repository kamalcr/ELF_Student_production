package com.elf.elfstudent.Adapters.ViewPagerAdapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.elf.elfstudent.Fragments.AllTestFragment;
import com.elf.elfstudent.Fragments.AllTestFragment12;
import com.elf.elfstudent.Fragments.PublicTestFragmet;
import com.elf.elfstudent.Fragments.RecommendedTestFragment;
import com.elf.elfstudent.Utils.BundleKey;

/**
 * Created by nandhu on 18/10/16.
 * Adapter which provides Fragment to {@link com.elf.elfstudent.Activities.BrowseTestActivity}
 *
 */
public class tenthTestPagerAdapter extends FragmentPagerAdapter {


    private static final String TAG = "TEST ADAPTER";
    public boolean istenth = false;
    public boolean isComputer = false;
    public boolean isBio = false;
    Fragment fragment = null;
    public tenthTestPagerAdapter(FragmentManager fm, boolean i, int i1) {

        super(fm);


        if (i){
            //Student is tenth
            istenth = true;
            Log.d("Adapter", "tenth Student: ");

            fragment  = new AllTestFragment();

        }
         else{
            Bundle b = new Bundle();

            //SHow 12 AllTest page with Subject iD and name
            fragment = new AllTestFragment();
            if (i1 == 1){
                //student is Computer
                Log.d(TAG, "tenthTestPagerAdapter: student is computer");
                isComputer = true;
                isBio = false;

                b.putString(BundleKey.SUBJECT_ID,"12");
                b.putString(BundleKey.SUBJECT_NAME,"COMPUTER");


            }
            else{
                //student is Bio
                isBio = true;
                isComputer = false;
                Log.d(TAG, "tenthTestPagerAdapter: student is bio");
                b.putString(BundleKey.SUBJECT_ID,"12");
                b.putString(BundleKey.SUBJECT_NAME,"BIOLOGY");
            }
            if (b!=null){

                fragment.setArguments(b);
            }

        }


    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "All";

            case 1:
                return "Recommended";

            case 2:
                return "Public";

        }
        return "Null";
    }

    @Override
    public Fragment getItem(int position) {


        switch (position) {
            case 0:
              if (fragment != null){
                  return fragment;
              }
            case 1:
                return new RecommendedTestFragment();
            case 2:
                return new PublicTestFragmet();
        }
        return null;
    }


    @Override
    public int getCount() {
        return 3;
    }
}
