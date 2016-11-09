package com.elf.elfstudent.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.elf.elfstudent.Activities.TestCompletedActivity;
import com.elf.elfstudent.CustomUI.HelviticaLight;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.Network.JsonProcessors.TestOverviewProvider;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.BundleKey;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 30/10/16.
 * <p>
 * The Fragment which is shhown in {@link TestCompletedActivity} viewpager
 */

public class TesCompletedOverallFragment extends Fragment implements ErrorHandler.ErrorHandlerCallbacks, TestOverviewProvider.TestOverviewCallback {


    private static final String TAG = "TestCompFragment";
    //// TODO: 10/11/16 add url
    private static final String OVERIVEW_URL = "";
    @BindView(R.id.test_comp_test_desc)
    HelviticaLight test_desc;
    @BindView(R.id.test_comp_test_sub)
    HelviticaLight subjectName;
    @BindView(R.id.right_no)
    HelviticaLight correctoptions;
    @BindView(R.id.textView19)
    HelviticaLight wrongoptions;

    DataStore mStore  =  null;
    private Context mContext = null;
    AppRequestQueue  mRequestQueue = null;
    ErrorHandler errorHandler = null;
    TestOverviewProvider testOverviewProvider = null;
    private String studentId = null;
    private String testID = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.test_completed_overall, container, false);
        ButterKnife.bind(this, v);


        try {

            mStore = DataStore.getStorageInstance(mContext);
        }
        catch (Exception e ){
            throw  new NullPointerException("Context cannot be NUll");
        }

        mStore = DataStore.getStorageInstance(mContext);
        errorHandler = new ErrorHandler(this);
        testOverviewProvider  = new TestOverviewProvider(this);

        if (getArguments() != null){
            testID = getArguments().getString(BundleKey.TEST_ID);
        }


        studentId  = mStore.getStudentId();

        //Make Requeat with student ID and Test ID

        if (studentId != null && testID != null){
            getOverviewReportFor(studentId,testID);
        }







        return v;

    }

    private void getOverviewReportFor(String s, String testID) {
        JSONObject mObject = null;
        try {
            mObject = new JSONObject();
            mObject.put("StudentId",s);
            mObject.put("TestId",testID);
        }
        catch(Exception e ){
            Log.d(TAG, "getOverviewReportFor: ");
        }

        JsonArrayRequest mREJsonArrayRequest = new JsonArrayRequest(Request.Method.POST,OVERIVEW_URL,mObject,testOverviewProvider,errorHandler);
        if (mRequestQueue!=null){
            mRequestQueue.addToRequestQue(mREJsonArrayRequest);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext = null;

    }


//    // TODO: 10/11/16 show stocl Layouts
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
    public void ShowOverview(String TestDesc, String SubjectName, String totalQues, String No_ofRight) {


        test_desc.setText(TestDesc);
        subjectName.setText(SubjectName);
        correctoptions.setText(No_ofRight);
        wrongoptions.setText(totalQues);
    }

    @Override
    public void noData() {

    }
}
