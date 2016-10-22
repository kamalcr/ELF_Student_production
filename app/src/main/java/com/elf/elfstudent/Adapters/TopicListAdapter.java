package com.elf.elfstudent.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elf.elfstudent.CustomUI.HelviticaLight;
import com.elf.elfstudent.R;
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
    public TopicListAdapter(Context mContext, List<Topic> mList) {
        this.Context = mContext;
        this.topicList = mList;
    }

    @Override
    public TopicViewer onCreateViewHolder(ViewGroup parent, int viewType) {
      if (inflater == null){
          inflater = LayoutInflater.from(Context);

      }
        return new TopicViewer(inflater.inflate(R.layout.topic_item_row,parent,false));
    }

    @Override
    public void onBindViewHolder(TopicViewer holder, int position) {
        holder.TopicName.setText(topicList.get(position).getTopicName());
        holder.mPoints.setText(topicList.get(position).getPoints());


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
