package com.elf.elfstudent.Fragments;

import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.elf.elfstudent.Activities.TestCompletedActivity;
import com.elf.elfstudent.CustomUI.HelviticaLight;
import com.elf.elfstudent.CustomUI.QucikSand;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.Network.JsonProcessors.TestOverviewProvider;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.BundleKey;
import com.elf.elfstudent.Utils.ScreenUtil;
import com.elf.elfstudent.Utils.SubjectBigImage;
import com.squareup.picasso.Picasso;

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

    private static final String OVERIVEW_URL = "http://elfanalysis.net/elf_ws.svc/GetTestOverview";


    DataStore mStore = null;
    @BindView(R.id.change_test_overall_root)
    FrameLayout changeableRoot;
    @BindView(R.id.test_overall_img)
    ImageView testImg;
    private Context mContext = null;
    AppRequestQueue mRequestQueue = null;
    ErrorHandler errorHandler = null;
    TestOverviewProvider testOverviewProvider = null;
    private String studentId = null;
    private String testID = null;
    private String subjectId = null;

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
        } catch (Exception e) {
            throw new NullPointerException("Context cannot be NUll");
        }

        mStore = DataStore.getStorageInstance(mContext);
        mRequestQueue = AppRequestQueue.getInstance(mContext.getApplicationContext());
        errorHandler = new ErrorHandler(this);
        testOverviewProvider = new TestOverviewProvider(this);

        if (getArguments() != null) {
            testID = getArguments().getString(BundleKey.TEST_ID);
            subjectId = getArguments().getString(BundleKey.SUBJECT_ID);
        }

        Log.d(TAG, "onCreateView: subject iD "+subjectId);
        Picasso.with(mContext).setLoggingEnabled(true);
        Picasso.with(mContext)
                        .load(SubjectBigImage.getBIgSubjectImage(subjectId))

                        .into(testImg);




        studentId = mStore.getStudentId();

        //Make Requeat with student ID and Test ID

        if (studentId != null && testID != null) {
            getOverviewReportFor(studentId, testID);
        }


        return v;

    }

    private void getOverviewReportFor(String s, String testID) {
        JSONObject mObject = null;
        try {
            mObject = new JSONObject();
            mObject.put("StudentId", s);
            mObject.put("TestId", testID);
        } catch (Exception e) {
            Log.d(TAG, "getOverviewReportFor: ");
        }


        Log.d(TAG, "aking Request");
        JsonArrayRequest mREJsonArrayRequest = new JsonArrayRequest(Request.Method.POST, OVERIVEW_URL, mObject, testOverviewProvider, errorHandler);
        if (mRequestQueue != null) {
            mRequestQueue.addToRequestQue(mREJsonArrayRequest);
        } else {
            throw new NullPointerException("Request Queue is null");
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
        try {

            changeableRoot.removeAllViews();
            View v = View.inflate(mContext.getApplicationContext(), R.layout.test_overall, changeableRoot);
            QucikSand SubjectDesc = (QucikSand) v.findViewById(R.id.test_comp_test_desc);
            HelviticaLight subId = (HelviticaLight) v.findViewById(R.id.test_comp_test_sub);
            HelviticaLight correctAnswers = (HelviticaLight) v.findViewById(R.id.right_no);
            HelviticaLight totalAnswer = (HelviticaLight) v.findViewById(R.id.textView19);

            RunAnimations(SubjectDesc, correctAnswers, totalAnswer);

            SubjectDesc.setText(TestDesc);
            subId.setText(SubjectName);
            correctAnswers.setText(No_ofRight);
            totalAnswer.setText(totalQues);
        } catch (Exception e) {
            Log.d(TAG, "ShowOverview: Exception in Test" +e.getLocalizedMessage());
        }


    }

    private void RunAnimations(final QucikSand subjectDesc, final HelviticaLight correctAnswers, final HelviticaLight totalAnswer) {

        //set Y values
        subjectDesc.setTranslationY(-ScreenUtil.getScreenHeight(mContext.getApplicationContext()));
        totalAnswer.setTranslationX(-ScreenUtil.getScreenWidth(mContext.getApplicationContext()));
        correctAnswers.setTranslationX(ScreenUtil.getScreenWidth(mContext.getApplicationContext()));

        subjectDesc.animate().translationY(0).setDuration(600).setInterpolator(new DecelerateInterpolator(2f))
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        //Display total Answer animations

                        try {

                            correctAnswers.setVisibility(View.VISIBLE);
                            totalAnswer.setVisibility(View.VISIBLE);
                            correctAnswers.animate().translationX(0).setDuration(400).setInterpolator(new OvershootInterpolator(1.f)).start();
                            totalAnswer.animate().translationX(0).setDuration(400).setInterpolator(new OvershootInterpolator(1.f)).start();
                        } catch (Exception e) {
                            Log.d(TAG, "Exception");
                        }
                    }

                    @Override
                    public void onAnimationStart(Animator animator) {
                        subjectDesc.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                }).start();


    }

    @Override
    public void noData() {

        Log.d(TAG, "noData: ");

    }
}
