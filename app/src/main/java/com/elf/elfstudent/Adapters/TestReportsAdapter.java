package com.elf.elfstudent.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.elf.elfstudent.CustomUI.HelviticaLight;
import com.elf.elfstudent.CustomUI.HelviticaMedium;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.SubjectImage;
import com.elf.elfstudent.model.TestReportModel;

import junit.framework.Test;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 20/10/16.
 * used in {@link com.elf.elfstudent.Activities.TestReportsActivity}
 */
public class TestReportsAdapter  extends RecyclerView.Adapter<TestReportsAdapter.TestReportHolder>{



    private Context mContext = null;
    private List<TestReportModel> mTestList;
    LayoutInflater inflater = null;
    private TestReportCallbacks mCallback = null;
    public TestReportsAdapter(Context context , List<TestReportModel> mlist,TestReportCallbacks mCallback) {
        this.mContext  = context;
        this.mTestList = mlist;
        this.mCallback = mCallback;
    }

    @Override
    public TestReportHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       if (inflater== null){
           inflater = LayoutInflater.from(mContext);

       }

        return new TestReportHolder(inflater.inflate(R.layout.test_report_item_row,parent,false),mCallback);
    }

    @Override
    public void onBindViewHolder(final TestReportHolder holder, int position) {
        holder.mTestName.setText(mTestList.get(position).getDescription());

        String marks = mTestList.get(position).getOverAll() + "/20";

        holder.marksObtained.setText(marks);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.ShowTestReportFor(holder.getAdapterPosition(),holder,mTestList.get(holder.getAdapterPosition()).getSubjectId());
            }
        });


        holder.mSubjectName.setText(SubjectImage.getSubjectName(mTestList.get(position).getSubjectId()));
        holder.rewriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.reWriteTest(mTestList.get(holder.getAdapterPosition()).getTestId(),mTestList.get(holder.getAdapterPosition()).getSubjectId());
            }
        });
    }

    @Override
    public int getItemCount() {
       return mTestList.size();
    }

    public static class TestReportHolder extends RecyclerView.ViewHolder{



        @BindView(R.id.rewrite_button)
        Button rewriteButton;
        @BindView(R.id.test_item_overall)
        HelviticaMedium marksObtained;
        @BindView(R.id.test_report_item_test_desc) HelviticaLight mTestName;

        @BindView(R.id.test_report_item_sub_name) HelviticaLight mSubjectName;

        public TestReportHolder(View itemView, TestReportCallbacks mCallback) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface TestReportCallbacks{
        void ShowTestReportFor(int position,TestReportHolder holder,String subId);
        void reWriteTest(String testid,String subjectId);
    }
}
