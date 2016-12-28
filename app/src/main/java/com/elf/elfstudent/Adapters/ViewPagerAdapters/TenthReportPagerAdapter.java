
        package com.elf.elfstudent.Adapters.ViewPagerAdapters;

        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentStatePagerAdapter;

        import com.elf.elfstudent.Fragments.ReportFragment;
        import com.elf.elfstudent.Utils.BundleKey;

/**
 * Created by nandhu on 26/10/16.
 *
 */
        public class TenthReportPagerAdapter extends FragmentStatePagerAdapter {



            String overall;
            String mSubjectName  = "";
            String mSubjectId = "";
            //    List<T> LessoList  =



            public TenthReportPagerAdapter(FragmentManager fm) {
                super(fm);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position){
                    case 0 :
                        return "SCIENCE";
                    case 1:
                        return "SOCIAL";
                    case 2:
                        return "MATHS";



                }
                return null;
            }

            @Override
            public Fragment getItem(int position) {

                ReportFragment fragment = new ReportFragment();

                switch (position){
                    //based on subjects , set Arguments to Fragemnt such as
                    //overall percentage , List of Lessons etc

                    //todo add subject iD heres
                    case 0: mSubjectId = "2";
                        mSubjectName = "SCIENCE";
                        break;
                    case 1 :
                        mSubjectId = "3";
                        mSubjectName = "SOCIAL";
                        break;
                    case 2:
                        mSubjectId = "1";
                        mSubjectName = "MATHS";
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
        return 3;
    }
}