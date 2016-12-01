package com.elf.elfstudent.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.elf.elfstudent.CustomUI.UbuntuRegular;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.FontCache;
import com.elf.elfstudent.Utils.ScreenUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 18/10/16.
 *
 */


public class AboutUsActivity extends AppCompatActivity {

    @BindView(R.id.moc_text)
    UbuntuRegular moc_text;
    @BindView(R.id.top_card)
    CardView topCard;
    @BindView(R.id.love_text)
    TextView loveText;
    @BindView(R.id.bottom_card)
    CardView bottomCard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us_activity);
        ButterKnife.bind(this);
        performIntroAnimations();
        loveText.setTypeface(FontCache.getTypeface("fonts/grand_hotel.otf",getApplicationContext()));


//        ButterKnife.bind(this);
    }

    private void performIntroAnimations() {
        moc_text.setTranslationY(ScreenUtil.getScreenHeight(this));
        loveText.setAlpha(0f);
        bottomCard.setTranslationY(ScreenUtil.getScreenHeight(this));
        moc_text.animate()
                .translationY(0)
                .setDuration(600)
                .setInterpolator(new DecelerateInterpolator())
                .start();
        loveText.animate()
                .alpha(1f)
                .setStartDelay(700)
                 .setDuration(600)
                .start();
        bottomCard.animate().translationY(0)
                .setDuration(600)
                .setStartDelay(1500)
                .start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
