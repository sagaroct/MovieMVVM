package com.air.movieapp;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.air.movieapp.common.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(getViewPagerAdapterWithAddedFragments());
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
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
                break;
        }
        return true;
    }

    private void setNavigationDrawer() {
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
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
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private ViewPagerAdapter getViewPagerAdapterWithAddedFragments() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        MovieFragment movieFragment ;
        movieFragment = new MovieFragment();
        movieFragment.setArguments(CommonUtils.getBundleWithValue(0));
        adapter.addFragment(movieFragment, getString(R.string.top_rated));
        movieFragment = new MovieFragment();
        movieFragment.setArguments(CommonUtils.getBundleWithValue(1));
        adapter.addFragment(movieFragment, getString(R.string.upcoming));
        movieFragment = new MovieFragment();
        movieFragment.setArguments(CommonUtils.getBundleWithValue(2));
        adapter.addFragment(movieFragment, getString(R.string.popular));
        return adapter;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

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
