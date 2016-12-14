package com.elf.elfstudent.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.SubjectImage;
import com.elf.elfstudent.Utils.TestChildHolder;
import com.elf.elfstudent.Utils.TestSubjectHolder;
import com.elf.elfstudent.model.AllTestModels;
import com.elf.elfstudent.model.TestPageParentModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by nandhu on 2/12/16.
 *
 */

public class AllTestAdapter extends ExpandableRecyclerAdapter<TestSubjectHolder, TestChildHolder> {


    private static final String TAG = "AllTestAdapter";
    private Context mContext = null;
    private List<TestPageParentModel> mList;
    private LayoutInflater mInflator;
    private onTestClicked mCallback;




    /**
     * Primary constructor. Sets up {@link #mParentItemList} and {@link #mItemList}.
     * <p>
     * Changes to {@link #mParentItemList} should be made through add/remove methods in
     * {@link ExpandableRecyclerAdapter}
     *
     * @param parentItemList List of all {@link ParentListItem} objects to be
     *                       displayed in the RecyclerView that this
     *                       adapter is linked to
     */
    public AllTestAdapter(Context mContext, List<TestPageParentModel> parentItemList,onTestClicked mCallback) {
        super(parentItemList);
        this.mContext = mContext;
        Log.d(TAG, "AllTestAdapter: ");
        this.mList = parentItemList;
        mInflator = LayoutInflater.from(mContext);
        this.mCallback =mCallback;
    }

    @Override
    public TestSubjectHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
//        View parent_view = mInflator.inflate(R.layout.test_parent, parentViewGroup, false);

//        return new TestSubjectHolder(parent_view);
        View v  = LayoutInflater.from(mContext.getApplicationContext()).inflate(R.layout.test_parent,parentViewGroup,false);
        return new TestSubjectHolder(v);
    }

    @Override
    public TestChildHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
      View child_view = LayoutInflater.from(mContext.getApplicationContext()).inflate(R.layout.test_child, childViewGroup, false);

        return new TestChildHolder(child_view);
    }

    @Override
    public void onBindParentViewHolder(TestSubjectHolder parentViewHolder, int position, ParentListItem parentListItem) {
        ImageView img = (ImageView) parentViewHolder.itemView.findViewById(R.id.test_parent_image);
        Log.d(TAG, "onBindParentViewHolder: "+position);
        try {

            Picasso.with(mContext.getApplicationContext())
                    .load(SubjectImage.getSubjectImage(mList.get(position).getSubjectId()))
                    .resize(300,60)
                    .centerCrop()
                    .into(img);
        }
        catch (Exception e ){
            Log.d(TAG, "onBindParentViewHolder: exception "+e.getLocalizedMessage());
        }

    }



    @Override
    public void onBindChildViewHolder(TestChildHolder childViewHolder, int position, Object childListItem) {
        Log.d(TAG, "onBindChildViewHolder: "+position);
        AllTestModels testList = (AllTestModels) childListItem;

        childViewHolder.bind(testList,mCallback);

    }


    public interface  onTestClicked{
        public void showTestWriteaPageFor(String TestId,String subjectId);

    }
}
