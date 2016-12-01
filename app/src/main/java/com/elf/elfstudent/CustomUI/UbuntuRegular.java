package com.elf.elfstudent.CustomUI;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.elf.elfstudent.Utils.FontCache;

/**
 * Created by nandhu on 1/12/16.
 *
 */

public class UbuntuRegular extends TextView {
    public UbuntuRegular(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public UbuntuRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public UbuntuRegular(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);

    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fonts/ubuntu_reg.ttf", context);
        setTypeface(customFont);
    }
}
