package com.elf.elfstudent.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.BundleKey;
import com.elf.elfstudent.Utils.ScreenUtil;
import com.elf.elfstudent.Utils.StringValidator;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 11/12/16.
 *
 */
public class RegisterActivity extends AppCompatActivity{
        private static final String TAG = "ELF";
        //password
        @BindView(R.id.nregister_pass)
        TextInputEditText mPassword;
        // phone Number
        @BindView(R.id.nregister_phone) TextInputEditText mPhoneBox;
        //Name
        @BindView(R.id.nregister_name) TextInputEditText mNameBox;

        // submit Button
        @BindView(R.id.nregister_submit)
        ImageView mSubmitButton;


        DataStore mStore = null;
        String mEmail = null;
        @BindView(R.id.name_imageview)
        ImageView nameImageview;
        @BindView(R.id.password_imageview)
        ImageView passwordImageview;
        @BindView(R.id.phone_imageview)
        ImageView phoneImageview;

        @BindView(R.id.profile_root)
        RelativeLayout mRootlayout;

//    @BindDrawable(R.drawable.register_back_image)

        @BindView(R.id.register_back_image_view) ImageView mBackImage;
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.registration_activity);
            ButterKnife.bind(this);
            loadBitmap();
            mStore = DataStore.getStorageInstance(getApplicationContext());

            if (getIntent() != null) {

                mEmail = getIntent().getStringExtra(BundleKey.ARG_EMAIL_ID_TAG);
            }


            mSubmitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    submitButtonClicked();
                }
            });
            try {
                nameImageview.setImageResource(R.drawable.name_ih);
                passwordImageview.setImageResource(R.drawable.password_ih);
                phoneImageview.setImageResource(R.drawable.mobile_ih);
//            mBackImage.setImageResource(R.drawable.register_back_image);

            }
            catch (Exception e ){
                Log.d(TAG, "Exception "+e.getLocalizedMessage());
            }
            //set ToolbarRe
        }

        public void loadBitmap() {
            Picasso.with(this)
                    .load(R.drawable.register_back_image)
                    .resize(ScreenUtil.getScreenWidth(this),ScreenUtil.getScreenHeight(this))
                    .into(mBackImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "onSuccess: ");
                            runEnterAnimations();
                        }

                        @Override
                        public void onError() {

                        }
                    });
        }

        private void runEnterAnimations() {


            mNameBox.setScaleX(0);
            mPassword.setScaleX(0);
            mPhoneBox.setScaleX(0);
            startScaleAnimation(mNameBox,1);
            startScaleAnimation(mPassword,2);
            startScaleAnimation(mPhoneBox,3);
        }

        private void startScaleAnimation(View view, int i) {
            view.animate().scaleX(1f).setDuration(400)
                    .setStartDelay(i*400)
                    .setInterpolator(new DecelerateInterpolator(3f))
                    .start();

        }

        private void submitButtonClicked() {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            String Name = null;
            String Password = null;
            String phone = null;
            String Email = null;
            if (mEmail != null) {
                Email = mEmail;
            }
            try {
                Name = mNameBox.getText().toString();
                Name = Name.trim();
                Password = mPassword.getText().toString();
                phone = mPhoneBox.getText().toString();
            } catch (Exception e) {
                throw new NullPointerException("NULL");
            }


            //validating UserName and Password and mobile Number
            if (StringValidator.checkPassword(Password)) {
                //pass word is valid , check for userName


                if (StringValidator.CheckUserName(Name)) {
                    // user name is valid ,check for Phone Number

                    if (StringValidator.checkPhoneNumber(phone)) {
                        // pHone Number is Also Correct
                        // Show Institute Page
                        Log.d(TAG, "submitButtonClicked: ");
                        //save Variables to storage(Shared prefs)
                        if (mStore != null) {

                            mStore.setUserName(Name);
                            mStore.setPhoneNumberTag(phone);
                            mStore.setPassword(Password);

                        }

                        Log.d(TAG, "sending Board Page");
                        Intent i = new Intent(this, InstitutePage.class);
                        i.putExtra(BundleKey.ARG_USER_NAME_TAG, Name);
                        i.putExtra(BundleKey.ARG_PHONE_NUMBER_TAG, phone);
                        i.putExtra(BundleKey.ARG_PASWORD, Password);
                        startActivity(i);

                    } else {
                        //phone number is not valid
                        Toast.makeText(this, "Please Enter Phone Number", Toast.LENGTH_SHORT).show();
                        mPhoneBox.startAnimation(shake);
                        mNameBox.setText("");
                    }
                } else {
                    // Name is not valid
                    Toast.makeText(this, "Please Enter Valid Name", Toast.LENGTH_SHORT).show();
                    mNameBox.startAnimation(shake);
                    mNameBox.setText("");
                }

            } else {
                //password is wrong
                Toast.makeText(this, "Please Enter Valid Password", Toast.LENGTH_SHORT).show();
                mPassword.startAnimation(shake);
                mPassword.setText("");
            }


        }

        @Override
        protected void onStart() {
            super.onStart();

            //IF came back from Previous Activity Store varaibles

            //set name
            try {

                String name = mStore.getUserName();
                if (name != null) {
                    //
                    mNameBox.setText(name);
                }

                //set Phone Number
                String phone = mStore.getPhoneNumber();
                if (phone != null) {
                    mPhoneBox.setText(phone);
                }

                //set Password
                String pass = mStore.getPassWord();
                if (pass != null) {
                    mPassword.setText(pass);
                }
            } catch (Exception e) {
                Log.d(TAG, "onStart: Exception");
            }
        }



}