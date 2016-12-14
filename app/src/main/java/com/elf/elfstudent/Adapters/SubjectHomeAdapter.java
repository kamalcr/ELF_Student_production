package com.elf.elfstudent.Adapters;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.elf.elfstudent.CustomUI.HelviticaLight;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.ScreenUtil;
import com.elf.elfstudent.Utils.SubjectImage;
import com.elf.elfstudent.model.SubjectModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 18/10/16.
 *
 */
public class SubjectHomeAdapter extends RecyclerView.Adapter<SubjectHomeAdapter.HomeHolder> {

private Context mContext;
private List<SubjectModel> mSubjectList;

    private String TAG = "HOMEADAPTER";
public onCardClick mcallback;
private int Animated_item_count;
        int last_pos = -1;


public SubjectHomeAdapter(Context context, List<SubjectModel> mSubjectList, onCardClick mlistener)  {
        this.mcallback = mlistener;
        this.mContext=context;
        this.mSubjectList=mSubjectList;
        Animated_item_count = mSubjectList.size();
        }

@Override
public HomeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeHolder(LayoutInflater.from(mContext).inflate(R.layout.home_item_row,parent,false),mcallback);
        }

@Override
public void onBindViewHolder(HomeHolder holder, int position) {



        runEnterAnimations(holder,position);
    try {

        Log.d(TAG, "onBindViewHolder: "+position);
        int submark = (int) Float.parseFloat(mSubjectList.get(position).getmPercentage());
        String percent = String.valueOf(submark)+"%";

        holder.mPercent.setText(String.valueOf(percent));
//        holder.mTitle.setText(mSubjectList.get(position).getmSubjectName());

        //setting Subject Image Based on Subject Id
        String subID = mSubjectList.get(position).getmSubjectId();
        Log.d(TAG, "geting image for SUbject id "+subID);


       Picasso.with(mContext.getApplicationContext())
                .load(SubjectImage.getSubjectImage(subID))
               .into(holder.mSubjectImage);
//        ViewCompat.setTransitionName(holder.mTitle, String.valueOf(position) + "_desc");
        ViewCompat.setTransitionName(holder.mPercent, String.valueOf(position) + "_sub");
        ViewCompat.setTransitionName(holder.mRootView,String.valueOf(position) + "_root");
        ViewCompat.setTransitionName(holder.mSubjectImage,String.valueOf(position) + "_img");
    }
    catch (Exception e ){
        Log.d(TAG, "onBindViewHolder: Exception is "+e.getLocalizedMessage());

    }


        }

/** This mehtod performs animations on Recvyler view adapter
 *
 * {@link com.elf.elfstudent.Activities.HomeActivity}
 * */

private void runEnterAnimations(HomeHolder holder, int position) {


        if (position >= Animated_item_count){
            //All positions have been animated
        return;
        }

        if (position>last_pos){
            //animate
                last_pos=position;
                holder.itemView.setTranslationY(ScreenUtil.getScreenHeight(mContext));
                holder.itemView.animate().translationY(0)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(1000)
                .start();
        }

   }

@Override
public int getItemCount() {
        return mSubjectList.size();
        }

    public void setNewSubjectList(List<SubjectModel> newSubjectList) {
        this.mSubjectList = newSubjectList;
        notifyDataSetChanged();
    }

    public static class HomeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    private static final String TAG = "HOME HOLDER";
//    @BindView(R.id.subject_title)
//    HelviticaLight mTitle;


    @BindView(R.id.home_holder_root)
    CardView mRootView;


    @BindView(R.id.percent) HelviticaLight mPercent;




    @BindView(R.id.home_card_sub_imageview) ImageView mSubjectImage;


    onCardClick mCall;
    public HomeHolder(View itemView, onCardClick mcallback) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.mCall  = mcallback;
        itemView.setOnClickListener(this);
//        mDetailsButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        mCall.InfoButtonClicked(getAdapterPosition(),itemView);
        }

}

   public interface onCardClick{
         void InfoButtonClicked(int position, View itemView);
         void DetailsButtonClicked(int position, View itemView);
}

}