package com.elf.elfstudent.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.elf.elfstudent.Fragments.EventsFragement;
import com.elf.elfstudent.Fragments.RequestFragment;

/**
 * Created by nandhu on 10/11/16.
 *
 */
public class NotificationPagerAdapter  extends FragmentPagerAdapter{

    public NotificationPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
       switch (position){
           case 0: return new EventsFragement();
           case 1 : return new RequestFragment();
       }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0 : return "EVENTS";
            case 1:return "REQUESTS";
        }
        return null;
    }
}
