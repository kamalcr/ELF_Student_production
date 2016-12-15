package com.elf.elfstudent.Adapters;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elf.elfstudent.CustomUI.HelviticaLight;
import com.elf.elfstudent.R;
import com.elf.elfstudent.model.AllTestModels;

import java.util.List;

/**
 * Created by nandhu on 7/11/16.
 *
 */
public class OptionalAdapter  extends RecyclerView.Adapter<OptionalAdapter.TestHolder> {



    private OptionalAdapter.OptionalCallback mCallback = null;
    private List<AllTestModels> mquestionList = null;
    private Context mContext = null;

    public OptionalAdapter( OptionalAdapter.OptionalCallback mCallback, List<AllTestModels> mquestionList, Context mContext) {
        this.mCallback = mCallback;
        this.mquestionList = mquestionList;
        this.mContext = mContext;
    }

    @Override
    public OptionalAdapter.TestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(mContext).inflate(R.layout.all_test_item_row,parent,false);
        Log.d("TESTADAPTER", "onCreateViewHolder: ");
        return new OptionalAdapter.TestHolder(v);
    }

    @Override
    public void onBindViewHolder(final OptionalAdapter.TestHolder holder, int position) {
        holder.mText.setText(mquestionList.get(position).getmTestDetail());

        //set the transition Name
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {


            holder.getViewRoot().setTransitionName("soc_card_"+String.valueOf(position));

            // Assign Textview Transition name
            holder.getmText().setTransitionName("soc_text_"+String.valueOf(position));
        }

        //click listener for the card
        holder.getViewRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallback != null){
                    mCallback.optionalTestClicked(holder,holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mquestionList.size();
    }

    public class TestHolder extends RecyclerView.ViewHolder {
        public HelviticaLight mText;

        public CardView mTestItem_root;

        public TestHolder(final View itemView) {
            super(itemView);
            //The root Layout
            mTestItem_root = (CardView) itemView;

            //The Test Text
            mText = (HelviticaLight) itemView.findViewById(R.id.all_test_text);


            //assign on click listener

        }

        public View getViewRoot() {
            return mTestItem_root;
        }


        public TextView getmText() {
            return mText;
        }

        public String getTextTransitionName() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                return getmText().getTransitionName();
            }
            return "null";
        }

        public String geCardTransitionName() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                return getViewRoot().getTransitionName();
            }
            return " ";
        }

    }

    public interface OptionalCallback{
        void optionalTestClicked(OptionalAdapter.TestHolder holder, int position);
    }
}