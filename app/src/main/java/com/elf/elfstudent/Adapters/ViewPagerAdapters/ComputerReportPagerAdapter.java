package com.elf.elfstudent.Adapters.ViewPagerAdapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.elf.elfstudent.Fragments.ReportFragment;
import com.elf.elfstudent.Utils.BundleKey;
import com.elf.elfstudent.Utils.SubjectImage;

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
                return "PHYSICS";
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
            case 0: mSubjectId = SubjectImage.PHY_ID;
                mSubjectName = "PHYSICS";
                break;
            case 1 :
                mSubjectId = SubjectImage.CHEM_ID;
                mSubjectName = "CHEMISTRY";
                break;
            case 2:
                mSubjectId =SubjectImage.MATHS_12;
                mSubjectName = "MATHS";
                break;
            case 3:
                mSubjectId = SubjectImage.COMP_ID;
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
