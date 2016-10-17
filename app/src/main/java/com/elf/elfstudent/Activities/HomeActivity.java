package com.elf.elfstudent.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.elf.elfstudent.CustomUI.HelviticaLight;
import com.elf.elfstudent.CustomUI.RoundedImageView;
import com.elf.elfstudent.CustomUI.SansRegularTextview;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.R;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 17/10/16.
 * The Home Acitivity
 *
 */
public class HomeActivity extends AppCompatActivity{


    DataStore mStore;

    //the Views OF this Activity


    //student Name
    @BindView(R.id.home_student_name)
    HelviticaLight mStudentName;

    //School Name
    @BindView(R.id.home_school_name) HelviticaLight mSchoolname;

    //standard Name
    @BindView(R.id.home_section_name) HelviticaLight mStandardName;

    //Profile picture
    @BindView(R.id.home_profile_imageview)
    RoundedImageView mProfilePicture;

    //state Rank Value

    @BindView(R.id.home_state_value)
    SansRegularTextview mStateRank;

    //Overall rank

    @BindView(R.id.home_overall_rank_value) SansRegularTextview mOverallValue;

    //District

    @BindView(R.id.home_dist_rank_value) SansRegularTextview mDistrictRankValue;



    //The Subject List

    @BindView(R.id.home_list)
    RecyclerView mSubjectList;

    //The Progress Bar
    @BindView(R.id.home_progress_bar)
    AVLoadingIndicatorView mProgressbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        ButterKnife.bind(this);

        //get The details for this User from Shared PRefs
        mStore  = DataStore.getStorageInstance(this.getApplicationContext());




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
