package com.elf.elfstudent.Adapters.ViewPagerAdapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.elf.elfstudent.Fragments.TesCompletedOverallFragment;
import com.elf.elfstudent.Fragments.TestComQuesListFrag;
import com.elf.elfstudent.Utils.BundleKey;

/**
 * Created by nandhu on 30/10/16.
 *
 * The pager Fragment is shown in {@link com.elf.elfstudent.Activities.TestCompletedActivity}
 *
 * Send TestID as arguments
 *
 *
 */
public class TestCompletedPagerAdapter extends FragmentStatePagerAdapter{
    private String TestId = null;
    private String subId = null;
    public TestCompletedPagerAdapter(FragmentManager fm, String testId, String subjectId) {
        super(fm);
        this.TestId = testId;
        this.subId = subjectId;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:return "OVERVIEW";
            case 1: return "REVIEW";

        }
        return null;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle b = new Bundle();

        //Add Test Id to Fragment
        b.putString(BundleKey.TEST_ID,this.TestId);
        b.putString(BundleKey.SUBJECT_ID,this.subId);
      switch (position){
          case 0:


              TesCompletedOverallFragment mFragment = new TesCompletedOverallFragment();
              mFragment.setArguments(b);

              return mFragment;
          case 1 :
              TestComQuesListFrag mFrag =  new TestComQuesListFrag();
              mFrag.setArguments(b);
              return mFrag;

      }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
