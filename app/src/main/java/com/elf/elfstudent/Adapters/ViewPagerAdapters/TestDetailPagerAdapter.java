package com.elf.elfstudent.Adapters.ViewPagerAdapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.elf.elfstudent.Fragments.IndepthFragment;
import com.elf.elfstudent.Fragments.OverAllTestFragment;

/**
 * Created by nandhu on 20/10/16.
 * The Pager Adapter which is provided to {@link com.elf.elfstudent.Activities.DetailedTestReportActivity}
 */
public class TestDetailPagerAdapter  extends FragmentPagerAdapter{
    public TestDetailPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new OverAllTestFragment();
            case 1 :
                return new IndepthFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
