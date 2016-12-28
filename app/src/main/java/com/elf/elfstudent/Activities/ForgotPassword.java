package com.elf.elfstudent.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.Network.JsonProcessors.ForgotPasswordHandler;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.BundleKey;
import com.elf.elfstudent.Utils.RequestParameterKey;
import com.elf.elfstudent.Utils.StringValidator;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 20/10/16.
 *
 */

public class ForgotPassword extends AppCompatActivity implements ErrorHandler.ErrorHandlerCallbacks, ForgotPasswordHandler.ForgotPasswordCallbacks {

    private static final String TAG = "ELF";
    private static final String CHECK_URL = "http://www.elfanalysis.net/elf_ws.svc/CheckUserDetails";
    @BindView(R.id.check_button)
    Button mForgotButton;

    @BindView(R.id.forgot_email_ted)
    TextInputLayout mEmailBox;

    @BindView(R.id.fp_phone_ted) TextInputLayout mPhoneBox;



    ErrorHandler errorHandler = null;
    ForgotPasswordHandler mForgotHander = null;
    AppRequestQueue mRequestQueue ;

    String email = null;
    String phoneNumber = null;
    ProgressDialog mDialog = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forogot_password);
        ButterKnife.bind(this);

        mDialog = new ProgressDialog(this);
        mDialog.setIndeterminate(true);
        mDialog.setMessage("Just a moment");




        mRequestQueue = AppRequestQueue.getInstance(getApplicationContext());
        errorHandler  = new ErrorHandler(this);
        mForgotHander = new ForgotPasswordHandler(this);
        //forgotButton
        mForgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forogotButtonClicked();
            }


        });
    }

    private void forogotButtonClicked() {
        if (!mDialog.isShowing()){
            mDialog.show();
        }
       email  = mEmailBox.getEditText().getText().toString();
        phoneNumber = mPhoneBox.getEditText().getText().toString();
        if (StringValidator.checkeEMail(email)) {
            //Right Email, check for Phone number
            if (StringValidator.checkPhoneNumber(phoneNumber)){
                //Correct Format , send Request
                JSONObject mObj = new JSONObject();
                try{
//
                    mObj.put(RequestParameterKey.EMAIL_ID,email);
                    mObj.put(RequestParameterKey.PHONE,phoneNumber);

                }
                catch (Exception e ){
                    Log.d(TAG, "forogotButtonClicked: ");
                }

                JsonArrayRequest mRequest  = new JsonArrayRequest(Request.Method.POST,CHECK_URL,mObj,mForgotHander,errorHandler);
                if (mRequestQueue != null){

                    mRequestQueue.addToRequestQue(mRequest);
                }
            }
            else{
                //Wrong PHone Number
                stopDialog();
                Log.d(TAG, "forogotButtonClicked: worng phone");
                Animation an  = AnimationUtils.loadAnimation(this,R.anim.shake);

                mPhoneBox.startAnimation(an);


            }

        }
        else{
            //wrong Email Format
            stopDialog();
            Log.d(TAG, "forogotButtonClicked: wrong email");
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.shake);
            mEmailBox.startAnimation(animation);
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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }



    private void stopDialog(){
        if (mDialog.isShowing()){
            mDialog.dismiss();
        }
    }
    @Override
    public void TimeoutError() {

        stopDialog();
    }

    @Override
    public void NetworkError() {
        stopDialog();
    }

    @Override
    public void ServerError() {
        stopDialog();
    }

    /*
    *
    * */

    @Override
    public void ShowPassword(String password) {


        stopDialog();


        //Entered Credentails is Correct
        //show Change Password Page
        final Intent i = new Intent(this,ChangePasswordActivity.class);
        if (email != null || phoneNumber != null){

            i.putExtra(BundleKey.ARG_EMAIL_ID_TAG,email);
            i.putExtra(BundleKey.ARG_PHONE_NUMBER_TAG,phoneNumber);
            startActivity(i);
        }
    }

    @Override
    public void WrongDetailsEntered() {
        stopDialog();
        Animation an = AnimationUtils.loadAnimation(this,R.anim.shake);
        mEmailBox.getEditText().setText("");
        mPhoneBox.getEditText().setText("");
        mEmailBox.startAnimation(an);
        mPhoneBox.startAnimation(an);
        Toast.makeText(this,"Password and Phone Number Do not match",Toast.LENGTH_SHORT).show();


    }
}
