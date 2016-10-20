package com.elf.elfstudent.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elf.elfstudent.Activities.SingleSubjectReportActivity;
import com.elf.elfstudent.Adapters.ReportLessonAdapter;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.BundleKey;

import java.io.FileDescriptor;
import java.io.PrintWriter;

import butterknife.BindView;

/**
 * Created by nandhu on 20/10/16.
 * The Fragment which is provided by {@link com.elf.elfstudent.Adapters.ViewPagerAdapters.ReportPagerAdapter}
 *
 * Gets The OVerall Percentage , lesson List and shows them in lIst
 * clicking on List , will show them topic wise
 */
public class ReportFragment extends Fragment implements ReportLessonAdapter.LessonClickCallbacks{




    @BindView(R.id.report_frag_card)
    CardView mTopRoot;

    @BindView(R.id.report_frag_list)
    RecyclerView mList;

    String overall = "";
//    List<T> mLessonNames;

    ReportLessonAdapter mAdapter = null;

    String subjecId = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.report_fragment,container,false);

        if (getArguments()!= null){
            //overall = getArguments().get
            //Lesson Names  = getArguments.getP
            //subjecId =

        }



        return mView;
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
    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        super.dump(prefix, fd, writer, args);
    }

    @Override
    public LayoutInflater getLayoutInflater(Bundle savedInstanceState) {
        return super.getLayoutInflater(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
    public void ShowLessonReportFor(int position, ReportLessonAdapter.LessonView itemView) {





        final Intent i = new Intent(getActivity(), SingleSubjectReportActivity.class);
        //i.putExtra(BundleKey.LESSON_ID,mLessonList.get(position).getLessonId());
        i.putExtra(BundleKey.SUBJECT_ID,subjecId);
        startActivity(i);
    }
}
