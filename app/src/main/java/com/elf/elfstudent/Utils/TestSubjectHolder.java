package com.elf.elfstudent.Utils;

import android.view.View;
import android.widget.ImageView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.elf.elfstudent.R;
import com.squareup.picasso.Picasso;

/**
 * Created by nandhu on 2/12/16.
 *
 */

public class TestSubjectHolder extends ParentViewHolder {
    /**
     * Default constructor.
     *
     * @param itemView The {@link View} being hosted in this ViewHolder
     */

   private     ImageView mImage;
    public TestSubjectHolder(View itemView) {
        super(itemView);
        mImage = (ImageView) itemView.findViewById(R.id.test_parent_image);
    }



}
