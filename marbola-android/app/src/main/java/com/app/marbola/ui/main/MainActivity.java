package com.app.marbola.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import com.app.marbola.R;
import com.app.marbola.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by arief on 07/05/18.
 */

public class MainActivity extends BaseActivity implements MainMvpView {

    private static final String TAG = "MainActivity";

    @Inject
    MainMvpPresenter<MainMvpView> mPresenter;

    @Inject
    MainPagerAdapter mPagerAdapter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.drawer_view)
    DrawerLayout mDrawer;

    @BindView(R.id.main_view_pager)
    ViewPager mViewPager;

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    /**
     * Un comment if you use navigation drawer
     */
    /*
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;
    */

    private int tabIconColorDefault;
    private int tabIconColorSelected;

    /**
     * Uncomment if you use navigation drawer
     */
    /* private ActionBarDrawerToggle mDrawerToggle; */

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        setUp();
    }

    @Override
    protected void setUp() {
        setSupportActionBar(mToolbar);

        tabIconColorDefault = getResources().getColor(R.color.gray2);
        tabIconColorSelected = getResources().getColor(R.color.white);

        /**
         * Uncomment if you use navigation drawer
         */
        /*
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawer,
                mToolbar,
                R.string.open_drawer,
                R.string.close_drawer
        ) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                hideKeyboard();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        */

        /* set up navigation drawer */
        /*
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        setUpNavMenu();
        mPresenter.onNavMenuCreated();
        */

        mPagerAdapter.setCount(4);
        mViewPager.setAdapter(mPagerAdapter);

        /* adding tab to tab layout */
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.ic_timer));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.ic_soccer));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.ic_calendar_clock));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.ic_trophy_variant_outline));

        /* set default color of tab icon */
        mTabLayout.getTabAt(0).getIcon().setColorFilter(tabIconColorDefault, PorterDuff.Mode.SRC_IN);
        mTabLayout.getTabAt(1).getIcon().setColorFilter(tabIconColorDefault, PorterDuff.Mode.SRC_IN);
        mTabLayout.getTabAt(2).getIcon().setColorFilter(tabIconColorDefault, PorterDuff.Mode.SRC_IN);
        mTabLayout.getTabAt(3).getIcon().setColorFilter(tabIconColorDefault, PorterDuff.Mode.SRC_IN);

        /* set toolbar title */
        setToolbarTitle(mTabLayout.getSelectedTabPosition());

        /* change color of tab icon selected */
        mTabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT);
        mTabLayout.getTabAt(mTabLayout.getSelectedTabPosition()).getIcon().setColorFilter(tabIconColorSelected, PorterDuff.Mode.SRC_IN);

        mViewPager.setOffscreenPageLimit(mTabLayout.getTabCount());
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                setToolbarTitle(mTabLayout.getSelectedTabPosition());
                tab.getIcon().setColorFilter(tabIconColorSelected, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(tabIconColorDefault, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    void setToolbarTitle(int position) {
        if (position == 0) {
            getSupportActionBar().setTitle(R.string.tt_live);
        } else if (position == 1) {
            getSupportActionBar().setTitle(R.string.tt_final_time);
        } else if (position == 2) {
            getSupportActionBar().setTitle(R.string.tt_schedule);
        } else if (position == 3) {
            getSupportActionBar().setTitle(R.string.tt_list_league);
        } else {
            getSupportActionBar().setTitle(R.string.tt_default);
        }
    }

    /**
     * Uncomment if you use navigation drawer
     */
    /*
    void setUpNavMenu() {
        // View headerLayout = mNavigationView.getHeaderView(0);

        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        mDrawer.closeDrawer(GravityCompat.START);
                        switch (item.getItemId()) {
                            case R.id.nav_item_live:
                                return true;
                            case R.id.nav_item_final_time:
                                return true;
                            case R.id.nav_item_schedule:
                                return true;
                            case R.id.nav_item_list_league:
                                return true;
                            default:
                                return false;
                        }
                    }

                }
        );
    }
    */

    @Override
    protected void onResume() {
        super.onResume();
        if (mDrawer != null)
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                upIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                            // Navigate up to the closest parent
                            .startActivities();
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onFragmentAttached() {
    }

    @Override
    public void onFragmentDetached(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            fragmentManager
                    .beginTransaction()
                    .disallowAddToBackStack()
                    .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                    .remove(fragment)
                    .commitNow();
        }
    }

    @Override
    public void lockDrawer() {
        if (mDrawer != null) {
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    @Override
    public void unlockDrawer() {
        if (mDrawer != null) {
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    @Override
    public void closeNavigationDrawer() {
        if (mDrawer != null) {
            mDrawer.closeDrawer(Gravity.START);
        }
    }

    @Override
    public void onBackPressed() {
        Intent exit = new Intent(Intent.ACTION_MAIN);
        exit.addCategory(Intent.CATEGORY_HOME);
        exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(exit);
        finish();
        super.onBackPressed();
    }

}
