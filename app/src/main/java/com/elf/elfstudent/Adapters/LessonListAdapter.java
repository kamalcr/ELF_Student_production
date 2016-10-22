package com.elf.elfstudent.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elf.elfstudent.CustomUI.HelviticaLight;
import com.elf.elfstudent.R;
import com.elf.elfstudent.model.Lesson;
import com.elf.elfstudent.model.Topic;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 22/10/16.
 * The Adapter to be used in {@link com.elf.elfstudent.Activities.SubjectViewActivity}
 *
 * provides list of Lessons for subject ,
 * its root is from {@link com.elf.elfstudent.Activities.HomeActivity}
 *  it is <b>not</b> used in Report Page
 *  No Clicking this only showing
 *
 */
public class LessonListAdapter  extends RecyclerView.Adapter<LessonListAdapter.LessonHolder>{



    private android.content.Context Context = null;
    private List<Lesson> lessonList = null;
    LayoutInflater inflater = null;

    public LessonListAdapter(Context mContext, List<Lesson> mList) {
        this.Context = mContext;
        this.lessonList = mList;
    }
    @Override
    public LessonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null){
            inflater = LayoutInflater.from(Context);

        }
        return new LessonHolder(inflater.inflate(R.layout.lesson_item_row,parent,false));
    }

    @Override
    public void onBindViewHolder(LessonHolder holder, int position) {
            holder.mLessonName.setText(lessonList.get(position).getmLessonName());
           holder.mGrowthPercentage.setText(lessonList.get(position).getmGrowthPercentage());

    }

    @Override
    public int getItemCount() {
        return lessonList.size();
    }

    public static class LessonHolder extends RecyclerView.ViewHolder{


        @BindView(R.id.lesson_name_item)
        HelviticaLight mLessonName;

        @BindView(R.id.lesson_growth) HelviticaLight mGrowthPercentage;


        public LessonHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
