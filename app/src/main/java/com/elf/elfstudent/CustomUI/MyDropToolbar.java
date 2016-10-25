package com.elf.elfstudent.CustomUI;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.elf.elfstudent.R;

/**
 * Created by nandhu on 25/10/16.
 *
 */

public class MyDropToolbar extends Toolbar {

    private ImageView mdropicon;
    private boolean isdropShowing = false;
    private HelviticaLight mTextview;
    View rootView  = getRootView();
    View mDropView ;
    public MyDropToolbar(Context context) {
        super(context);

    }

    public MyDropToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void showDropDown(){
//        mDropView =
    }
}
