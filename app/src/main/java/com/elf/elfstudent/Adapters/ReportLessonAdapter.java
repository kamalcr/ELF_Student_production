package com.elf.elfstudent.Adapters;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elf.elfstudent.CustomUI.HelviticaLight;
import com.elf.elfstudent.R;
import com.elf.elfstudent.model.Lesson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 20/10/16.
 */
public class  ReportLessonAdapter  extends RecyclerView.Adapter<ReportLessonAdapter.LessonView>{


    private Context mContext;
    private List<Lesson> mList;
    LayoutInflater inflater = null;
    private LessonClickCallbacks mCallback;
    public ReportLessonAdapter(Context context,List<Lesson> mLessonList,LessonClickCallbacks mCallback) {
        this.mList  = mLessonList;
        this.mContext = context;
        this.mCallback = mCallback;
    }

    @Override
    public LessonView onCreateViewHolder(ViewGroup parent, int viewType) {

        if (inflater== null){
            inflater = LayoutInflater.from(mContext);
        }
        return new LessonView(inflater.inflate(R.layout.report_lesson_item_row,parent,false),mCallback);
    }

    @Override
    public void onBindViewHolder(final LessonView holder, int position) {



        holder.mLessonName.setText(mList.get(position).getmLessonName());
        holder.mGrowth.setText(mList.get(position).getmGrowthPercentage());
        ViewCompat.setTransitionName(holder.mLessonName,String.valueOf(position)+"_lesson");
        ViewCompat.setTransitionName(holder.mGrowth,String.valueOf(position)+"_growth");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.ShowLessonReportFor(holder.getAdapterPosition(),holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class LessonView extends RecyclerView.ViewHolder{


       public  @BindView(R.id.report_frag_lesson_name)
        HelviticaLight mLessonName;

      public   @BindView(R.id.report_frag_lesson_growth) HelviticaLight mGrowth;

        public LessonView(View itemView, final LessonClickCallbacks mCallback) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }

   public interface LessonClickCallbacks{
        void ShowLessonReportFor(int position , LessonView itemView);
    }
}
