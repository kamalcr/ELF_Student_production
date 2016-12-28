package com.elf.elfstudent.Adapters.ViewPagerAdapters;

import android.os.Bundle;
import android.support.v4.app.BundleCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.elf.elfstudent.Activities.SingleSubjectReportActivity;
import com.elf.elfstudent.Fragments.ReportFragment;
import com.elf.elfstudent.Utils.BundleKey;

import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by nandhu on 20/10/16.
 * Construction of Top Report Pager Adapter
 */
public class ReportPagerAdapter  extends FragmentStatePagerAdapter{



    String overall;
    String mSubjectName  = "";
    String mSubjectId = "";
    //    List<T> LessoList  =
    String[] tenthtitles = {"MATHS","SCIENCE","SOCIAL"};
    String[] computerTitles  = {"PHYSICS","CHEMISTRY","MATHS","COMPUTER"};
    String[] bioTitles = {"PHYSICS","CHEMISTRY","MATHS","BIOLOGY"};


    public ReportPagerAdapter(FragmentManager fm, int i) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0 :
                return "MATHS";
            case 1:
                return "SCIENCE";
            case 2:
                return "SOCIAL";
        }
        return null;
    }

    @Override
    public Fragment getItem(int position) {

        ReportFragment fragment = new ReportFragment();

        switch (position){
            //based on subjects , set Arguments to Fragemnt such as
            //overall percentage , List of Lessons etc
            case 0: mSubjectId = "0";
                mSubjectName = "MATHS";
                break;
            case 1: mSubjectId = "1";
                mSubjectName = "SCIENCE";
                break;
            case 2: mSubjectId = "2";
                mSubjectName = "SOCIAL";
                break;


        }
        Bundle b  = new Bundle();
        b.putString(BundleKey.REPORT_OVERALL,overall);
        fragment.setArguments(b);

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
