package com.elf.elfstudent.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.RequestParameterKey;
import com.google.firebase.crash.FirebaseCrash;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 18/10/16.
 * The Login Activity
 */

public class LoginActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONArray> {


    private static final String TAG = "ELF";
    private static final String LOGIN_URL = "http://www.hijazboutique.com/elf_ws.svc/CheckStudentLogin";

    @BindView(R.id.login_button)
    Button mLoginButton;

    @BindView(R.id.login_email_box)
    EditText memailBox;
    @BindView(R.id.login_password_box)
    EditText mPasswordBox;


    @BindView(R.id.textView31)
    TextView mForgotPassword;


    private AppRequestQueue mRequestQueue;
    private DataStore mStore = null;

    ProgressDialog mDialog   = null;


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

        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                forgotPAssword();
            }
        });

    }

    private void forgotPAssword() {
        final Intent i   = new Intent(this,ForgotPassword.class);
        startActivity(i);
    }

    private void loginButtonClicked() {
        mDialog = new ProgressDialog(getApplicationContext());
        mDialog.setIndeterminate(true);
        mDialog.setMessage("Logging In. Please Wait");
        String userName = memailBox.getText().toString();
        String password = mPasswordBox.getText().toString();


        JSONObject mObject = new JSONObject();
        try {

//            // TODO: 10/11/16 User name and Password keyword
            mObject.put("username", userName);
            mObject.put("password", password);
        } catch (Exception e) {
            Log.d(TAG, "loginButtonClicked: ");
        }

        JsonArrayRequest mRequest = new JsonArrayRequest(Request.Method.POST, LOGIN_URL, mObject, this, this);
        mRequestQueue.addToRequestQue(mRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        stopDialog();
    }


    private void stopDialog(){
        if (mDialog.isShowing()){
            mDialog.dismiss();
        }
    }

    @Override
    public void onResponse(JSONArray response) {
        //parse the Response

        JSONObject mObject = null;

        String studentId = null;
        String StudentName = null;
        String classId = null;
        String insId = null;
        String insName = null;
        String groupId = null;


        try {
            mObject = response.getJSONObject(0);
            if (mObject != null) {

                String result = mObject.getString("StatusCode");
                if (result.equals("1000")) {
                    //validation went through , get Student details
                    studentId = mObject.getString("StudentId");
                    StudentName = mObject.getString("StudentName");
                    classId = mObject.getString("classId");
                    groupId = mObject.getString("GroupId");
                    insId = mObject.getString("institutionId");
                    insName = mObject.getString("InstitutionName");
                    String boardId = mObject.getString("BoardId");

                    if (studentId != null) {
                        //Save it file
                        mStore.setBoardId(boardId);
                        mStore.setStudentId(studentId);
                        mStore.setInstitutionId(insId);
                        mStore.setUserName(StudentName);
                        mStore.setStudentStandard(classId);
                        //
                        //
                        // TODO: 10/11/16 get group ID , set if in store according to it
                        mStore.setStudentPrefrerredSubject(groupId);
                        //                        mStore.setEmailId();

                        //set First to false , so as to not show it again
                        mStore.setIsFirstTime(false);

                        stopDialog();
                        //Show Home Activitu
                        final Intent i = new Intent(this, HomeActivity.class);

                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                }


            } else {
                //wrong show shake Animation
                //// TODO: 10/11/16
            }
        } catch (JSONException e) {
            e.printStackTrace();
            stopDialog();
            FirebaseCrash.log("Error in parsing LOgin Info");
        }

    }
}
