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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 18/10/16.
 */
public class TestLessonAdapter  extends RecyclerView.Adapter<TestLessonAdapter.LessonTextHolder> {



    private Context mContext;

    private OnTestViewClick mCallback;



    /* the construct for this Adapter , takes three paremete context,
    callback (usually a fragment which is implementing the interface
    and dataset */
    public TestLessonAdapter(Context mContext,    OnTestViewClick mListener){
        this.mContext = mContext;
        this.mCallback = mListener;
        addDataset();

        Log.d("TEST ADAPTER", "TestLessonAdapter: ");
    }

    private void addDataset() {
        mlessonames = new ArrayList<>(5);
        mlessonames.add("Matrix Operation");
        mlessonames.add("Kincematics");
        mlessonames.add("Vector Algebra");
        mlessonames.add("Organic Chemistry");
        mlessonames.add("Motion and Gravity");
        mlessonames.add("Linear Algebra");
        mlessonames.add("Laser properties");
        mlessonames.add("Light and Energy");
        mlessonames.add("Quantum computing");
        mlessonames.add("VR overview");
        mlessonames.add("Fundamental of Computing");
        mlessonames.add("Dynamics");
        mlessonames.add("Thirukural");
        mlessonames.add("Thermal Properties of solids");
        mlessonames.add("Fluid Dynamics");
    }


    private List<String> mlessonames;


    public void setmCallback(OnTestViewClick mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public LessonTextHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(mContext).inflate(R.layout.all_test_item_row,parent,false);
        Log.d("TESTADAPTER", "onCreateViewHolder: ");
        return new LessonTextHolder(v);
    }


    /*The view binding method called for Recycler adapter
    *
    * Set Transtiotn NAme here for views
    * for shared Elemnt transtion
    * onclick is called from here
    *  so as to get the complete holder object itself
    *
    *
    * */

    @Override
    public void onBindViewHolder(final LessonTextHolder holder,  int position) {

        holder.mText.setText(mlessonames.get(position));

        //set the transition Name
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            String bgTansName = "card_bg_" + position;
            String titleTransName = "card_text" + position;
            //assign background Transition Name for Content Root
            holder.getViewRoot().setTransitionName(bgTansName);

            // Assign Textview Transition name
            holder.getmText().setTransitionName(titleTransName);
        }

        //click listener for the card
        holder.getViewRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallback != null){
                    mCallback.onTestCardClick(holder,holder.getAdapterPosition());
                }
            }
        });

    }






    @Override
    public int getItemCount() {
        return mlessonames.size();
    }

    public class LessonTextHolder extends RecyclerView.ViewHolder {
        public HelviticaLight mText;

        public CardView mTestItem_root;

        public LessonTextHolder(final View itemView) {
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
    public interface OnTestViewClick{
        void onTestCardClick(LessonTextHolder holder, int position);
    }
}

