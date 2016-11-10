package com.elf.elfstudent.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.elf.elfstudent.Adapters.NotificationPagerAdapter;
import com.elf.elfstudent.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 10/11/16.
 *
 */

public class NotificationsAcitivtity extends AppCompatActivity {


    @BindView(R.id.noti_tab)
    TabLayout mTabLayout;
    @BindView(R.id.noti_pager)
    ViewPager mPager;



    //The Pager Adapter
    private NotificationPagerAdapter mAdapter = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications_activity);
        ButterKnife.bind(this);

    }
}
