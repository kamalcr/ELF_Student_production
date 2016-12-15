package com.elf.elfstudent.Activities;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.WebServices;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 31/10/16.
 */

public class FeedbackActivity extends AppCompatActivity implements ErrorHandler.ErrorHandlerCallbacks {


    private static final String TAG = "Feedack";

    @BindView(R.id.feed_edit)
    EditText mFeedback;

    @BindView(R.id.feed_button)
    Button mSubmitButton;
    String text = null;

    JSONObject mRequestObject = null;
    ErrorHandler errorHandler = null;
    AppRequestQueue mRequestQueue = null;

    JsonArrayRequest mRequest = null;
    @BindView(R.id.feed_toolbar)
    Toolbar feedToolbar;

    private DataStore mStore = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_activity);
        ButterKnife.bind(this);

        mRequestQueue = AppRequestQueue.getInstance(this);
        errorHandler = new ErrorHandler(this);

        setSupportActionBar(feedToolbar);
        feedToolbar.setTitle("Send Feedback");
        ActionBar ab = getSupportActionBar();
        if (ab != null){
            ab.setTitle("send Feedback");
            ab.setDisplayShowHomeEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
        }


        mStore = DataStore.getStorageInstance(this);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = mFeedback.getText().toString();
                if (!TextUtils.isEmpty(text)) {
                    submitFeedback(text);
                }
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                }
                else{
                    finish();
                }
        }

        return true;
    }


    private void submitFeedback(String text) {
        mRequestObject = new JSONObject();
        try {

            mRequestObject.put("UserId",mStore.getStudentId());
            mRequestObject.put("UserType", "Student");
            mRequestObject.put("Feedback", text);
        } catch (Exception e) {
            Log.d(TAG, "submitFeedback: ");
        }

        mRequest = new JsonArrayRequest(Request.Method.POST, WebServices.FEED_URL, mRequestObject, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {

                    JSONObject mOb = response.getJSONObject(0);
                    if (mOb.getString("Statuscode").equals("Success")) {
                        Toast.makeText(getApplicationContext(), "Feedback sent, Thanks for Sharing you valuable opinion", Toast.LENGTH_LONG).show();
                    } else {

                    }
                } catch (Exception e) {
                    Log.d(TAG, "onResponse: ");
                }
            }
        }, errorHandler);

        if (mRequestQueue != null) {
            mRequestQueue.addToRequestQue(mRequest);
        }


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
        Toast.makeText(getApplicationContext(), "Please make sure you have Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void NetworkError() {

        Toast.makeText(getApplicationContext(), "Network Error ,Please try again", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ServerError() {
        Toast.makeText(getApplicationContext(), "Server Error, please try again after some time", Toast.LENGTH_SHORT).show();

    }
}
