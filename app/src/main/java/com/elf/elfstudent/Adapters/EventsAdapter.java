package com.elf.elfstudent.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.elf.elfstudent.R;
import com.elf.elfstudent.model.StudentEvent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 11/11/16.
 *
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsHolder>{


    private List<StudentEvent> mList = null;
    private Context mContext = null;
    private  OnCardClick mOnCardClick  = null;

    public EventsAdapter(Context  context, List<StudentEvent> mList, OnCardClick mOnCardClick) {
        this.mList = mList;
        this.mContext = context;
        this.mOnCardClick = mOnCardClick;
    }

    @Override
    public EventsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        // TODO: 11/11/16 based on View type show Diffrent Layouts
        View v = LayoutInflater.from(mContext).inflate(R.layout.student_events,parent,false);
        return new EventsHolder(v);

    }

    @Override
    public void onBindViewHolder(EventsHolder holder, int position) {
//        holder.mEvents.setText(mList.get(position));


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class EventsHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.event_text)
        TextView mEvents;
        @BindView(R.id.see_report_button)
        Button mSeeReportBt;

        public EventsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnCardClick{

        void showTestReports(String testId);
        void PublicTextClicked();

    }
}
