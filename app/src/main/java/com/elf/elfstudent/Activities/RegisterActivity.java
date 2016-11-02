package com.elf.elfstudent.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.VectorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.R;
import com.elf.elfstudent.SplashActivity;
import com.elf.elfstudent.Utils.BundleKey;
import com.elf.elfstudent.Utils.ScreenUtil;
import com.elf.elfstudent.Utils.StringValidator;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 17/10/16.
 * THe Acitivyt which HAndles Registration
 */
public class RegisterActivity extends AppCompatActivity {


    private static final String TAG = "ELF";
    //password
    @BindView(R.id.nregister_pass)
    TextInputEditText mPassword;
    // phone Number
    @BindView(R.id.nregister_phone)
    TextInputEditText mPhoneBox;
    //Name
    @BindView(R.id.nregister_name)
    TextInputEditText mNameBox;

    // submit Button
    @BindView(R.id.nregister_submit)
    ImageButton mSubmitButton;


    DataStore mStore = null;
    String mEmail = null;
    @BindView(R.id.name_imageview)
    ImageView nameImageview;
    @BindView(R.id.password_imageview)
    ImageView passwordImageview;
    @BindView(R.id.phone_imageview)
    ImageView phoneImageview;

    @BindView(R.id.register_back_image) ImageView mBackImage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        ButterKnife.bind(this);

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
            loadBitmap(R.drawable.register_back_image,mBackImage);
            mSubmitButton.setImageResource(R.drawable.enter_icon);
        }
        catch (Exception e ){
            Log.d(TAG, "Exception "+e.getLocalizedMessage());
        }
        //set Toolbar


    }

    public void loadBitmap(int resId, ImageView imageView) {
       MyBitmapWorkerTask task = new MyBitmapWorkerTask(imageView);
        task.execute(resId);
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

    class MyBitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private int data = 0;

        public MyBitmapWorkerTask(ImageView imageView) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(Integer... params) {
            data = params[0];
            return BitmapFactory.decodeResource(getResources(),data);
        }

        private Bitmap decodeSampledBitmapFromResource(Resources resources, int data, int i, int i1) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = true;
//            BitmapFactory.decodeResource(resources, data, options);
//
            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, i, i1);

            // Decode bitmap with inSampleSize set
//            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeResource(resources, data, options);
        }

        private int calculateInSampleSize(BitmapFactory.Options options, int i, int i1) {
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;

            if (height > i1 || width > i) {

                final int halfHeight = height / 2;
                final int halfWidth = width / 2;

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while ((halfHeight / inSampleSize) >= i1
                        && (halfWidth / inSampleSize) >= i) {
                    inSampleSize *= 2;
                }
            }

            return inSampleSize;
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

}
