package com.elf.elfstudent.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.Network.JsonProcessors.ForgotPasswordHandler;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.BundleKey;
import com.elf.elfstudent.Utils.RequestParameterKey;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 20/10/16.
 */

public class ForgotPassword extends AppCompatActivity implements ErrorHandler.ErrorHandlerCallbacks, ForgotPasswordHandler.ForgotPasswordCallbacks {

    private static final String TAG = "ELF";
    private static final String CHECK_URL = "http://www.hijazboutique.com/elf_ws.svc/CheckUserDetails";
    @BindView(R.id.check_button)
    Button mForgotButton;

    @BindView(R.id.forgot_phone_ted)
    TextInputLayout mPhoneBox;

    @BindView(R.id.fp_email_ted) TextInputLayout mEmailBox;



    ErrorHandler errorHandler = null;
    ForgotPasswordHandler mForgotHander = null;
    AppRequestQueue mRequestQueue ;

    String email = null;
    String phoneNumber = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forogot_password);
        ButterKnife.bind(this);




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
       email  = mEmailBox.getEditText().getText().toString();
        phoneNumber = mPhoneBox.getEditText().getText().toString();

        JSONObject mObj = new JSONObject();
        try{
//
            mObj.put(RequestParameterKey.EMAIL_ID,email);
            mObj.put(RequestParameterKey.PHONE,phoneNumber);

        }
        catch (Exception e ){
            Log.d(TAG, "forogotButtonClicked: ");
        }

        JsonObjectRequest mRequest  = new JsonObjectRequest(Request.Method.POST,CHECK_URL,mObj,mForgotHander,errorHandler);
        if (mRequestQueue != null){

            mRequestQueue.addToRequestQue(mRequest);
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

    @Override
    public void TimeoutError() {

    }

    @Override
    public void NetworkError() {

    }

    @Override
    public void ServerError() {

    }

    /*
    *
    * */

    @Override
    public void ShowPassword(String password) {


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



    }
}
