package com.elf.elfstudent.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.BundleKey;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 19/10/16.
 *
 */

public class SubjectViewActivity extends AppCompatActivity {


    @BindView(R.id.subject_view_root)
    CardView mRoot;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject_view_activity);

        ButterKnife.bind(this);
        if (getIntent() != null){
            String transName = getIntent().getStringExtra(BundleKey.ROOT_VIEW_TRANS_NAME);
            ViewCompat.setTransitionName(mRoot,transName);
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
}
