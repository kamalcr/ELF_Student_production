package com.elf.elfstudent.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.elf.elfstudent.Adapters.SchoolListAdapter;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.Network.InstituteRespHandler;
import com.elf.elfstudent.Network.NetworkUitls;
import com.elf.elfstudent.Network.RegisterListener;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.BundleKey;
import com.elf.elfstudent.Utils.RequestParameterKey;
import com.elf.elfstudent.model.InstitutionModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 20/10/16.
 * The Instituite and Standard Choosing Page
 */

public class InstitutePage extends AppCompatActivity implements ErrorHandler.ErrorHandlerCallbacks, InstituteRespHandler.InstituteHandler, RegisterListener.RegistrationCallback {


    private static final String TAG = "Institute page";
    private static final String GET_INSTITUTE_URL = "";
    private static final String REGISTER_URL = "";
    String boardId  = null;
    String stateId = null;


    //The Views

    @BindView(R.id.institute_page_register_button)
    Button mRegisterButton;


    @BindView(R.id.ins_autocomplete)
    AutoCompleteTextView mInsTextView;

    //standard Spinner can be 10th or 11th or 12th
    @BindView(R.id.ins_std_spinner)
    Spinner mSpinner;



    //The Toolabar


    List<InstitutionModel> institutionList;

    private AppRequestQueue mRequestQueue = null;
    ErrorHandler errorHandler = null;
    InstituteRespHandler instituteRespHandler = null;

    DataStore mStore = null;
    RegisterListener mRegisterListener = null;

    SchoolListAdapter mAdapter = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.institute_page);
        ButterKnife.bind(this);

        //get Board Id and State Id and Set it to Adapter
        if (getIntent() != null){
            boardId = getIntent().getStringExtra(BundleKey.ARG_BOARD_ID);
            stateId  = getIntent().getStringExtra(BundleKey.ARG_STATE_ID);

        }
        //get a Handle to Saved Values
        mStore  = DataStore.getStorageInstance(getApplicationContext());

        //The Network Handler objects
        errorHandler = new ErrorHandler(this);
        instituteRespHandler = new InstituteRespHandler(this);
        mRegisterListener = new RegisterListener(this);

        mRequestQueue  = AppRequestQueue.getInstance(getApplicationContext());

        prepareAdapterForInstituion(boardId,stateId);




        //Register Button
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterStudent();
            }
        });
    }

    private void RegisterStudent() {
        JSONObject mObject = new JSONObject();

        //Request Body Objects
        try{
            mObject.put(RequestParameterKey.board_id,mStore.getBoardId());
            mObject.put(RequestParameterKey.STUDENT_NAME,mStore.getUserName());
            mObject.put(RequestParameterKey.EMAIL_ID,mStore.getEmailId());
            mObject.put(RequestParameterKey.PASSWORD,mStore.getPassWord());
            mObject.put(RequestParameterKey.PHONE,mStore.getPhoneNumber());
            mObject.put(RequestParameterKey.STATE_ID,mStore.getStateId());
            //get Instituion Id
//            mObject.put(RequestParameterKey.INSTITUION_ID,mStore.)

            //get standard
//            mObject.put(RequestParameterKey.STANDARD,)
        }
        catch (Exception e ){
            Log.d(TAG, "RegisterStudent: ");
        }

        //Request Body
        JsonObjectRequest mRequest = new JsonObjectRequest(Request.Method.POST,REGISTER_URL,mObject,mRegisterListener,errorHandler);

        //add to request Queue
        mRequestQueue.addToRequestQue(mRequest);
    }

    private void prepareAdapterForInstituion(String boardId, String stateId) {
        JSONObject mObject = new JSONObject();
        try{
            mObject.put(RequestParameterKey.BOARD_ID,boardId);
            mObject.put(RequestParameterKey.STATE_ID,stateId);
        }
        catch (Exception e ){
            Log.d(TAG, "prepareAdapterForInstituion: ");

        }
        JsonArrayRequest mRequest  = new JsonArrayRequest(Request.Method.POST,GET_INSTITUTE_URL,mObject,instituteRespHandler,errorHandler);
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

    /*This Method gives us the list of institution from { @link InstituteRespHandler }
    * Prepare the Adatpter and set it to Cusotm Autcomplete
    *
    * //todo set Autocomplete view
    *
    *
    * */

    @Override
    public void setInstitutionList(List<InstitutionModel> list) {
        mAdapter = new SchoolListAdapter(getApplicationContext());
    }


    @Override
    public void Registered(String studentId) {
        if (mStore != null){

            //set studentId
            mStore.setStudentId(studentId);
            mStore.setIsFirstTime(false);

        }

        //Finally Show home Page ,Before That Show Choose Subject Activity
        final Intent  i = new Intent(this,ChooseSubjectActivity.class);

        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}
