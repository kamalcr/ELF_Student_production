package com.elf.elfstudent.Activities;

import android.animation.Animator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.elf.elfstudent.CustomUI.HelviticaLight;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.Network.JsonProcessors.EmailHandler;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.BundleKey;
import com.elf.elfstudent.Utils.RequestParameterKey;
import com.elf.elfstudent.Utils.ScreenUtil;
import com.elf.elfstudent.Utils.StringValidator;
import com.elf.elfstudent.Utils.WebServices;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.crash.FirebaseCrash;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 20/10/16.
 * the Page which is first shown to user on Register Clicking
 */

public class EmailActivityPage extends AppCompatActivity  implements
        ErrorHandler.ErrorHandlerCallbacks, EmailHandler.EmaiCallbacks {


private static final String TAG = "ELF";

private static final String EMAIL_URL = "http://www.elfanalysis.net/elf_ws.svc/CheckStudentEmail";
@BindView(R.id.email_text)
TextInputEditText mEmailBox;

    @BindView(R.id.welcom_image) ImageView mBackImage;

@BindView(R.id.email_next_button)
ImageView mNextButton;

//sign in Text

@BindView(R.id.sign_in_text)
HelviticaLight mSignIn;

        DataStore mStore = null;
        EmailHandler emailHandler = null;
        ErrorHandler errorHandler = null;
        AppRequestQueue mRequestQueue = null;


        String email = null;

        ProgressDialog mDialog = null;
        JsonArrayRequest mRequest = null;
        int count =0;



@Override
protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.email_activity);
        ButterKnife.bind(this);

        loadBitmap(R.drawable.welcome_back,mBackImage);



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

        //Sign in Page

        mSignIn.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        showLoginPage();
        }
        });
        }

private void showLoginPage() {
final  Intent i = new Intent(this,LoginActivity.class);
        if (email != null){

                i.putExtra(BundleKey.ARG_EMAIL_ID_TAG,email);
        }

        startActivity(i);
        }

private void CheckEmail() {
        email  = mEmailBox.getText().toString();
        if (StringValidator.checkeEMail(email)){
        //Correct Email format
        checkifEmailExists(email);
        }
        else{
        //wrong Email Format
                try {

                        Toast.makeText(this,"Enter Valid Email",Toast.LENGTH_SHORT).show();
                        Animation animation = AnimationUtils.loadAnimation(this,R.anim.shake);
                        mEmailBox.startAnimation(animation);
                        mEmailBox.setText("");

                }
                catch (Exception e ){
                        FirebaseCrash.log("Exception ");
                }
        }
}

private void checkifEmailExists(String email) {
        mDialog = new ProgressDialog(this);
        mDialog.setIndeterminate(true);
        mDialog.setMessage("Checking for Registration");
//        mDialog.setContentView(R.layout.progress_view);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
        email = email.trim();
        JSONObject mObj = new JSONObject();
        try {
        mObj.put(RequestParameterKey.EMAIL_ID,email);

        }
        catch (Exception e ){
        Log.d(TAG, "checkifEmailExists: ");
        }

        mRequest = new JsonArrayRequest(Request.Method.POST,EMAIL_URL,mObj,emailHandler,errorHandler);
        mRequestQueue.addToRequestQue(mRequest);

        }


public void loadBitmap(int resId, ImageView imageView) {
        Picasso.with(this)

        .load(R.drawable.welcome_back)
        .resize(ScreenUtil.getScreenWidth(this),ScreenUtil.getScreenHeight(this))

        .into(mBackImage, new Callback() {
@Override
public void onSuccess() {
        RunEnterAnimations();
        }

@Override
public void onError() {

        }
        });

        }

private void RunEnterAnimations() {
        mEmailBox.setTranslationY(ScreenUtil.getScreenHeight(this));
        mEmailBox.animate().translationY(0).setDuration(800)
        .setInterpolator(new DecelerateInterpolator(2f))
        .setListener(new Animator.AnimatorListener() {
@Override
public void onAnimationStart(Animator animator) {
        if (!mEmailBox.isShown()){

        mEmailBox.setVisibility(View.VISIBLE);
        }
        }

@Override
public void onAnimationEnd(Animator animator) {
        mSignIn.setVisibility(View.VISIBLE);
        popupNextButton();
        }

@Override
public void onAnimationCancel(Animator animator) {

        }

@Override
public void onAnimationRepeat(Animator animator) {

        }
        }).start();
        }

private void popupNextButton() {
        if (!mNextButton.isShown()){
                mNextButton.setVisibility(View.VISIBLE);   //make button visible and pop up
        }
        mNextButton.setScaleX(0);
        mNextButton.setScaleY(0);
        mNextButton.animate().scaleX(1.1f).scaleY(1.1f)
                   .setInterpolator(new OvershootInterpolator(1.5f))
                   .setDuration(600)
                   .start();
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


        if (mRequestQueue !=null){
           if (!(count>2)){
                    mRequestQueue.addToRequestQue(mRequest);
           }
          else{
                 stopDialog();
                 Toast.makeText(this,"Please Make sure You Have Connection",Toast.LENGTH_SHORT).show();
           }
        }
 }

private void stopDialog() {
        if (mDialog != null){
        if (mDialog.isShowing()){
        mDialog.dismiss();
        }
        }
        }

@Override
public void NetworkError() {

        stopDialog();

        Toast.makeText(getApplicationContext(),"Network Error",Toast.LENGTH_SHORT).show();
        }

@Override
public void ServerError() {

        stopDialog();
        Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_SHORT).show();
        }

@Override
public void ShowPersonalInfoPage() {

        stopDialog();
        Log.d(TAG, "going to register page");
        mStore.setEmailId(email);
         final Intent i = new Intent(this,RegisterActivity.class);

          startActivity(i);
        }

       @Override public void emailAlreadyExists() {
        //// TODO: 20/10/16 email Already Existe show toast
        stopDialog();
         final  Intent i = new Intent(this,LoginActivity.class);
               startActivity(i);
        }



        }