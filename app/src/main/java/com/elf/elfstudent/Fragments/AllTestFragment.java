package com.elf.elfstudent.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.elf.elfstudent.Activities.TestWriteActivity;
import com.elf.elfstudent.Adapters.AllTestAdapter;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.Network.JsonProcessors.TestListProvider;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.BundleKey;
import com.elf.elfstudent.model.AllTestModels;
import com.elf.elfstudent.model.TestPageParentModel;
import com.google.firebase.crash.FirebaseCrash;

import junit.framework.Test;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 18/10/16.
 * This Fragment gets called only for 10th Standard
 */
public class AllTestFragment extends Fragment implements
        ErrorHandler.ErrorHandlerCallbacks,
        TestListProvider.TestProviderCallback , AllTestAdapter.onTestClicked{


    //The Changable Frame Root , invisible by default
    @BindView(R.id.changable_test_view_root)
    FrameLayout mViewRoot;

    @BindView(R.id.top_view_layout)
    RelativeLayout mDataLayout;
    @BindView(R.id.test_page_recycler_view)
    RecyclerView mTestListView;

    private View mView;


    //    the Request Queue for this body
    private AppRequestQueue mRequestQue;
    private JsonArrayRequest mReq = null;

    private static final String TAG = "ALL TEST";


    List<AllTestModels> mSocialQuestion = null;
    List<AllTestModels> mScienceQuestion = null;
    List<AllTestModels> mMathsQuestions = null;


    //Error handler
    ErrorHandler errorHandler;

    //TestProcesso
    TestListProvider mTestListProvider = null;


    DataStore mStore = null;
    private String mStdId;
    private int count = 0;
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext  =context;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    private void prepareTests(String mStdId) {
        JSONObject object = new JSONObject();

//
        try {
            object.put("StudentId", mStdId);
            object.put("Type", "All");


        } catch (Exception e) {
            FirebaseCrash.log("Exception in Making Json Object " + TAG);

        }

        // make request with that body

        String URL = "http://www.hijazboutique.com/elf_ws.svc/GetPendingTests";
        mReq = new JsonArrayRequest(Request.Method.POST, URL, object,
                mTestListProvider, errorHandler);

        mRequestQue.addToRequestQue(mReq);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.all_test_fragment, container, false);
        ButterKnife.bind(this, mView);

        //setting layout Manager for Adapter

        //get the Student Details for making Reuqest
        mStore = DataStore.getStorageInstance(getContext());
        //get Student id
        //get tests to write for that student .. it is overall
        mRequestQue = AppRequestQueue.getInstance(getContext());
        mStdId = mStore.getStudentId();

        errorHandler = new ErrorHandler(this);
        mTestListProvider = new TestListProvider(this);


        if (mStdId != null) {
            prepareTests(mStdId);
        } else {
            FirebaseCrash.log("Test ID null in All Test 10");
            throw new NullPointerException("Student ID cannot be nUll");
        }


//        mScienceList.setAdapter(mAdapter);
//        mSocialList.setAdapter(mAdapter);
//        mMathsList.setAdapter(mAdapter);


        return mView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
    public void TimeoutError() {
        if (!(count > 2)) {
            //make Request again
            if (mRequestQue != null) {
                if (mReq != null) {
                    mRequestQue.addToRequestQue(mReq);
                    count++;
                } else {
                    FirebaseCrash.log("Request object is null, TimeoutError " + TAG);
                }
            }
        } else {

            //max retry Acheived

            if (!mViewRoot.isShown()) {
                mViewRoot.setVisibility(View.VISIBLE);
            }
            if (mDataLayout.isShown()) {
                mDataLayout.setVisibility(View.INVISIBLE);
            }
            mViewRoot.removeAllViews();
            View v = View.inflate(getContext(), R.layout.try_again_layout, mViewRoot);
        }
    }

    @Override
    public void NetworkError() {
        try {

            if (!mViewRoot.isShown()) {
                mViewRoot.setVisibility(View.VISIBLE);
            }

            if (mDataLayout.isShown()) {
                mDataLayout.setVisibility(View.INVISIBLE);
            }
            mViewRoot.removeAllViews();
            View v = View.inflate(getContext(), R.layout.no_internet, mViewRoot);
        } catch (Exception e) {
            FirebaseCrash.log("Exception in AllTestFragment");
        }
    }

    @Override
    public void ServerError() {

        try {
            if (!mViewRoot.isShown()) {
                mViewRoot.setVisibility(View.VISIBLE);
            }
            if (mDataLayout.isShown()) {
                mDataLayout.setVisibility(View.INVISIBLE);
            }

            mViewRoot.removeAllViews();
            View v = View.inflate(getContext(), R.layout.no_data, mViewRoot);
        } catch (Exception e) {
            FirebaseCrash.log("Server Error in AllTestFragment");
        }


    }


    /*
    * This Method is provides data from JsonProcessor
    *
    * save the data in the current activity so that We can get Test ID easily
    *
    *
    * */
    @Override
    public void setTestListData(List<AllTestModels> mTests) {

        mScienceQuestion = mTests;


        if (!mDataLayout.isShown()) {
            //IF Data layout is not shown , show data layout
            mDataLayout.setVisibility(View.VISIBLE);
            if (mViewRoot.isShown()) {
                //
                mViewRoot.setVisibility(View.INVISIBLE);
            }
        }


        //we have all Test for this student {@param mTests }

        if(mStore.getStandard().equals("10")){
            //Student is 10th Prepare Adapter Accodringly
            TestPageParentModel Science_q = new TestPageParentModel("12", getTestsbySubjectId("12",mTests));
            TestPageParentModel social_q  = new TestPageParentModel("13",getTestsbySubjectId("13",mTests));
            TestPageParentModel maths_q = new TestPageParentModel("14",getTestsbySubjectId("14",mTests));
            List<TestPageParentModel> mListData = new ArrayList<>();
            mListData.add(Science_q);
            mListData.add(social_q);
            mListData.add(maths_q);
            try {

                AllTestAdapter mAdapter = new AllTestAdapter(getContext(),mListData,this);
                mTestListView.setAdapter(mAdapter);
                mTestListView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
            catch (Exception e){
                Log.d(TAG, "setTestListData: Exception "+e.getLocalizedMessage());
            }
        }
        else{
            //Student is 12 , find which Group
            TestPageParentModel phy_q = new TestPageParentModel("1",mTests);
            TestPageParentModel chem_q = new TestPageParentModel("2",mTests);
            TestPageParentModel maths_q = new TestPageParentModel("3",mTests);
            TestPageParentModel optional = null;

            if (mStore != null){
                if (mStore.getStudentGroup().equals("COMPUTER")){
                    //Studnet is Computer science

                    optional   = new TestPageParentModel("4",mTests);
                }
                else{
                    //student is Bio
                    optional = new TestPageParentModel("5",mTests);
                }
            }
            List<TestPageParentModel> mListData = new ArrayList<>();
            mListData.add(phy_q);
            mListData.add(chem_q);
            mListData.add(maths_q);
            mListData.add(optional);
            try {

                AllTestAdapter mAdapter = new AllTestAdapter(getContext(),mListData,this);
                mTestListView.setAdapter(mAdapter);
                mTestListView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
            catch (Exception e){
                Log.d(TAG, "setTestListData: Exception "+e.getLocalizedMessage());
            }
        }
    }

    private List<AllTestModels> getTestsbySubjectId(String s, List<AllTestModels> mTests) {

        List<AllTestModels> returnList = new ArrayList<>();

        if (mTests.size() == 0){
            return null;
        }

        for (int i =0 ;i < mTests.size() ; i++){
            if (mTests.get(i).getmTestId().equals(s)){
                //same subject Id, add it to list
                returnList.add(mTests.get(i));
            }
        }
        return returnList;
    }

    @Override
    public void NoTestListData() {


        //NO data Received From Webservice

        //hide Content LAyyout
        if (mDataLayout.isShown()) {
            mDataLayout.setVisibility(View.INVISIBLE);
        }

        //Show no data  layout
        if (!mViewRoot.isShown()) {
            mViewRoot.setVisibility(View.VISIBLE);
        }

        View v = View.inflate(getContext(), R.layout.no_data, mViewRoot);

    }


//    Write Test Button hAs been Clicked , show Test Write PAge for this Test ID

    @Override
    public void showTestWriteaPageFor(String TestId,String subId) {


        final Intent i = new Intent(mContext, TestWriteActivity.class);
        i.putExtra(BundleKey.TEST_ID,TestId);
        i.putExtra(BundleKey.SUBJECT_ID,subId);
        startActivity(i);

        //
    }
}
