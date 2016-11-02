package com.elf.elfstudent.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elf.elfstudent.CustomUI.HelviticaLight;
import com.elf.elfstudent.CustomUI.QucikSand;
import com.elf.elfstudent.R;
import com.elf.elfstudent.model.Answers;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 30/10/16.
 * The Adapter which is used in {@link com.elf.elfstudent.Fragments.TestComQuesListFrag} ' s Recycler view
 *
 * Shows a list of Questions _shows in green is right and red if wrong
 */
public class TestCompQuestionsAdapter extends RecyclerView.Adapter<TestCompQuestionsAdapter.TestQuestionHolder>{



    private List<Answers> mList;
    private Context mContext = null;

    public TestCompQuestionsAdapter(List<Answers> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public TestQuestionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TestQuestionHolder(LayoutInflater.from(mContext).inflate(R.layout.test_quest_item_row,parent,false));
    }

    @Override
    public void onBindViewHolder(TestQuestionHolder holder, int position) {
          holder.mQuestion.setText(mList.get(position).getmQuestion());
        holder.correctOption.setText(mList.get(position).getCorrectoption());

        if (mList.get(position).getAnswerStatus().equals("Correct")){
            holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.material_green500));
        }else{
            holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.material_red300));
        }
    }

    @Override
    public int getItemCount()
    {
        Log.d("Report Adapter", "getItemCount: size"+mList.size());
        return mList.size();
    }


    public static class TestQuestionHolder extends RecyclerView.ViewHolder{


        public @BindView(R.id.item_question)
        HelviticaLight mQuestion;
        @BindView(R.id.correct_option)
        QucikSand correctOption;
        public TestQuestionHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
