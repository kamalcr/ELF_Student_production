package com.elf.elfstudent.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elf.elfstudent.CustomUI.HelviticaLight;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.BundleKey;
import com.elf.elfstudent.model.SubjectModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 18/10/16.
 *
 */

public class AllTestFragment12 extends Fragment {






    // The Views
    @BindView(R.id.all_phy_list)
    RecyclerView mPhyList;
    @BindView(R.id.all_chem_list) RecyclerView mChemList;
    @BindView(R.id.all_maths_list) RecyclerView mMathsList;
    @BindView(R.id.all_opt_list) RecyclerView moptionList;
    @BindView(R.id.opt_subject)
    HelviticaLight moptSubject;

    String subjectId = null;
    String subjecName = null;

    AppRequestQueue  mRequestQueue = null;
    DataStore mStore = null;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null){
           subjectId = getArguments().getString(BundleKey.SUBJECT_ID);
            subjecName = getArguments().getString(BundleKey.SUBJECT_NAME);
        }


        mStore = DataStore.getStorageInstance(getContext());
        mRequestQueue = AppRequestQueue.getInstance(getContext());
    }

    private void setOptionaLsubjectList(String subjecName, String subjectId) {
        moptSubject.setText(subjecName);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.alltest_12,container,false);
        ButterKnife.bind(this,v);
        if (subjectId != null && subjecName != null){
            setOptionaLsubjectList(subjecName,subjectId);
        }

        Log.d("subjectname=",subjecName);
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
    public void onStart() {
        super.onStart();
        if (mStore == null){
            mStore = DataStore.getStorageInstance(getContext());
        }
        if (mRequestQueue == null){
            mRequestQueue = AppRequestQueue.getInstance(getContext());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public AllTestFragment12() {
        super();
    }
}
