package com.elf.elfstudent.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.elf.elfstudent.Activities.SingleSubjectReportActivity;
import com.elf.elfstudent.Adapters.ReportLessonAdapter;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.Network.JsonProcessors.LessonProvider;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.BundleKey;
import com.elf.elfstudent.Utils.RVdecorator;
import com.elf.elfstudent.Utils.ScreenUtil;
import com.elf.elfstudent.model.Lesson;
import com.elf.elfstudent.model.Topic;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.animation.EasingFunction;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.crash.internal.FirebaseCrashOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v4.app.ActivityOptionsCompat.makeSceneTransitionAnimation;
import static com.elf.elfstudent.Adapters.ReportLessonAdapter.*;

/**
 * Created by nandhu on 20/10/16.
 * The Fragment which is provided by {@link com.elf.elfstudent.Adapters.ViewPagerAdapters.ReportPagerAdapter}
 *
 * Gets The OVerall Percentage , lesson List and shows them in lIst
 * clicking on List , will show them topic wise
 */
public class ReportFragment extends Fragment implements LessonClickCallbacks, LessonProvider.SubjectLoaderCallback, ErrorHandler.ErrorHandlerCallbacks {


    private static final String TAG = "REPORT_FRAG";
    private static final String REPORT_URL = "http://www.hijazboutique.com/elf_ws.svc/GetLessionWiseReport";



    @BindView(R.id.report_frag_card)
    CardView mTopRoot;

    @BindView(R.id.report_frag_list)
    RecyclerView mList;

    //The Divider for Recyler view
    RVdecorator decorator  = null;


    ///THe pie chart

    @BindView(R.id.report_chart)
    PieChart mChart;
    //The Top Level Content Root



    String overall = "75";

    private List<Topic> mTopiclist = null;
//    List<T> mLessonNames;

    ReportLessonAdapter mAdapter = null;

    List<Lesson> mLessonList = null;

    String subjecId = null;
    DataStore mStore = null;

    JsonArrayRequest getLessonRequest = null;
    private String subjectName = null;
    String studentId = null;

    private AppRequestQueue mRequestQueue = null;
    private LessonProvider mLessonProvider = null;
    private ErrorHandler errorHandler = null;

    @BindView(R.id.report_progress)
    FrameLayout mLoadingLayout;

    @BindView(R.id.report_visisble_layout)
    FrameLayout    mVisibleLayout;





    private Context mContext = null;
    private int count = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.report_fragment, container, false);
        ButterKnife.bind(this, mView);

        if (mContext == null){
            mContext = getContext();
            if (mContext == null){
                mContext = getActivity().getApplicationContext();
            }
            else{
                throw new  NullPointerException("Context is null");
            }
        }

        if (getArguments() != null) {
            subjecId = getArguments().getString(BundleKey.SUBJECT_ID);
            subjectName = getArguments().getString(BundleKey.SUBJECT_NAME);

        }
        else{
            throw new NullPointerException("Arguments Cannot be null");
        }


        //Check whetther subject id and name is not null

        if (subjecId == null){
            throw new NullPointerException("Subject Id cannot be null");
        }


        //Initislize network Related codes

        mRequestQueue = AppRequestQueue.getInstance(mContext);
        mLessonProvider = new LessonProvider(this);
        errorHandler = new ErrorHandler(this);
        mStore = DataStore.getStorageInstance(mContext);

        if (mStore != null) {
            studentId = mStore.getStudentId();
        }

        if (studentId != null) {
            PrepareSubjectReportsFor(studentId,subjecId);

        }
        else{
            //NO request or Adapter is sent , display no data page
            try {
                mLoadingLayout.removeAllViews();;
                View v = LayoutInflater.from(getContext()).inflate(R.layout.no_internet,mLoadingLayout,true);


            }
            catch (Exception e ){
                FirebaseCrash.log("Adapter is not made , excepition in showing no data page");

            }
        }




//

        return mView;

    }







    private void PrepareSubjectReportsFor(String studentId, String subjecId) {
        JSONObject mObject = new JSONObject();
        try {

            mObject.put("studentId", studentId);
            mObject.put("subjectId", subjecId);
        } catch (Exception e) {
            FirebaseCrash.log("Exception in putting JSON oBject");
        }

        getLessonRequest = new JsonArrayRequest(Request.Method.POST, REPORT_URL, mObject, mLessonProvider, errorHandler);

        if (mRequestQueue != null) {
            mRequestQueue.addToRequestQue(getLessonRequest);

        }
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
    public void onStart() {
        super.onStart();
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.mContext = context;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        mContext =null;
        errorHandler = null;
        mLessonProvider = null;
        mStore = null;
        mRequestQueue = null;
        getLessonRequest = null;
        subjecId = null;
        subjectName = null;
    }

    @Override
    public void ShowLessonReportFor(int position, LessonView itemView) {


        Log.d("REport", "ShowLessonReportFor: ");
        final Intent i = new Intent(getActivity(), SingleSubjectReportActivity.class);
        //i.putExtra(BundleKey.LESSON_ID,mLessonList.get(position).getLessonId());

//        String LessonTransName = ViewCompat.getTransitionName(itemView.mLessonName);
//        String percentTransName = ViewCompat.getTransitionName(itemView.mGrowth);
        String laytrnas  =ViewCompat.getTransitionName(itemView.itemView);
        i.putExtra(BundleKey.SUBJECT_ID,subjecId);

        i.putExtra(BundleKey.ITEMVIEW,laytrnas);

        i.putExtra(BundleKey.LESSON_ID,mLessonList.get(position).getLessonId());
        i.putExtra(BundleKey.LESSON_NAME,mLessonList.get(position).getmLessonName());
        i.putExtra(BundleKey.PERCENTAGE,mLessonList.get(position).getmGrowthPercentage());
//        i.putExtra(BundleKey.LESSON_NAME_TRANS,LessonTransName);
//        i.putExtra(BundleKey.PERCENT_TRANS,percentTransName);
        //MAking pairs for many Share elements

        Pair<View, String> p2 = Pair.create((View) itemView.itemView, laytrnas);


        ActivityOptionsCompat options = makeSceneTransitionAnimation(getActivity(), p2);
        if (ScreenUtil.isAndroid5()){

            startActivity(i, options.toBundle());
        }else{
            startActivity(i);
        }
    }

    @Override
    public void setLessonList(List<Lesson> mLessons, int overall) {
        this.mLessonList = mLessons;




        if (mLoadingLayout.isShown()){

            mLoadingLayout.setVisibility(View.INVISIBLE);
        }
        mVisibleLayout.setVisibility(View.VISIBLE);



        try{

            //got the list from server , dispay it, after pie animations
            setPieChartValue(overall);
            decorator = new RVdecorator(ContextCompat.getDrawable(mContext,R.drawable.divider));
            mAdapter = new ReportLessonAdapter(getContext(),mLessons,this);
            mList.setLayoutManager(new LinearLayoutManager(mContext));
            mList.addItemDecoration(decorator);
            mList.setAdapter(mAdapter);

        }
        catch (Exception e ){
            FirebaseCrash.log("Exception in setting Adapter Report Fragment");
        }
    }


    /*seting pie chart values
    *
    *   As per example at {https://github.com/PhilJay/MPAndroidChart/blob/master/MPChartExample/src/com/xxmassdeveloper/mpchartexample/PieChartActivity.java}
    *
    *   Pie chart data is added in following mechanism
    *
    *  Chart---> piedata-->pieDataset--->entries--->pieEntry
    *
    *
    * */
    private void setPieChartValue(int overall) {

        int remaining  = 100 - overall;
        mChart.setCenterText("Overall completion");
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.

            entries.add(new PieEntry(overall));
            entries.add(new PieEntry(remaining));

            PieDataSet dataSet = new PieDataSet(entries, "Overall Completion");
            dataSet.setSliceSpace(1f);
            dataSet.setSelectionShift(5f);
            ArrayList<Integer> colors = new ArrayList<Integer>();
            colors.add((Color.parseColor("#F20654")));
            colors.add((Color.parseColor("#36353F")));
            dataSet.setColors(colors);

            PieData data = new PieData(dataSet);
            data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(11f);
            data.setValueTextColor(Color.WHITE);
             mChart.setData(data);
            mChart.animateY(400, Easing.EasingOption.EaseOutSine);

//            data.setValueTypeface(mTfLight);

    }

    @Override
    public void noLesson() {

        FirebaseCrash.log("No Report in Report Fragment , no lessons");
        mLoadingLayout.removeAllViews();
        View v = View.inflate(mContext,R.layout.no_data,mLoadingLayout);
    }

    @Override
    public void TimeoutError() {


        if (!(count>2)){
            //count in not greater , perform request again
            if (mRequestQueue != null){
                if (getLessonRequest != null){

                    mRequestQueue.addToRequestQue(getLessonRequest);
                }
            }
        }
        try {

            mLoadingLayout.removeAllViews();
            View v  = LayoutInflater.from(getContext()).inflate(R.layout.try_again_layout,mLoadingLayout,true);
            TextView t = (TextView) v.findViewById(R.id.try_again_text);
            t.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mRequestQueue != null){
                        mRequestQueue.addToRequestQue(getLessonRequest);
                    }
                }
            });
        }
        catch (Exception e ){
          FirebaseCrash.log("Error in setting view");
        }

    }

    @Override
    public void NetworkError() {
       try {
           mLoadingLayout.removeAllViews();;
           View v = LayoutInflater.from(getContext()).inflate(R.layout.no_internet,mLoadingLayout,true);


       }
       catch (Exception e ){

       }
    }



    @Override
    public void ServerError() {
        FirebaseCrash.log("server error , top priority to look into");
    }
}
