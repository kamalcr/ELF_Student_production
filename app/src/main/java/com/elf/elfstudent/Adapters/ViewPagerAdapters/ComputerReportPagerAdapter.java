package com.elf.elfstudent.Adapters.ViewPagerAdapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.elf.elfstudent.Fragments.ReportFragment;
import com.elf.elfstudent.Utils.BundleKey;

/**
 * Created by nandhu on 26/10/16.
 */
public class ComputerReportPagerAdapter extends FragmentStatePagerAdapter {



    String overall;
    String mSubjectName  = "";
    String mSubjectId = "";
    //    List<T> LessoList  =



    public ComputerReportPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0 :
                return "PHYSCICS";
            case 1:
                return "CHEMISTRY";
            case 2:
                return "MATHS";
            case 3:
                return "COMPUTER";



        }
        return null;
    }

    @Override
    public Fragment getItem(int position) {

        ReportFragment fragment = new ReportFragment();

        switch (position){
            //based on subjects , set Arguments to Fragemnt such as
            //overall percentage , List of Lessons etc

//            todo add correct subject id
            case 0: mSubjectId = "2";
                mSubjectName = "PHYSCICS";
                break;
            case 1 :
                mSubjectId = "3";
                mSubjectName = "CHEMISTRY";
                break;
            case 2:
                mSubjectId = "1";
                mSubjectName = "MATHS";
                break;
            case 3:
                mSubjectId = "14";
                mSubjectName = "COMPUTER";
                break;



        }





        Bundle b  = new Bundle();
        b.putString(BundleKey.SUBJECT_ID,mSubjectId);
        b.putString(BundleKey.SUBJECT_NAME,mSubjectName);
        fragment.setArguments(b);

        return fragment;











    }

    @Override
    public int getCount() {
        return 4;
    }
}
