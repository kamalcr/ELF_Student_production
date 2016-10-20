package com.elf.elfstudent.Adapters.ViewPagerAdapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.elf.elfstudent.Fragments.AllTestFragment;
import com.elf.elfstudent.Fragments.PublicTestFragmet;
import com.elf.elfstudent.Fragments.RecommendedTestFragment;

/**
 * Created by nandhu on 18/10/16.
 * Adapter which provides Fragment to {@link com.elf.elfstudent.Activities.BrowseTestActivity}
 *
 */
public class TestPagerAdapter extends FragmentPagerAdapter {


    public TestPagerAdapter(FragmentManager fm) {
        super(fm);
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
                return new AllTestFragment();
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
