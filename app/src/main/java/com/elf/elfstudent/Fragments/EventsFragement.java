package com.elf.elfstudent.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.elf.elfstudent.Activities.DetailedTestReportActivity;
import com.elf.elfstudent.Activities.TestCompletedActivity;
import com.elf.elfstudent.Adapters.EventsAdapter;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.Network.JsonProcessors.EventDataProvider;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.BundleKey;
import com.elf.elfstudent.model.StudentEvent;
import com.google.firebase.crash.FirebaseCrash;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 10/11/16.
 *
 * The Events Fragment , showuld have student id , to hit the webservice
 *
 */
public class EventsFragement  extends Fragment implements
        ErrorHandler.ErrorHandlerCallbacks,
        EventDataProvider.EventDataCallbacls,
        EventsAdapter.OnCardClick {


//    // TODO: 11/11/16 add URL
    private static final String URLL = "";
    private AppRequestQueue mRequestQueue = null;
    private ErrorHandler errorHandler =  null;
    EventDataProvider eventDataProvider = null;

    @BindView(R.id.event_root)
    RelativeLayout mRoot;
    private Context mContext = null;
    private DataStore mDataStore = null;
    private String studentId = null;
    JsonArrayRequest mRequest  = null;

    RecyclerView mLRecyclerView = null;
    private EventsAdapter mAdapter = null;

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
       View view = LayoutInflater.from(getContext()).inflate(R.layout.events_frag,container,false);
        ButterKnife.bind(this,view);


        mRequestQueue = AppRequestQueue.getInstance(mContext);
        errorHandler = new ErrorHandler(this);
        eventDataProvider = new EventDataProvider(this);
        mDataStore  = DataStore.getStorageInstance(mContext);

        if (mDataStore!= null){
            studentId = mDataStore.getStudentId();

        }
        else{
            FirebaseCrash.log("Store is null");
        }
        if (studentId != null){
            getEvents(studentId);
        }
        else{
            throw new NullPointerException("Student Id cannot be null");
        }





        return view;
    }

    private void getEvents(String s) {
        JSONObject  mObject = new JSONObject();
        try {
            mObject.put("StudentId",s);

        }
        catch (Exception e ){
            FirebaseCrash.log("Stuent ID cannot be null");
        }

        mRequest = new JsonArrayRequest(Request.Method.POST,URLL,mObject,eventDataProvider,errorHandler);


        if (mRequestQueue != null){
            mRequestQueue.addToRequestQue(mRequest);
        }


    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onStop() {
        super.onStop();
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext  = null;
        mAdapter = null;
        mDataStore = null;
        errorHandler = null;
        eventDataProvider = null;
        mRequestQueue = null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

    @Override
    public void EventsReceived(List<StudentEvent> mList) {
        mRoot.removeAllViews();
        View v = View.inflate(mContext,R.layout.home_recycler,mRoot);
        mLRecyclerView = (RecyclerView) v.findViewById(R.id.home_list);
        mAdapter = new EventsAdapter(mContext,mList,this);
        mLRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mLRecyclerView.setAdapter(mAdapter);



    }

    @Override
    public void NoEvents() {

    }

    @Override
    public void showTestReports(String testId) {



        showReportActivity(testId);
    }

    private void showReportActivity(String testId) {
        final  Intent  i = new Intent(mContext, TestCompletedActivity.class);
        i.putExtra(BundleKey.TEST_ID,testId);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    @Override
    public void PublicTextClicked() {

    }
}
