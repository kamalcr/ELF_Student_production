package com.elf.elfstudent.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.ScreenUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 20/10/16.
 * the Page which is first shown to user on Register Clicking
 */

public class EmailActivityPage extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final int RC_SIGN_IN = 1001;
    private static final String TAG = "EMAil ACITIVYT";
    @BindView(R.id.welcom_image)
    ImageView welcomImage;
    @BindView(R.id.gsing_in)
    SignInButton gsingIn;
    private GoogleApiClient mGoogleApiClient;

    DataStore mStore = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_activity);
        ButterKnife.bind(this);

        //G sign iN button
        gsingIn.setTranslationY(ScreenUtil.getScreenHeight(this));
        Picasso.with(this).load(R.drawable.welcome_back)
                .resize(ScreenUtil.getScreenWidth(this),ScreenUtil.getScreenHeight(this))

                .into(welcomImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        RUnEnterAnimations();
                    }

                    @Override
                    public void onError() {

                    }
                });

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
        gsingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    signIn();
            }
        });

    }

    private void RUnEnterAnimations() {
        gsingIn.animate().translationY(0).setDuration(600)
                .setInterpolator(new DecelerateInterpolator())
                .start();

    }

    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.

            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {

                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }



    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.d(TAG, "handleSignInResult: "+acct.getEmail());
            Log.d(TAG, "handleSignInResult: token id"+acct.getIdToken());
            Log.d(TAG, "handleSignInResult: id"+acct.getId());
            if (mStore == null){
                mStore = DataStore.getStorageInstance(this);
            }
            mStore.setEmailId(acct.getEmail());

            mStore.setUserName(acct.getDisplayName());
            mStore.setPassword(acct.getId());
            final  Intent i = new Intent(this,PasswordActivity.class);
            startActivity(i);




        } else {
            // Signed out, show unauthenticated UI.
            Log.d(TAG, "handleSignInResult: not Signed iD");
            Log.d(TAG, "handleSignInResult: "+result.getStatus());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }


    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
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
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }
}