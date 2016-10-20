package com.elf.elfstudent.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.elf.elfstudent.Adapters.TestLessonAdapter;
import com.elf.elfstudent.CustomUI.HelviticaMedium;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.R;
import com.elf.elfstudent.model.AllTestModels;

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
public class AllTestFragment extends Fragment implements  TestLessonAdapter.OnTestViewClick, ErrorHandler.ErrorHandlerCallbacks {

    @BindView(R.id.all_phy_text)
    HelviticaMedium mPhyText;
    @BindView(R.id.all_chem)
    HelviticaMedium mChemText;
    @BindView(R.id.all_maths_text)
    HelviticaMedium mMathsText;
    @BindView(R.id.all_maths_list)
    RecyclerView mMathsList;
    @BindView(R.id.all_chem_list)
    RecyclerView mChemList;
    private View mView;

    @BindView(R.id.all_phy_list)
    RecyclerView mPhysList;

    //the url for this link
    private static String URL = "http://www.hijazboutique.com/elf_ws.svc/GetPendingTests";

    //the Request Queue for this body
    private AppRequestQueue mRequestQue;

    private static final String TAG = "ALL TEST";





    //Error handler
    ErrorHandler errorHandler;


    private boolean adapterSet = false;
    LinearLayoutManager mLayoutManager = null;

    TestLessonAdapter mAdapter ;



    DataStore mStore = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


       //get the Student Details for making Reuqest
        mStore = DataStore.getStorageInstance(getContext());
        //get Student id
        //get tests to write for that student .. it is overall
        mRequestQue = AppRequestQueue.getInstance(getContext());
        String mStdId = mStore.getStudentId();

        errorHandler = new ErrorHandler(this);
        prepareTests(mStdId);



        mAdapter  = new TestLessonAdapter(getContext(),this);
        mAdapter.setmCallback(this);
        //prepare adapter for writng tests

    }

    private void prepareTests(String mStdId) {
        JSONObject object = new JSONObject();

//        // TODO: 23/8/16 add dynamic student id
        try {
            object.put("StudentId", "1");
            object.put("Type", "All");


        } catch (Exception e) {
            Log.d(TAG, "prepareTests:  exception");

        }

        // make request with that body

        final JsonArrayRequest mReq = new JsonArrayRequest(Request.Method.POST, URL, object,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //build objects show it
                        processResponse(response);
                    }
                }, errorHandler);


        mRequestQue.addToRequestQue(mReq);
    }

    private void processResponse(JSONArray response) {
        int count = response.length();
        Log.d(TAG, "onResponse: count " + count);
        ArrayList<AllTestModels> mTestList = new ArrayList<>(count);

        JSONObject mobject = null;
        try {
            for (int i = 0; i < count; i++) {
                mobject = response.getJSONObject(0);
                mTestList.add(new AllTestModels(mobject.getString("TestId"),
                        mobject.getString("Description")
                        , mobject.getString("SubjectName"),
                        mobject.getString("SubjectId")));

            }
            prepareAdapter(mTestList);
        } catch (Exception e) {
            Log.d(TAG, "Exception");
        }
    }


    private void prepareAdapter(List<AllTestModels> mTestList) {




    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.all_test_fragment, container, false);
        ButterKnife.bind(this, mView);

        //setting layout Manager for Adapter
        Log.d(TAG, "onCreateView: ");


        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager mManager2  = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager mManager3  = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        mPhysList.setLayoutManager(mLinearLayoutManager);
        mChemList.setLayoutManager(mManager2);
        mMathsList.setLayoutManager(mManager3);


        mPhysList.setAdapter(mAdapter);
        mChemList.setAdapter(mAdapter);
        mMathsList.setAdapter(mAdapter);



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


    /*    TestViewFragment mFragment = TestViewFragment.newInstance();
        String textTransname = "";
        String cardTranstionName = "";
        String Title = (String) holder.getmText().getText();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            textTransname = holder.getTextTransitionName();
            cardTranstionName = holder.geCardTransitionName();
            Log.d(TAG, "Text Transition Name "+textTransname);
            Log.d(TAG, "Card Transition Name "+cardTranstionName);

            //first fragment exit transition

//              second Fragment enter transition
            mFragment.setSharedElementEnterTransition(new DetailsTransition());
            mFragment.setEnterTransition(new Slide());
//
            mFragment.setSharedElementReturnTransition(new DetailsTransition());


        }
        Bundle b  = new Bundle();
        b.putString(BundleKey.TEXT_TRANS_NAME,textTransname);
        b.putString(BundleKey.VIEW_ROOT,cardTranstionName);
        b.putString(BundleKey.TEST_ID, (String) holder.getmText().getText());
        b.putString(BundleKey.SUBJECT_ID,Title);
        mFragment.setArguments(b);


        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addSharedElement(holder.mText,textTransname)
                .replace(R.id.frag_holder,mFragment)
                .addToBackStack("TestVIEW")
                .commit();

*/



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
}
