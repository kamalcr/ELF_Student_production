package com.elf.elfstudent.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elf.elfstudent.CustomUI.HelviticaLight;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.R;
import com.google.firebase.crash.FirebaseCrash;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.bitmap;

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

    @BindView(R.id.chn_profile_image)
    ImageView mProfilePicture;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_profile);
        ButterKnife.bind(this);
        mStore = DataStore.getStorageInstance(getApplicationContext());
        setValuestoViews();
        mToolbar.setTitle("Profile");
        setSupportActionBar(mToolbar);

        try {
            ActionBar ab = getSupportActionBar();
            ab.setTitle("Profile");
            ab.setDisplayShowHomeEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);

        }
        catch (Exception e){
            FirebaseCrash.log("Excception in setting Toolbar");
        }


        String picturpath = mStore.getpicturePath();
        Log.d(TAG, "setViewValues: picture path "+picturpath);
        if (picturpath.equals("null")){
            //NO Picture path , set Dfault Image
            Log.d(TAG, "setViewValues: default picture");
            Picasso.with(this).load(R.drawable.ic_account_circle_white_36dp).into(mProfilePicture);
        }
        else{
            //Some pIcture path is available
            Log.d(TAG, "setViewValues: somepicture");
            Uri pic = Uri.parse(mStore.getpicturePath());
            Picasso.with(this).load(pic)
                    .resize(100,100)
                    .centerCrop()

                    .into(mProfilePicture);

        }
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
        mProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }


        });
    }

    private void pickImage() {
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            Log.d(TAG, "onActivityResult: Picture path "+picturePath);

            if (mStore != null){
                mStore.setProPicturePath(selectedImage.toString());
            }
            else{
                Toast.makeText(this,"Picture not Saved",Toast.LENGTH_LONG).show();
            }
            cursor.close();
            Picasso.with(this).load(selectedImage).into(mProfilePicture);
        }
    }

    private void setValuestoViews() {

        if (mStore != null){

            mElfId.setText(mStore.getStudentId());
            mNamebox.setText(mStore.getUserName());
            mSchoolName.setText(mStore.getInstituionName());
            mStandard.setText(String.format("%s Standard", mStore.getStandard()));
            mEmailBox.setText(mStore.getEmailId());
            mPhoneNumber.setText(mStore.getPhoneNumber());
        }
       else{
            throw new NullPointerException("store cannot be null");
        }

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
