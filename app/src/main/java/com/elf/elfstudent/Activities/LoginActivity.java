package com.elf.elfstudent.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.RequestParameterKey;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 18/10/16.
 * The Login Activity
 */

public class LoginActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {


    private static final String TAG = "ELF";
    private static final String LOGIN_URL = "http://www.hijazboutique.com/elf_ws.svc/CheckStudentLogin";

    @BindView(R.id.login_button)
    Button mLoginButton;

    @BindView(R.id.login_email_box)
    EditText memailBox;
    @BindView(R.id.login_password_box) EditText mPasswordBox;


    private AppRequestQueue mRequestQueue;
    private DataStore mStore = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        ButterKnife.bind(this);



        //intitlalize Req Que
        mRequestQueue = AppRequestQueue.getInstance(getApplicationContext());

        mStore = DataStore.getStorageInstance(getApplicationContext());







        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButtonClicked();
            }
        });




    }

    private void loginButtonClicked() {
        String userName = memailBox.getText().toString();
        String password = mPasswordBox.getText().toString();


        JSONObject mObject = new JSONObject();
        try{
            mObject.put("username",userName);
            mObject.put("password",password);
        }
        catch (Exception e ){
            Log.d(TAG, "loginButtonClicked: ");
        }

        JsonObjectRequest mRequest = new JsonObjectRequest(Request.Method.POST,LOGIN_URL,mObject,this,this);
        mRequestQueue.addToRequestQue(mRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        //parse the Response


        String result = "";
        if (result.equals("1000")){
            Intent i = new Intent(this,HomeActivity.class);
//            mStore.setBoardId("");
//            mStore.setStudentId();
//            mStore.setInstitutionId();
//            mStore.setUserName();
//            mStore.setEmailId();
//            mStore.setIsFirstTime(false);

            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        }
    }
}
