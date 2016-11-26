package com.elf.elfstudent.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.JsonProcessors.ChangePasswordHandler;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.RequestParameterKey;
import com.elf.elfstudent.Utils.StringValidator;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 20/10/16.
 *
 * THe class that is used to change Password
 * specified by //todo find what to send
 */
public class ChangePasswordActivity  extends AppCompatActivity implements ErrorHandler.ErrorHandlerCallbacks, ChangePasswordHandler.Callbacks {

    private static final String TAG = "ELF";

    // todo Change Passwod UEl here

    private static final String FORGOT_URL = "";
    @BindView(R.id.enter_password)
    TextInputEditText mPasswordBox;
    @BindView(R.id.re_enter_password) TextInputEditText mReEnterPaswordBox;

    @BindView(R.id.save_button)
    Button mSaveNewPasswordButton;


    ChangePasswordHandler passwordHandler = null;
    ErrorHandler errorHandler = null;
    DataStore mStore = null;
    AppRequestQueue mRequestQueue = null;
    private String  identifier;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_chane_activity);

        ButterKnife.bind(this);
        mStore = DataStore.getStorageInstance(getApplicationContext());
        mRequestQueue = AppRequestQueue.getInstance(getApplicationContext());
        errorHandler = new ErrorHandler(this);
        passwordHandler = new ChangePasswordHandler(this);

        mSaveNewPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePassword();
            }
        });
    }

    private void savePassword() {
        String pass = mPasswordBox.getText().toString();
        String mrepass  = mReEnterPaswordBox.getText().toString();
        if (pass.equals(mrepass) ){
            //same password
            if (StringValidator.checkPassword(pass)){
                //correct password lentgh blah bla
                saveNewPassword(pass,identifier);
            }
            else{
              Toast.makeText(this,"Enter more than 6 Characters",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            //Wrong password combo
            Toast.makeText(this,"Enter Same Password",Toast.LENGTH_SHORT).show();
            mPasswordBox.setText("");
            mReEnterPaswordBox.setText("");
        }
    }

    private void saveNewPassword(String pass, String identifier) {
        JSONObject  mObject = new JSONObject();
        try {
            //
            // todo mObject.put(RequestParameterKey.LOGIN_USER_NAME,identifier);
            mObject.put(RequestParameterKey.PASSWORD,pass);
        }
        catch (Exception  e ){
            Log.d(TAG, "saveNewPassword: ");
        }

        JsonObjectRequest mRequest = new JsonObjectRequest(Request.Method.POST,FORGOT_URL,mObject,passwordHandler,errorHandler);
        if (mRequestQueue   != null) {
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
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
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
    public void passWordChanged(String newPassword) {
        //show login Activity
        Toast.makeText(this,"Password Changed Successfully",Toast.LENGTH_SHORT).show();
        final Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
    }

    @Override
    public void passwordNotChanged() {

        //Some Error Occured
        //Repeat the Process

        Toast.makeText(this,"Please Try again",Toast.LENGTH_SHORT).show();
        mPasswordBox.setText("");
        mReEnterPaswordBox.setText("");
    }
}
