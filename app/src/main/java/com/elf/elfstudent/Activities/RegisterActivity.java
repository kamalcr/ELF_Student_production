package com.elf.elfstudent.Activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.elf.elfstudent.CustomUI.HelviticaMedium;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.BundleKey;
import com.elf.elfstudent.Utils.StringValidator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 17/10/16.
 * THe Acitivyt which HAndles Registration
 *
 */
public class RegisterActivity  extends AppCompatActivity{


    private static final String TAG = "PersonalPage";
    //password
    @BindView(R.id.nregister_pass)
    TextInputLayout mPassword;
    // phone Number
    @BindView(R.id.nregister_phone) TextInputLayout mPhoneBox;
    //Name
    @BindView(R.id.nregister_name) TextInputLayout mNameBox;

    // submit Button
    @BindView(R.id.nregister_submit)
    Button mSubmitButton;

    //Set Text
    @BindView(R.id.nregister_settext)
    HelviticaMedium mSetText;



    String mEmail = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        ButterKnife.bind(this);

        if (getIntent()!= null){
            mEmail  = getIntent().getStringExtra(BundleKey.ARG_EMAIL_ID_TAG);
        }
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitButtonClicked();
            }
        });


    }

    private void submitButtonClicked() {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        String Name  =null;
        String Password = null;
        String phone = null;
        String Email = null;
        if (mEmail != null){
            Email = mEmail;
        }
        try {
            Name  = mNameBox.getEditText().getText().toString();
            Password = mPassword.getEditText().getText().toString();
            phone = mPhoneBox.getEditText().getText().toString();
        }
        catch (Exception e){
            throw new NullPointerException("NULL");
        }


        //validating UserName and Password and mobile Number
        if (StringValidator.checkPassword(Password)){
            //pass word is valid , check for userName


            if (StringValidator.CheckUserName(Name)){
                // user name is valid ,check for Phone Number

                if (StringValidator.checkPhoneNumber(phone)){
                     // pHone Number is Also Correct
                     // Show Institute Page
                    Log.d(TAG, "submitButtonClicked: ");
                    Intent  i = new Intent(this,BoardPage.class);
                    i.putExtra(BundleKey.ARG_USER_NAME_TAG,Name);
                    i.putExtra(BundleKey.ARG_PHONE_NUMBER_TAG,phone);
                    i.putExtra(BundleKey.ARG_PASWORD,Password);
                    startActivity(i);

                }else{
                    //phone number is not valid
                    Toast.makeText(this,"Please Enter Phone Number",Toast.LENGTH_SHORT).show();
                    mPhoneBox.startAnimation(shake);
                    mNameBox.getEditText().setText("");
                }
            }else{
                // Name is not valid
                Toast.makeText(this,"Please Enter Valid Name",Toast.LENGTH_SHORT).show();
                mNameBox.startAnimation(shake);
                mNameBox.getEditText().setText("");
            }

        }else{
            //password is wrong
            Toast.makeText(this,"Please Enter Valid Password",Toast.LENGTH_SHORT).show();
            mPassword.startAnimation(shake);
            mPassword.getEditText().setText("");
        }






    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }
}
