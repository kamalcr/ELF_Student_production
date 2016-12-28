package com.elf.elfstudent.CustomUI;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.elf.elfstudent.Utils.FontCache;

/**
 * Created by nandhu on 17/10/16.
 *
 */

public class HelviticaLight extends TextView {
    public HelviticaLight(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public HelviticaLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public HelviticaLight(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fonts/fall_Sky_cond.otf", context);
        setTypeface(customFont);
    }
}