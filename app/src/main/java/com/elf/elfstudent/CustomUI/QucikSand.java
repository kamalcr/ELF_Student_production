package com.elf.elfstudent.CustomUI;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.elf.elfstudent.Utils.FontCache;

/**
 * Created by nandhu on 27/10/16.
 *
 */

public class QucikSand extends TextView {
    public QucikSand(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public QucikSand(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public QucikSand(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);

    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fonts/quicksnad_regular.otf", context);
        setTypeface(customFont);
    }
}
