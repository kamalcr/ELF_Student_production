package com.elf.elfstudent.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.elf.elfstudent.CustomUI.HelviticaLight;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.StringValidator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by nandhu on 4/12/16.
 *
 */
public class PasswordActivity extends AppCompatActivity {

    @BindView(R.id.pass_ph_text)
    HelviticaLight passPhText;
    @BindView(R.id.phone_ed)
    EditText phoneEd;
    @BindView(R.id.pass_phone_icon)
    ImageView passPhoneIcon;
    @BindView(R.id.save_num_button)
    Button saveNumButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pass_act);
        ButterKnife.bind(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Intent i  = new Intent(this,EmailActivityPage.class);
        startActivity(i);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @OnClick(R.id.save_num_button)
    public void onClick() {
        saveNUmberButtonClicked();
    }

    private void saveNUmberButtonClicked() {
        String phoneNumber  = phoneEd.getText().toString();
        if(StringValidator.checkPhoneNumber(phoneNumber)){
            //Correct Phone
            DataStore mStore   = DataStore.getStorageInstance(this);
            if (mStore != null){
                mStore.setPhoneNumberTag(phoneNumber);
                Intent i = new Intent(this,InstitutePage.class);
                startActivity(i);
            }
            else{
                throw  new NullPointerException("Store Not Initalised");
            }

        }
        else{
            //Worng Format
            Animation an = AnimationUtils.loadAnimation(this,R.anim.shake);
            phoneEd.setText("");
            phoneEd.startAnimation(an);
        }

    }

}
