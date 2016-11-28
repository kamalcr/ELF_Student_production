package com.elf.elfstudent.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.elf.elfstudent.CustomUI.HelviticaLight;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.ScreenUtil;
import com.elf.elfstudent.model.Topic;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 20/10/16.
 *
 * The Topic List Adpater used in {@link com.elf.elfstudent.Activities.SingleSubjectReportActivity}
 */
public class  TopicListAdapter extends RecyclerView.Adapter<TopicListAdapter.TopicViewer> {



    private Context Context = null;
    private List<Topic> topicList = null;
    LayoutInflater inflater = null;
    private int Animated_item_count = 0;
    private int last_pos = -1 ;

    public TopicListAdapter(Context mContext, List<Topic> mList) {
        this.Context = mContext;
        this.topicList = mList;
        Animated_item_count = mList.size();
    }

    @Override
    public TopicViewer onCreateViewHolder(ViewGroup parent, int viewType) {
      if (inflater == null){
          inflater = LayoutInflater.from(Context);

      }
        return new TopicViewer(inflater.inflate(R.layout.topic_item_row,parent,false));
    }

    private void runEnterAnimations(TopicViewer holder, int position) {
        Log.d("Animation",""+position);

        if (position >= Animated_item_count){
            Log.d("Animation","postion one");
            return;
        }



        if (position>last_pos){

            Log.d("Animation","inside if");
            last_pos=position;
            holder.itemView.setTranslationY(ScreenUtil.getScreenHeight(Context));
            holder.itemView.setScaleX(0.2f);

            holder.itemView.animate().translationY(0)
                    .scaleX(1)

                    .setInterpolator(new DecelerateInterpolator(2f))
                    .setDuration(600)
                    .start();
        }
    }
    @Override
    public void onBindViewHolder(TopicViewer holder, int position) {
        runEnterAnimations(holder,position);
        holder.TopicName.setText(topicList.get(position).getTopicName());
        holder.mPoints.setText(String.valueOf((int)Float.parseFloat(topicList.get(position).getPoints())));


    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }

    public static class TopicViewer extends RecyclerView.ViewHolder{


        //Topic Name
        @BindView(R.id.topic_name_item)
        HelviticaLight TopicName;
        //Points in Topic
        @BindView(R.id.points_topic_item)
        TextView mPoints;
        public TopicViewer(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
