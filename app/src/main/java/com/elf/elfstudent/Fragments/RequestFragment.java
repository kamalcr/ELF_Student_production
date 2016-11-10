package com.elf.elfstudent.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.elf.elfstudent.Adapters.RequestDisplayAdapter;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.Network.JsonProcessors.RequestdataProvider;
import com.elf.elfstudent.R;
import com.elf.elfstudent.model.StudentRequestModel;
import com.google.firebase.crash.FirebaseCrash;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 10/11/16.
 * The Request Fragments
 *
 * send Student ID as Arguments
 */
public class RequestFragment extends Fragment implements ErrorHandler.ErrorHandlerCallbacks, RequestdataProvider.RequestCallbacks, RequestDisplayAdapter.RequestCall {





//    // TODO: 10/11/16 get the URL
    private static String URL = "";


    ///The Root View

    @BindView(R.id.request_root)
    RelativeLayout mRoot;

    private Context mContext = null;
    private AppRequestQueue mRequestQueue = null;
    private ErrorHandler errorHandler = null;
    private JsonArrayRequest getStudentRequests;
    private RequestdataProvider requestdataProvider = null;
    DataStore mStore = null;



    RecyclerView mListView;
    private String studentId = null;
    private RequestDisplayAdapter mAdapter = null;


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
     View v = LayoutInflater.from(mContext).inflate(R.layout.request_frag,container,false);
        ButterKnife.bind(this,v);
        mRequestQueue = AppRequestQueue.getInstance(mContext);
        errorHandler = new ErrorHandler(this);
        requestdataProvider = new RequestdataProvider(this);


        mStore = DataStore.getStorageInstance(mContext);

        if (mStore != null){
            studentId = mStore.getStudentId();
        }

        if (studentId != null){
            getRequests(studentId);
        }

        return v;
    }

    private void getRequests(String s) {
        JSONObject mObject  = new JSONObject();
        try {
            mObject.put("StudentId",s);

        }
        catch (Exception e ){
            FirebaseCrash.log("Exception in putting JSon");

        }

        getStudentRequests = new JsonArrayRequest(Request.Method.POST,URL,mObject,requestdataProvider,errorHandler);

        if (mRequestQueue != null){
            mRequestQueue.addToRequestQue(getStudentRequests);
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void TimeoutError() {
        mRoot.removeAllViews();
        View v = View.inflate(mContext,R.layout.try_again_layout,mRoot);

    }

    @Override
    public void NetworkError() {

        mRoot.removeAllViews();
        View v = View.inflate(mContext,R.layout.no_data,mRoot);

    }

    @Override
    public void ServerError() {

        mRoot.removeAllViews();
        View v = View.inflate(mContext,R.layout.no_data,mRoot);
        FirebaseCrash.log("Server Error");
    }

    @Override
    public void setNotification(List<StudentRequestModel> mList) {



        mRoot.removeAllViews();
        View vi = View.inflate(mContext,R.layout.home_recycler,mRoot);
        mListView = (RecyclerView) vi.findViewById(R.id.home_list);
        mAdapter = new RequestDisplayAdapter(mContext,mList,this);

    }

    private void setAdapter(List<StudentRequestModel> mList) {

    }

    @Override
    public void noRequest() {

    }


//    // TODO: 11/11/16 get Student ID
    @Override
    public void AcceptRequest() {

    }

    @Override
    public void RejectButtonClicked() {

    }
}
