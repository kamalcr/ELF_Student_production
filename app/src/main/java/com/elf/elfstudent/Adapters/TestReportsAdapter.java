package com.elf.elfstudent.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elf.elfstudent.CustomUI.HelviticaLight;
import com.elf.elfstudent.CustomUI.HelviticaMedium;
import com.elf.elfstudent.R;
import com.elf.elfstudent.model.TestReportModel;

import junit.framework.Test;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 20/10/16.
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
        holder.mTestName.setText(mTestList.get(position).getSubjectName());
        holder.mSubjectName.setText(mTestList.get(position).getSubjectName());
        holder.marksObtained.setText(mTestList.get(position).getOverAll());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.ShowTestReportFor(holder.getAdapterPosition(),holder);
            }
        });
    }

    @Override
    public int getItemCount() {
       return mTestList.size();
    }

    public static class TestReportHolder extends RecyclerView.ViewHolder{



        //The Subject Name
        @BindView(R.id.test_report_item_sub_name)
        HelviticaLight mSubjectName;
        @BindView(R.id.test_item_overall)
        HelviticaMedium marksObtained;
        @BindView(R.id.test_report_item_test_desc) HelviticaLight mTestName;

        public TestReportHolder(View itemView, TestReportCallbacks mCallback) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface TestReportCallbacks{
        void ShowTestReportFor(int position,TestReportHolder holder);
    }
}
