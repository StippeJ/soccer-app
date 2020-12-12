package com.example.group04.soccerapp;

import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import com.example.group04.soccerapp.ui.overview.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class OverviewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }
}