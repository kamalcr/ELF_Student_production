package com.elf.elfstudent.Adapters.ViewPagerAdapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.elf.elfstudent.Fragments.TesCompletedOverallFragment;
import com.elf.elfstudent.Fragments.TestComQuesListFrag;

/**
 * Created by nandhu on 30/10/16.
 *
 */
public class TestCompletedPagerAdapter extends FragmentStatePagerAdapter{
    public TestCompletedPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:return "OVERALL";
            case 1: return "REVIEW";

        }
        return null;
    }

    @Override
    public Fragment getItem(int position) {
      switch (position){
          case 0:
              return new TesCompletedOverallFragment();
          case 1 :return new TestComQuesListFrag();
      }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
