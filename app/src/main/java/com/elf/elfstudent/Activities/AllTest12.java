package com.elf.elfstudent.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elf.elfstudent.CustomUI.HelviticaLight;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.Network.JsonProcessors.TestListProvider12;
import com.elf.elfstudent.R;

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

public class AllTest12 extends Fragment {



    //Physics Recycler view
/*    @BindView(R.id.all_phy_list)
    RecyclerView mPhysicsList ;


    //Chemistry Recycelr view

    @BindView(R.id.all_chem_list) RecyclerView mChemList;

    //Maths
    @BindView(R.id.all_maths_list) RecyclerView mMathsList;

    //Optional Text and list

    @BindView(R.id.opt_subject)
    HelviticaLight mSubject;

    @BindView(R.id.all_opt_list) RecyclerView mOptList;
*/

    //Network Relate Code


    ErrorHandler errorHandler  = null;
    TestListProvider12 testListProvider = null;
    DataStore mStore = null;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alltest_12,container,false);
        ButterKnife.bind(this,v);




        return v;
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
