
/*
 *
 *  * Copyright © 2016, Robosoft Technologies Pvt. Ltd
 *  * Written under contract by Robosoft Technologies Pvt. Ltd.
 *
 */
package com.air.movieapp.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.air.movieapp.R;
import com.air.movieapp.common.CommonUtils;
import com.air.movieapp.common.Constants;

import java.util.ArrayList;
import java.util.List;


/**
 * Main Container where all movie fragments are added
 */
public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,R.string.app_name,R.string.app_name);
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(getViewPagerAdapterWithAddedFragments());
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);
        setNavigationDrawer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_share:
                break;
            case R.id.menu_settings:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivityForResult(intent, Constants.SETTINGS_REQUEST_CODE);
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode == Constants.SETTINGS_REQUEST_CODE){

        }
    }

    private void setNavigationDrawer() {
        NavigationView navView = (NavigationView) findViewById(R.id.navigation_view);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int pos = 0;
                switch (menuItem.getItemId()) {
                    case R.id.item_top_rated:
                        pos = 0;
                        break;
                    case R.id.item_upcoming:
                        pos = 1;
                        break;
                    case R.id.item_popular:
                        pos = 2;
                        break;
                }
                mViewPager.setCurrentItem(pos, true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private ViewPagerAdapter getViewPagerAdapterWithAddedFragments() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        MovieFragment topratedMovieFragment = new MovieFragment();
        topratedMovieFragment.setArguments(CommonUtils.getBundleWithValue(0));
        adapter.addFragment(topratedMovieFragment, getString(R.string.top_rated));
        MovieFragment upcomingMovieFragment = new MovieFragment();
        upcomingMovieFragment.setArguments(CommonUtils.getBundleWithValue(1));
        adapter.addFragment(upcomingMovieFragment, getString(R.string.upcoming));
        MovieFragment popularMovieFragment = new MovieFragment();
        popularMovieFragment.setArguments(CommonUtils.getBundleWithValue(2));
        adapter.addFragment(popularMovieFragment, getString(R.string.popular));
        return adapter;
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private final List<MovieFragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public MovieFragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(MovieFragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
