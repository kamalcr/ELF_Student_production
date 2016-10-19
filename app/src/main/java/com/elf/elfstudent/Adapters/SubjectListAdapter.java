package com.elf.elfstudent.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elf.elfstudent.R;
import com.elf.elfstudent.model.Lesson;

import java.util.List;

/**
 * Created by nandhu on 19/10/16.
 */
public class SubjectListAdapter  extends RecyclerView.Adapter<SubjectListAdapter.LessonHolder>{
    private List<Lesson> mList;
    private Context mContext;
    LayoutInflater inflater = null;
    public SubjectListAdapter(Context context, List<Lesson> mLessonList) {
        this.mContext = context;
        this.mList = mLessonList;
    }

    @Override
    public LessonHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (inflater == null){
            inflater = LayoutInflater.from(mContext);
        }
        return new LessonHolder(inflater.inflate(R.layout.lesson_item_row,parent,false));
    }

    @Override
    public void onBindViewHolder(LessonHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class LessonHolder extends RecyclerView.ViewHolder{

        public LessonHolder(View itemView) {
            super(itemView);
        }
    }
}
