package com.elf.elfstudent.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.BundleKey;

import butterknife.ButterKnife;

/**
 * Created by nandhu on 18/10/16.
 * The Page which Show Test Completed
 */

public class TestCompletedActivity extends AppCompatActivity {



    String testId = null;
    String subjectId = null;
    String testDesc = null;

    String studentId = null;


    DataStore mStore =  null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_completed);
        ButterKnife.bind(this);


        mStore = DataStore.getStorageInstance(getApplicationContext());

        if (mStore == null){
            studentId = mStore.getStudentId();
        }

        //get The Test Variables from intent
        if (getIntent() != null){
            testDesc = getIntent().getStringExtra(BundleKey.TEST_ID);
            subjectId = getIntent().getStringExtra(BundleKey.SUBJECT_ID);
            testId = getIntent().getStringExtra(BundleKey.TEST_DESC);
        }



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
