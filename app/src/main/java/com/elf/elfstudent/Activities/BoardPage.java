package com.elf.elfstudent.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.elf.elfstudent.Adapters.CustomSpinnerBoardAdapter;
import com.elf.elfstudent.Adapters.CustomSpinnerStateAdapter;
import com.elf.elfstudent.Adapters.SchoolListAdapter;
import com.elf.elfstudent.CustomUI.CustomAutcomplete;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.BundleKey;
import com.elf.elfstudent.model.BoardModel;
import com.elf.elfstudent.model.StateModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 17/10/16.
 *
 * The Activity Which gets the Institute page
 */
public class BoardPage extends AppCompatActivity {


    private static final String TAG = "ELF";
    //The Varaibles which Hold vAlues from Previous Activity
    private String studentName;
    private String studentEmail;
    private String studentPhoneNumber;
    private String studentPassword;

    //The Views Associated with this Activity
    @BindView(R.id.board_spinner)
    Spinner mBoardSpinner;

    //The state Spinner
    @BindView(R.id.ins_state_actext) Spinner mStateSpinner;

    //Toolbar
    @BindView(R.id.board_toolbar)
    Toolbar mToolbar;



    //Adapter Associated Variables
    //Board Spinner  ref's
    List<BoardModel> mTypeList = new ArrayList<>(3);
    CustomSpinnerBoardAdapter mBoardAdapter =null;

    //State Spinner Ref's
    List<StateModel> mStateList = new ArrayList<>(25);
    CustomSpinnerStateAdapter mStateAdapter = null;




    @BindView(R.id.ins_finish_button)
    Button mFinishButton;

    String boardId = null;
    String stateId= null;
//    SchoolListAdapter mSchoolAdapter= null;

    DataStore mStore = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instituteinfo);
        ButterKnife.bind(this);

        //Get the Values entered from Previous Page
        getValuesFromIntent();


        mStore = DataStore.getStorageInstance(getApplicationContext());

        //Prepare The Adapter for State and Board Spinner
        setUpSpinnerAdapters();

        setSupportActionBar(mToolbar);
        ActionBar ab  = getSupportActionBar();
        try{
            ab.setDisplayShowHomeEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
        }
        catch (Exception e) {
            Log.d(TAG, "onCreate: ");
        }


        mFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NextButtonClicked();
            }
        });




    }

    private void NextButtonClicked() {

        final Intent   i = new Intent(this,InstitutePage.class);
        i.putExtra(BundleKey.ARG_BOARD_ID,boardId);
        i.putExtra(BundleKey.ARG_STATE_ID,stateId);
        Log.d(TAG, "Moving to Ins PAge: Board ID "+boardId +" StateId "+stateId );
        startActivity(i);
    }

    /*This Method Prepares the populates the { @link StateModel } & { @link BoardModel }
    * in Hardcoded Way , only School List is Dynamic and that too is next page {@link InstitutePage }*/

    private void setUpSpinnerAdapters() {
        //state Spinners Values
        new Thread(new Runnable() {
            @Override
            public void run() {
                mStateList.add(new StateModel("Tamil Nadu","1"));
                mStateList.add(new StateModel("Kerala","2"));
                mStateList.add(new StateModel("Delhi","3"));
                mStateList.add(new StateModel("Maharastra","4"));
                mStateList.add(new StateModel("Andhra Pradesh","5"));
                mStateList.add(new StateModel("karnataka","5"));
                mStateList.add(new StateModel("Himachal Pradesh","5"));
                mStateList.add(new StateModel("Haryana","5"));
                mStateList.add(new StateModel("Bihar","5"));
                mStateList.add(new StateModel("Punjab","5"));
                mStateList.add(new StateModel("Chattisgarh","5"));
                mStateList.add(new StateModel("Jammmu & Kashmir","5"));
                mStateList.add(new StateModel("Uttar Pradesh","5"));
                mStateList.add(new StateModel("Sikkhim","5"));
                mStateList.add(new StateModel("Assam","5"));
                mStateList.add(new StateModel("Arunacha Pradesh","5"));
                mStateList.add(new StateModel("Telungana","5"));
                mStateList.add(new StateModel("Orissa","5"));
                mStateList.add(new StateModel("Rajasthan","5"));
                mStateList.add(new StateModel("Sikkhim","5"));
            }
        }).start();
        mStateAdapter = new CustomSpinnerStateAdapter(this,R.layout.custom_spinner,mStateList);

        // Board Adapter
        mTypeList.add(new BoardModel("CBSE","1"));
        mTypeList.add(new BoardModel("SAMACHEER","2"));
        mTypeList.add(new BoardModel("ICSE","3"));
        mBoardAdapter = new CustomSpinnerBoardAdapter(this,R.layout.custom_spinner,mTypeList);


        //The School adapter
    }

    private void getValuesFromIntent() {
        if (getIntent() != null){

            Log.d(TAG, "getValuesFromIntent:");
            studentName = getIntent().getStringExtra(BundleKey.ARG_USER_NAME_TAG);
//            studentEmail = getIntent().getStringExtra(BundleKey.ARG_EMAIL_ID_TAG);
            studentPhoneNumber = getIntent().getStringExtra(BundleKey.ARG_PHONE_NUMBER_TAG);
            studentPassword = getIntent().getStringExtra(BundleKey.ARG_PASWORD);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //setting State Adapter to spinner;
        if (mStateAdapter != null){
            mStateSpinner.setAdapter(mStateAdapter);
        }
        //similar;y for board

        if (mBoardAdapter != null){
            mBoardSpinner.setAdapter(mBoardAdapter);
        }


        //selected Listner for state
        mStateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                StateModel selected = (StateModel) adapterView.getItemAtPosition(i);
                Log.d(TAG, "State selected: saving to store "+selected.getStateName());
                stateId = selected.getStateId();
                mStore.setStateId(stateId);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });

        //selected listener for Board
        mBoardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                BoardModel model  = (BoardModel) adapterView.getItemAtPosition(i);
                Log.d(TAG, "Board Selected saving to store"+model.getName() );
                boardId = model.getBoardId();
                mStore.setBoardId(boardId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
