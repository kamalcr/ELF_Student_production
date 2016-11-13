package com.elf.elfstudent.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.elf.elfstudent.Adapters.ChemistryAdapter;
import com.elf.elfstudent.Adapters.OptionalAdapter;
import com.elf.elfstudent.Adapters.PhysicsAdapter;
import com.elf.elfstudent.Adapters.TestRecyclerAdapeters.MathsAdapter;
import com.elf.elfstudent.CustomUI.HelviticaLight;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.Network.JsonProcessors.TestListProvider12;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.BundleKey;
import com.elf.elfstudent.model.AllTestModels;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 25/10/16.
 * The Fragment Displayed in{@link BrowseTestActivity}
 * This is Shown only for 12th
 *
 * based on Subject id and Subject Name
 * manipulate TextView
 *
 */

public class AllTest12 extends Fragment implements
        ErrorHandler.ErrorHandlerCallbacks, TestListProvider12.TestProviderCallback12, PhysicsAdapter.PhysicsAdapterCallback, MathsAdapter.MathsAdapterCallback, ChemistryAdapter.ChemistryAdapterCallback, OptionalAdapter.OptionalCallback {


    private static final String TAG = "AllTEST12";


    //The Root view

    @BindView(R.id.alltest_frag_root)
    RelativeLayout mchangeableRoot;
    //Physics Recycler view
  @BindView(R.id.all_phy_list)
    RecyclerView mPhysicsList ;


    //Chemistry Recycelr view

    @BindView(R.id.all_chem_list) RecyclerView mChemList;

    //Maths
    @BindView(R.id.all_maths_list) RecyclerView mMathsList;

    //Optional Text and list

    @BindView(R.id.opt_subject)
    HelviticaLight mSubject;

    @BindView(R.id.all_opt_list) RecyclerView mOptList;


    //Network Relate Code


    ErrorHandler errorHandler  = null;
    TestListProvider12 testListProvider = null;
    DataStore mStore = null;
    AppRequestQueue mRequestQueue  = null;




    private Context mContext = null;

    private PhysicsAdapter physicsAdapter = null;
    private ChemistryAdapter chemistryAdapter = null;
    private  MathsAdapter mathsAdapter = null;
    private OptionalAdapter optionalAdapter = null;


    List<AllTestModels> phylist = null;
    List<AllTestModels> chemList = null;
    List<AllTestModels> mathsList = null;
    List<AllTestModels> optionalList = null;

    FirebaseAnalytics mAnalytics;
    private int count = 0;
    private String studentId = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alltest_12,container,false);
        ButterKnife.bind(this,v);


        mStore = DataStore.getStorageInstance(mContext);
        testListProvider  = new TestListProvider12(this);
        errorHandler = new ErrorHandler(this);

        mRequestQueue = AppRequestQueue.getInstance(mContext);
        mAnalytics = FirebaseAnalytics.getInstance(mContext);


        if (mStore != null){

            studentId = mStore.getStudentId();
        }
        else{
            throw new NullPointerException("Store cannot be Null");

        }

        if (studentId != null){
            prepareTests(studentId);
        }
        else{
            throw new NullPointerException("Studnet ID cannot be NUll");
        }




        return v;
    }

    private void prepareTests(String s) {

    }

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
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext = null;
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

        if(!(count>2)){
            //Retry Request again
            if ()

        }
        else{
            //show the trya again page
            mchangeableRoot.removeAllViews();
            try {

                View v = View.inflate(mContext,R.layout.no_data,mchangeableRoot);
            }
            catch (Exception e ){
                Log.d(TAG, "NoTestListData: ");
            }
        }
    }

    @Override
    public void NetworkError() {

    }

    @Override
    public void ServerError() {

    }

    private void refreshLayout() {

        try {
            getView().requestLayout();
        }
        catch (Exception e ){
            Log.d(TAG, "refreshLayout: exception in Refreshing layout");
        }
    }

    @Override
    public void setTestListData(List<AllTestModels> mphysics, List<AllTestModels> mchemistry, List<AllTestModels> maths, List<AllTestModels> optionallist, String optionalName) {
        mSubject.setText(optionalName);
        phylist = mphysics;
        chemList = mchemistry;
        mathsList = maths;
        optionalList = optionallist;
        setPhyscisAdapter(phylist);
        setChemistryAdapter(chemList);
        setMathsListAdapter(mathsList);
        setOptionalListAdapter(optionalList);
    }

    private void setOptionalListAdapter(List<AllTestModels> optionalList) {
        optionalAdapter = new OptionalAdapter(this,optionalList,mContext);

      if (mOptList != null){
          mOptList.setAdapter(optionalAdapter);
          refreshLayout();
      }


    }

    private void setMathsListAdapter(List<AllTestModels> mathsList) {
        mathsAdapter = new MathsAdapter(this,mathsList,mContext);

        if (mMathsList != null){
            mMathsList.setAdapter(mathsAdapter);
            refreshLayout();
        }

    }

    private void setChemistryAdapter(List<AllTestModels> chemList) {
        chemistryAdapter = new ChemistryAdapter(this,chemList,mContext);
        if (mChemList != null){
            mChemList.setAdapter(chemistryAdapter);
            refreshLayout();
        }
    }

    private void setPhyscisAdapter(List<AllTestModels> phylist) {

        physicsAdapter  = new PhysicsAdapter(this,phylist,mContext);
        if (mPhysicsList != null){
            mPhysicsList.setAdapter(physicsAdapter);
            refreshLayout();
        }
    }

    @Override
    public void NoTestListData() {
        FirebaseCrash.log("No Test Questions Received for 12th ");
        mchangeableRoot.removeAllViews();
        try {

            View v = View.inflate(mContext,R.layout.no_data,mchangeableRoot);
        }
        catch (Exception e ){
            Log.d(TAG, "NoTestListData: ");
        }
    }

    /*The methods called from adapter code
    *
    * These methods return holder(for shared animations and Position)
    *
    * from position get test iD and Show Test page
    * */

    @Override
    public void PhysicsTestClicked(PhysicsAdapter.TestHolder holder, int position) {
        Bundle b = new Bundle();
        b.putString(BundleKey.F_PHYSCIS,"writing physics");
        mAnalytics.logEvent("Test",b);

        String testId = null;

        //get Test ID
        if (phylist != null){
            try {
                testId = phylist.get(position).getmTestId();
            }
            catch (Exception e ){
                FirebaseCrash.log("No test id from phyiscs");
            }
        }

        //pass it to next activity
        if (testId != null){
            showTestWritingPage(testId);
        }
        else{
            FirebaseCrash.log("NO Test ID");
        }



    }

    private void showTestWritingPage(String testId) {
        Log.d(TAG, "showTestWritingPage: "+testId);
        final Intent i  = new Intent(getContext(),TestWriteActivity.class);
        i.putExtra(BundleKey.TEST_ID,testId);
        startActivity(i);
    }

    @Override
    public void mathsTestClicked(MathsAdapter.TestHolder holder, int position) {
        String testId = null;

        //get Test ID
        if (mathsList != null){
            try {
                testId = mathsList.get(position).getmTestId();
            }
            catch (Exception e ){
                FirebaseCrash.log("No test id from chemistry");
            }
        }

        //pass it to next activity
        if (testId != null){
            showTestWritingPage(testId);
        }
        else{
            FirebaseCrash.log("NO Test ID");
        }
    }

    @Override
    public void chemistryTestClicked(ChemistryAdapter.TestHolder holder, int position) {

        String testId = null;

        //get Test ID
        if (chemList != null){
            try {
                testId = chemList.get(position).getmTestId();
            }
            catch (Exception e ){
                FirebaseCrash.log("No test id from phyiscs");
            }
        }

        //pass it to next activity
        if (testId != null){
            showTestWritingPage(testId);
        }
        else{
            FirebaseCrash.log("NO Test ID");
        }
    }

    @Override
    public void optionalTestClicked(OptionalAdapter.TestHolder holder, int position) {
        String testId = null;

        //get Test ID
        if (optionalList != null){
            try {
                testId = optionalList.get(position).getmTestId();
            }
            catch (Exception e ){
                FirebaseCrash.log("No test id from phyiscs");
            }
        }

        //pass it to next activity
        if (testId != null){
            showTestWritingPage(testId);
        }
        else{
            FirebaseCrash.log("NO Test ID");
        }
    }
}
