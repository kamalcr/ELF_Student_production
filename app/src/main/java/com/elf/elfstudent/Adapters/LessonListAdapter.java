package com.elf.elfstudent.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.elf.elfstudent.CustomUI.HelviticaLight;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.ScreenUtil;
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
    private int Animated_item_count = 0;
    private int last_pos = -1;
    private int screenHeight = 0;
    private int screenWidth = 0;

    public LessonListAdapter(Context mContext, List<Lesson> mList) {
        this.Context = mContext;
        this.lessonList = mList;
        Animated_item_count = mList.size();
        screenHeight = ScreenUtil.getScreenHeight(mContext);
        screenWidth  = ScreenUtil.getScreenWidth(mContext);
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
            runEnterAnimations(holder,position);
            holder.mLessonName.setText(lessonList.get(position).getmLessonName());
           holder.mGrowthPercentage.setText(lessonList.get(position).getmGrowthPercentage());

    }
    private void runEnterAnimations(LessonHolder holder, int position) {

        if (position >= Animated_item_count){
            return;
        }

        if (position>last_pos){

            last_pos=position;
            holder.itemView.setScaleY(0.2f);
            holder.itemView.setScaleX(0.2f);

            holder.itemView.animate().scaleY(1)
                    .scaleX(1)
                    .setInterpolator(new DecelerateInterpolator(2f))
                    .setDuration(600)
                    .start();
        }
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
