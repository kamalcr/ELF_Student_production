package com.elf.elfstudent.Activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.Network.ForgotPasswordHandler;
import com.elf.elfstudent.R;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 20/10/16.
 */

public class ForgotPassword extends AppCompatActivity implements ErrorHandler.ErrorHandlerCallbacks, ForgotPasswordHandler.ForgotPasswordCallbacks {

    private static final String TAG = "FORGOT";
    @BindView(R.id.forgot_button)
    Button mForgotButton;



    ErrorHandler errorHandler = null;
    ForgotPasswordHandler mForgotHander = null;
    AppRequestQueue mRequestQueue ;
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
        String email  = "";
        String phone = "";

        JSONObject mObj = new JSONObject();
        try{
//            mObj.put()
        }
        catch (Exception e ){
            Log.d(TAG, "forogotButtonClicked: ");
        }

        JsonObjectRequest mRequest  = new JsonObjectRequest(Request.Method.POST,FORGOT_URL,mObj,mForgotHander,errorHandler);
        mRequestQueue.addToRequestQue(mRequest);


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

    @Override
    public void ShowPassword(String password) {

    }

    @Override
    public void WrongDetailsEntered() {



    }
}
