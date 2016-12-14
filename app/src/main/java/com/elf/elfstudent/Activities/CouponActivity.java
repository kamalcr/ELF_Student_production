package com.elf.elfstudent.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.RequestParameterKey;
import com.elf.elfstudent.Utils.StringValidator;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 5/12/16.
 *
 */

public class CouponActivity extends AppCompatActivity  implements Response.Listener<JSONArray>,Response.ErrorListener{
    //// TODO: 5/12/16 addd Url
    private static final String COUPON_URL =   "";
    @BindView(R.id.textView2)
    TextView text;

    @BindView(R.id.editText)
    EditText coupBox;
    @BindView(R.id.button2)
    Button couponButton;
    AppRequestQueue mRequestQueue  = null;
    JsonArrayRequest mCouponRequest = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coupon_activity);
        ButterKnife.bind(this);
        mRequestQueue = AppRequestQueue.getInstance(this.getApplicationContext());

        couponButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coupOnButtonCLicked();
            }
        });

    }

    private void coupOnButtonCLicked() {
        String coupon_text = coupBox.getEditableText().toString();




        sendCoupondCode(coupon_text);
    }

    private void sendCoupondCode(String coupon_text) {
        DataStore mStore  = DataStore.getStorageInstance(this.getApplicationContext());
        JSONObject mObject   =  new JSONObject();
        try {
            mObject.put(RequestParameterKey.COUPON,coupon_text);
            mObject.put(RequestParameterKey.STUDENT_ID,mStore.getStudentId());
        }
        catch (Exception e ){
            Log.d("Coupon COde", "sendCoupondCode: ");

        }
        mCouponRequest = new JsonArrayRequest(Request.Method.POST, COUPON_URL, mObject,this,this);

        if (mRequestQueue != null){
            mRequestQueue.addToRequestQue(mCouponRequest);
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
    public void onResponse(JSONArray response) {


        try{

            if (response != null){
                JSONObject mObject =  response.getJSONObject(0);
                if (mObject.getString("StatusCode").equals("1000")){

                    final Intent in  = new Intent(this,CouponAcceptedActivity.class);
                    startActivity(in);
                }
            }
        }
        catch (Exception e){
            Log.d("Exception ", "onResponse: "+e.getLocalizedMessage());
            Toast.makeText(this,"Error Occured Please Try Again ",Toast.LENGTH_LONG).show();

        }

    }







    @Override
    public void onErrorResponse(VolleyError error) {
        Animation an  = AnimationUtils.loadAnimation(this,R.anim.shake);
        coupBox.startAnimation(an);
    }
}
