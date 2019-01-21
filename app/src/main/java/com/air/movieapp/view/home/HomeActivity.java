
/*
 *
 *  * Copyright © 2016, Robosoft Technologies Pvt. Ltd
 *  * Written under contract by Robosoft Technologies Pvt. Ltd.
 *
 */
package com.air.movieapp.view.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.air.movieapp.MovieApplication;
import com.air.movieapp.R;
import com.air.movieapp.common.CommonUtils;
import com.air.movieapp.common.Constants;
import com.air.movieapp.databinding.ActivityMainBinding;
import com.air.movieapp.view.SettingsActivity;
import com.air.movieapp.view.base.BaseActivity;
import com.air.movieapp.view.movielist.MovieListFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * Main Container where all movie fragments are added
 */
public class HomeActivity extends BaseActivity {

    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private ActivityMainBinding mActivityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(mActivityMainBinding.toolbar);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mActivityMainBinding.drawerLayout,
                mActivityMainBinding.toolbar,R.string.app_name,R.string.app_name);
        mActivityMainBinding.drawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();
        mActivityMainBinding.viewpager.setOffscreenPageLimit(3);
        setAdapterToViewPager(mActivityMainBinding.viewpager, getSupportFragmentManager());
        mActivityMainBinding.tabs.setupWithViewPager(mActivityMainBinding.viewpager);
        setNavigationDrawer();
    }

    public void setAdapterToViewPager(ViewPager mViewPager, FragmentManager fragmentManager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(fragmentManager);
        MovieListFragment topratedMovieFragment = new MovieListFragment();
        topratedMovieFragment.setArguments(CommonUtils.getBundleWithValue(0));
        adapter.addFragment(topratedMovieFragment, getString(R.string.top_rated));
        MovieListFragment upcomingMovieFragment = new MovieListFragment();
        upcomingMovieFragment.setArguments(CommonUtils.getBundleWithValue(1));
        adapter.addFragment(upcomingMovieFragment, getString(R.string.upcoming));
        MovieListFragment popularMovieFragment = new MovieListFragment();
        popularMovieFragment.setArguments(CommonUtils.getBundleWithValue(2));
        adapter.addFragment(popularMovieFragment, getString(R.string.popular));
        mViewPager.setAdapter(adapter);
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
                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
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
                mActivityMainBinding.viewpager.setCurrentItem(pos, true);
                mActivityMainBinding.drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    protected void setupActivityComponent() { }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private final List<MovieListFragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public MovieListFragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(MovieListFragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    protected void onDestroy() {
        MovieApplication.get(HomeActivity.this).releaseMovieListComponent();
        super.onDestroy();
    }
}
