package com.elf.elfstudent.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.elf.elfstudent.CustomUI.HelviticaMedium;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
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

public class LoginActivity extends AppCompatActivity implements
        Response.ErrorListener,
        Response.Listener<JSONArray>, GoogleApiClient.OnConnectionFailedListener {


    private static final String TAG = "ELF";
    private static final String LOGIN_URL = "http://elfanalysis.net/elf_ws.svc/CheckStudentLogin";
    @BindView(R.id.textView23)
    HelviticaMedium textView23;
    @BindView(R.id.g_login)
    SignInButton gLogin;


    private AppRequestQueue mRequestQueue;
    private DataStore mStore = null;

    ProgressDialog mDialog = null;
    String userName = null;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 1001;
    private JsonArrayRequest mLoginRequest;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        ButterKnife.bind(this);

        mDialog = new ProgressDialog(this);
        mDialog.setIndeterminate(true);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setMessage("Logging In. Please Wait");


        //intitlalize Req Que
        mRequestQueue = AppRequestQueue.getInstance(this.getApplicationContext());

        mStore = DataStore.getStorageInstance(getApplicationContext());
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // [END configure_signin]

        // [START build_client]
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        gLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButtonClicked();
            }
        });


    }

    private void forgotPAssword() {
        final Intent i = new Intent(this, ForgotPassword.class);
        startActivity(i);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }

    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            JSONObject object = new JSONObject();
            if (acct == null){
                Log.d(TAG, "handleSignInResult: account null");
            }
            try {
                object.put("username",acct.getEmail());
                object.put("password",acct.getId());
                Log.d(TAG, "handleSignInResult: mobject "+object.toString());
            }

            catch (Exception e ){
                Log.d(TAG, "handleSignInResult: Excepion in Sigin in");
            }

            mLoginRequest = new JsonArrayRequest(Request.Method.POST,LOGIN_URL,object,this,this);

            if (mRequestQueue != null){
                mRequestQueue.addToRequestQue(mLoginRequest);
            }




        } else {
            // Signed out, show unauthenticated UI.
            Log.d(TAG, "handleSignInResult: not Signed iD");
            Log.d(TAG, "handleSignInResult: "+result.getStatus());
        }
    }

    private void loginButtonClicked() {

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onErrorResponse(VolleyError error) {


        stopDialog();
        Toast.makeText(this, "Please turn on Data Services", Toast.LENGTH_LONG).show();
    }


    private void stopDialog() {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onResponse(JSONArray response) {
        //parse the Response


        Log.d(TAG, "onResponse: " + response.toString());
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
                        mStore.setStudentId(studentId);
                        mStore.setBoardId(boardId);
                        mStore.setStudentId(studentId);
                        mStore.setInstitutionId(insId);
                        mStore.setUserName(StudentName);
                        mStore.setStudentStandard(classId);
                        mStore.setInstituionName(insName);

                        mStore.setStudentPrefrerredSubject(groupId);
                        mStore.setEmailId(userName);

                        //set First to false , so as to not show it again
                        mStore.setIsFirstTime(false);

                        stopDialog();
                        //Show Home Activitu
                        final Intent i = new Intent(this, HomeActivity.class);

                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                } else {
                    //wrong Details
                    stopDialog();
                    Animation anim = AnimationUtils.loadAnimation(this, R.anim.shake);

                    Toast.makeText(this, "Incorrect Details", Toast.LENGTH_SHORT).show();

                }
            } else {
                throw new NullPointerException("Object Cannot e null");
            }

        } catch (JSONException e) {

            stopDialog();
            Log.d(TAG, "onResponse: exception " + e.getLocalizedMessage());

            Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
            FirebaseCrash.log("Error in parsing LOgin Info");
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this,"Cannot connect to Google Services",Toast.LENGTH_LONG).show();
    }
}
