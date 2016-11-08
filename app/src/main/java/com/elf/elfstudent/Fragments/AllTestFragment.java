package com.elf.elfstudent.Fragments;

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
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.elf.elfstudent.Activities.TestWriteActivity;
import com.elf.elfstudent.Adapters.TestLessonAdapter;
import com.elf.elfstudent.Adapters.TestRecyclerAdapeters.MathsAdapter;
import com.elf.elfstudent.Adapters.TestRecyclerAdapeters.ScienceAdapter;
import com.elf.elfstudent.Adapters.TestRecyclerAdapeters.SocialAdapter;
import com.elf.elfstudent.CustomUI.HelviticaMedium;
import com.elf.elfstudent.CustomUI.QucikSand;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.Network.JsonProcessors.TestListProvider;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.BundleKey;
import com.elf.elfstudent.model.AllTestModels;
import com.google.firebase.crash.FirebaseCrash;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 18/10/16.
 * This Fragment gets called only for 10th Standard
 */
public class AllTestFragment extends Fragment implements  TestLessonAdapter.OnTestViewClick, ErrorHandler.ErrorHandlerCallbacks, TestListProvider.TestProviderCallback, MathsAdapter.MathsAdapterCallback, ScienceAdapter.ScienceAdapterCallback, SocialAdapter.SocialAdapterCallback {


    ///The Textview of subject Names
    @BindView(R.id.all_science_text)
    QucikSand mScienceText;
    @BindView(R.id.all_social_text)
    QucikSand mSocialText;
    @BindView(R.id.all_maths_tex)
    QucikSand mMathsText;

    //The list
    @BindView(R.id.all_math_list)
    RecyclerView mMathsList;
    @BindView(R.id.all_social_list)
    RecyclerView mSocialList;


    //The Changable Frame Root , invisible by default
    @BindView(R.id.changable_test_view_root)
    FrameLayout mViewRoot;

    @BindView(R.id.top_view_layout)
    RelativeLayout mDataLayout;
    private View mView;


    @BindView(R.id.all_sci_list)
    RecyclerView mScienceList;

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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }



    private void prepareTests(String mStdId) {
        JSONObject object = new JSONObject();

//
        try {
            object.put("StudentId", "1");
            object.put("Type", "All");


        } catch (Exception e) {
            FirebaseCrash.log("Exception in Making Json Object "+TAG);

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
        mTestListProvider  = new TestListProvider(this);


        if (mStdId != null){
            prepareTests(mStdId);
        }
        else{
            FirebaseCrash.log("Test ID null in All Test 10");
        }

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager mManager2  = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager mManager3  = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        mScienceList.setLayoutManager(mLinearLayoutManager);
        mSocialList.setLayoutManager(mManager2);
        mMathsList.setLayoutManager(mManager3);


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
    public void onTestCardClick(TestLessonAdapter.LessonTextHolder holder, int position) {
        Log.d(TAG, "onTestCardClick: ");

        final Intent i = new Intent(getActivity(), TestWriteActivity.class);
//        i.putExtra(BundleKey.TEST_ID,t)



    }

    @Override
    public void TimeoutError() {
        if (!(count>2)){
            //make Request again
            if (mRequestQue != null){
                if (mReq != null){
                    mRequestQue.addToRequestQue(mReq);
                    count++;
                }else{
                    FirebaseCrash.log("Request object is null, TimeoutError "+TAG);
                }
            }
        }
        else{

            //max retry Acheived

            if (!mViewRoot.isShown()){
             mViewRoot.setVisibility(View.VISIBLE);
            }
            if (mDataLayout.isShown()){
                mDataLayout.setVisibility(View.INVISIBLE);
            }
            mViewRoot.removeAllViews();
            View v = View.inflate(getContext(),R.layout.try_again_layout,mViewRoot);
        }
    }

    @Override
    public void NetworkError() {
        if (!mViewRoot.isShown()){
            mViewRoot.setVisibility(View.VISIBLE);
            mViewRoot.removeAllViews();
            View v = View.inflate(getContext(),R.layout.no_internet,mViewRoot);
        }
        if (mDataLayout.isShown()){
            mDataLayout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void ServerError() {
        if (!mViewRoot.isShown()){
            mViewRoot.setVisibility(View.VISIBLE);
        }
        if (mDataLayout.isShown()){
            mDataLayout.setVisibility(View.INVISIBLE);
        }

        mViewRoot.removeAllViews();
        View v = View.inflate(getContext(),R.layout.no_data,mViewRoot);
        FirebaseCrash.log("Server error in "+TAG);
    }


  /*
  * This Method is provides data from JsonProcessor
  *
  * save the data in the current activity so that We can get Test ID easily
  *
  *
  * */
    @Override
    public void setTestListData(List<AllTestModels> mScience, List<AllTestModels> mSocial, List<AllTestModels> maths) {
        Log.d(TAG, "setTestListData: ");
       mScienceQuestion = mScience;
        mSocialQuestion = mSocial;
        mMathsQuestions = maths;


        if (!mDataLayout.isShown()){


            mDataLayout.setVisibility(View.VISIBLE);
            if(mViewRoot.isShown()){
                mViewRoot.setVisibility(View.INVISIBLE);
            }


        }
        setSocailAdapter(mSocial);
        setScicneAdapter(mScience);
        setMathsAdapter(maths);
    }

    @Override
    public void NoTestListData() {


        //NO data Received From Webservice

        //hide Content LAyyout
        if (mDataLayout.isShown()){
            mDataLayout.setVisibility(View.INVISIBLE);
        }

        //Show no data  layout
        if (!mViewRoot.isShown()){
            mViewRoot.setVisibility(View.VISIBLE);
        }

        View v = View.inflate(getContext(),R.layout.no_data,mViewRoot);

    }

    private void setMathsAdapter(List<AllTestModels> maths) {
        MathsAdapter mMathsAdapter = new MathsAdapter(this, maths, getContext());
        if (mMathsList != null){

            mMathsList.setAdapter(mMathsAdapter);
            refreshLayout();
        }
        else{
            Log.d(TAG, "MAths Null");
        }

    }

    private void setScicneAdapter(List<AllTestModels> mScience) {
        ScienceAdapter mScienceAdapter = new ScienceAdapter(this, mScience, getContext());
        if (mScienceList != null) {

            mScienceList.setAdapter(mScienceAdapter);
            refreshLayout();
        }
    }


    private void refreshLayout() {

        try {
            getView().requestLayout();
        }
        catch (Exception e ){
            Log.d(TAG, "refreshLayout: exception in Refreshing layout");
        }
    }


    private void setSocailAdapter(List<AllTestModels> mSocial) {
        SocialAdapter mSocialAdapter = new SocialAdapter(this, mSocial, getContext());
        if (mSocialList != null) {

            mSocialList.setAdapter(mSocialAdapter);
            refreshLayout();
        }
    }



    /*Callbacks form Adapter Some Test Has Been Clicked
    * The main thing here is to get Test Id
    * */


    //Maths Test has been Clicked
    @Override
    public void mathsTestClicked(MathsAdapter.TestHolder holder, int position) {

        Log.d(TAG, "mathsTestClicked: ");
        String testId = null;
        if (mMathsQuestions != null){
            try {

                testId = mMathsQuestions.get(position).getmTestId();
            }
            catch (Exception e ){
                Log.d(TAG, "NO Subject iD");
            }
        }
        //

        if (testId  != null){
            showTestWritingPage(testId);
        }

    }

    private void showTestWritingPage(String testId) {
        final Intent i  = new Intent(getContext(),TestWriteActivity.class);
        i.putExtra(BundleKey.TEST_ID,testId);
        startActivity(i);
    }


    //Scienc Subject has Been CLicked
    @Override
    public void scienceTestClicked(ScienceAdapter.TestHolder holder, int position) {
        String testId = null;
        if (mScienceQuestion != null){
            try {

                testId = mScienceQuestion.get(position).getmTestId();
            }
            catch (Exception e ){
                Log.d(TAG, "NO Subject iD");
            }
        }
        //

        if (testId  != null){
            showTestWritingPage(testId);
        }

    }

    @Override
    public void SocialTestClicked(SocialAdapter.TestHolder holder, int position) {
        String testId = null;
        if (mSocialQuestion != null){
            try {

                testId = mScienceQuestion.get(position).getmTestId();
            }
            catch (Exception e ){
                Log.d(TAG, "NO Subject iD");
            }
        }
        //

        if (testId  != null){
            showTestWritingPage(testId);
        }
    }
}
