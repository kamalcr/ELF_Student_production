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

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.EmailHandler;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.RequestParameterKey;
import com.elf.elfstudent.Utils.StringValidator;

import org.json.JSONObject;

import butterknife.BindView;

/**
 * Created by nandhu on 20/10/16.
 * the Page which is first shown to user on Register Clicking
 */

public class EmailActivityPage extends AppCompatActivity implements ErrorHandler.ErrorHandlerCallbacks, EmailHandler.EmaiCallbacks {


    private static final String TAG = "EmaIl PAge";
    private static final String EMAIL_URL = "";
    @BindView(R.id.email_text)
    TextInputEditText mEmailBox;

    @BindView(R.id.email_next_button)
    Button mNextButton;

    DataStore mStore = null;
    EmailHandler emailHandler = null;
    ErrorHandler errorHandler = null;
    AppRequestQueue mRequestQueue = null;

    String email = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_activity);





        mRequestQueue = AppRequestQueue.getInstance(getApplicationContext());

        mStore = DataStore.getStorageInstance(getApplicationContext());

        errorHandler = new ErrorHandler(this);

        emailHandler = new EmailHandler(this);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckEmail();
            }
        });
    }

    private void CheckEmail() {
      email  = mEmailBox.getText().toString();
        if (StringValidator.checkeEMail(email)){
                //Correct Email format
            checkifEmailExists(email);
        }
        else{
            //wrong Email Format
            // TODO: 20/10/16 show Shake Animation
        }
    }

    private void checkifEmailExists(String email) {

        email = email.trim();
        JSONObject mObj = new JSONObject();
        try {
            mObj.put(RequestParameterKey.EMAIL_ID,email);

        }
        catch (Exception e ){
            Log.d(TAG, "checkifEmailExists: ");
        }

        JsonObjectRequest mRequest = new JsonObjectRequest(Request.Method.POST,EMAIL_URL,mObj,emailHandler,errorHandler);

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
    public void ShowPersonalInfoPage() {
        mStore.setEmailId(email);
        final Intent i = new Intent(this,RegisterActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    @Override
    public void emailAlreadyExists() {
        //// TODO: 20/10/16 email Already Existe show toast
    }
}
