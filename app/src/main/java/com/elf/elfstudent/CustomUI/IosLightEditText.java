package com.elf.elfstudent.CustomUI;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import com.elf.elfstudent.Utils.FontCache;

/**
 * Created by nandhu on 17/10/16.
 */

public class IosLightEditText extends EditText {
    public IosLightEditText(Context context) {
        super(context);
        applyCustomFont(context);

    }

    public IosLightEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public IosLightEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }
    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fonts/ios_light.ttf.ttf", context);
        setTypeface(customFont);
    }
}
