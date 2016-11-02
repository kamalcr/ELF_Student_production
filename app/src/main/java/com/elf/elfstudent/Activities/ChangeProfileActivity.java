package com.elf.elfstudent.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.elf.elfstudent.CustomUI.HelviticaLight;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 18/10/16.
 *
 * The Page WHich lets user//studnet Change Profile
 */

public class ChangeProfileActivity extends AppCompatActivity {


    private static final String TAG = "ELF";
    //Elf id
    @BindView(R.id.elf_id)
    HelviticaLight mElfId;


    //Name

    @BindView(R.id.change_profile_name_value) HelviticaLight mNamebox;


    //Email

    @BindView(R.id.chg_profile_email_value)
    TextView mEmailBox;

    //Phone Number

    @BindView(R.id.chg_profile_phone_value) TextView mPhoneNumber;


    //Standard
    @BindView(R.id.chg_profile_std_value) HelviticaLight mStandard;

    @BindView(R.id.chg_profile_school_value) HelviticaLight mSchoolName;


    @BindView(R.id.change_profile_toolbar)
    Toolbar mToolbar;

    DataStore mStore = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_profile);
        ButterKnife.bind(this);
        mStore = DataStore.getStorageInstance(getApplicationContext());
        setValuestoViews();

        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowHomeEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);

        mNamebox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NameChange();
            }
        });

        mEmailBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmailChange();
            }
        });

        mPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneNUmberChange();
            }
        });
    }

    private void setValuestoViews() {
        mElfId.setText(mStore.getStudentId());
        mNamebox.setText(mStore.getUserName());
        mSchoolName.setText(mStore.getInstituionName());
        mStandard.setText(mStore.getStandard());
        mEmailBox.setText(mStore.getEmailId());
        mPhoneNumber.setText(mStore.getPhoneNumber());

    }

    private void PhoneNUmberChange() {

    }

    private void EmailChange() {

    }

    private void NameChange() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                final Intent i = new Intent(this,HomeActivity.class);
                startActivity(i);
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
