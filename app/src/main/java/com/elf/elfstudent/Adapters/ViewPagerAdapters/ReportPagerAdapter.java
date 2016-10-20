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
    String SubjectName = "";
//    List<T> LessoList  =

    public ReportPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        ReportFragment fragment = null;

        switch (position){
          //based on subjects , set Arguments to Fragemnt such as
            //overall percentage , List of Lessons etc
            case 0:
                overall = "";


        }


        Bundle b  = new Bundle();
        b.putString(BundleKey.REPORT_OVERALL,overall);
        fragment.setArguments(b);

        return fragment;











    }

    @Override
    public int getCount() {
        return 0;
    }
}